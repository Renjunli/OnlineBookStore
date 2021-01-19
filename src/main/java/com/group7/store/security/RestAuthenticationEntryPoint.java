package com.group7.store.security;

import com.alibaba.fastjson.JSON;
import com.group7.store.util.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/18
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
//        response.getWriter().println(JSONUtil.parse(CommonResult.unauthorized(authException.getMessage())));
//        response.getWriter().println(ResultUtil.resultCode(401,"不好意思，你的登陆信息已经失效，请重新登陆"));
        String json = JSON.toJSONString(ResultUtil.resultCode(401, "不好意思，你的登陆信息已经失效，请重新登陆"));
        response.getWriter().print(json);
        response.getWriter().flush();
    }
}
