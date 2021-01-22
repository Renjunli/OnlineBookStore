package com.group7.store.security;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group7.store.entity.user.SecurityUser;
import com.group7.store.entity.user.User;
import com.group7.store.service.UserService;
import com.group7.store.util.JwtTokenUtil;
import com.group7.store.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Liuminge
 * @Date: 2021/1/18
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String CONTEXTTYPE = "application/json;charset=utf-8";
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
//    auth.userDetailsService(myUserDetailService)将我们自己定义的myUserDetailService
//    注入到SpringSecurity的安全上下文中去，SpringSecurity早自己有一个默认的UserDetailsService,这里将它替换掉了
    //同样地，这里将我们自己定义的加密工具类替换掉原先默认的加密替换类
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/states", "/user/switch", "/book/modify").hasAnyRole("ADMIN")
                .antMatchers("/user/init", "/book/get", "/user/code", "/user/signup", "/book/detail", "/book/search", "/images/*", "/mail","/img/*").permitAll()
                .antMatchers("/book/*", "/register", "/sort/*", "/topic/*", "/user/register", "/user/accountVerify")// 对登录注册要允许匿名访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
//                .antMatchers("/**")//测试时全部运行访问
//                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/user/login").permitAll()
                .and()
                .logout().logoutUrl("/logout").permitAll().logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType(CONTEXTTYPE);
                PrintWriter out = response.getWriter();
                HttpSession session = request.getSession();
                session.removeAttribute("loginState");
                out.write(new ObjectMapper().writeValueAsString("登出成功"));
                out.flush();
                out.close();
            }
        })
                .and().csrf().disable()
                // 开启跨域
                .cors().and();
        // 禁用缓存
        http.headers().cacheControl();

        // 添加JWT filter
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);

        //添加登陆验证过滤器
        http.addFilterAt(cafilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CustomAuthenticationFilter cafilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                resp.setContentType(CONTEXTTYPE);
                PrintWriter out = resp.getWriter();
                log.info("=============登录成功===============");
                //从authentication中取出SecurityUser的信息
                SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
                String token = jwtTokenUtil.generateToken(securityUser);
                token = "Bearer" + token;

                resp.setHeader("Authorization", token);
                resp.setHeader("Access-control-Expose-Headers", "Authorization");
                User user = new User();
                user.setAccount(securityUser.getUsername());
                boolean isManage = userService.getUser(user.getAccount()).isManage();
                user.setManage(isManage);
                Map<String, Object> map = new HashMap<>();
                map.put("user", user);
                String json = JSON.toJSONString(ResultUtil.resultSuccess(map));
                out.print(json);
                out.flush();
                out.close();
            }
        });
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                resp.setContentType(CONTEXTTYPE);
                log.info("=============登录失败=================");
                PrintWriter out = resp.getWriter();
                out.write(new ObjectMapper().writeValueAsString("登录失败"));
                out.flush();
                out.close();
            }
        });
        //authenticationManagerBean()为WebSecurityConfigurerAdapter中的一个方法，
        // 这个方法的返回值为AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
}
