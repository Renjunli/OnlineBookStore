package com.group7.store.security;

import com.group7.store.entity.user.SecurityUser;
import com.group7.store.entity.user.User;
import com.group7.store.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author: Liuminge
 * @Date: 2021/1/18
 */
@Component
public class MyUserDetailService implements UserDetailsService {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        log.info("======username:================");
        log.info(username);
        User user = userService.getUser(username);

        SecurityUser securityUser = new SecurityUser();
        if (user == null) {
            securityUser.setEnabled(true);
            securityUser.setUsername("wrong" + username);
            securityUser.setPassword("wrong");
        } else {
            log.info("登录用户名:");
            log.info(username);
            log.info("数据库密码：");
            log.info(user.getPassword());

            securityUser.setEnabled(user.isEnable());
            securityUser.setUsername(username);
            securityUser.setPassword(user.getPassword());
            String role = user.isManage() ? "ROLE_ADMIN" : "ROLE_USER";
//         public static List<GrantedAuthority>
//
            //这里设置securityUser的角色
            securityUser.setRoles(AuthorityUtils.commaSeparatedStringToAuthorityList(role));

        }
        //返回角色的信息
        return securityUser;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
