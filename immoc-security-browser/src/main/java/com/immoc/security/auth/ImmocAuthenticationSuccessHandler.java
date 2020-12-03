package com.immoc.security.auth;

import com.immoc.security.properties.LoginType;
import com.immoc.security.properties.MySecurityProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("imoocAuthenticationSuccessHandler")
public class ImmocAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MySecurityProperties securityProperties;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("登陆成功");
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        }

    }
}
