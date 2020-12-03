package com.immoc.security.filter;

import com.immoc.security.controller.ValidationCodeController;
import com.immoc.security.exception.ValidationCodeException;
import com.immoc.security.properties.MySecurityProperties;
import com.immoc.security.validation.ImageCode;
import com.immoc.security.validation.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private Set<String> urls = new HashSet<>();

    private MySecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getValidationCodeProperties().getImage().getUrl(),",");
        for (String configUrl : configUrls) {
            urls.add(configUrl);
        }
        urls.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;
        for (String url : urls) {
            if (antPathMatcher.match(url, httpServletRequest.getRequestURI())) {
                action = true;
            }
        }

        if (StringUtils.equals("/authentication/mobile", httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")) {
            try {
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidationCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;  // 停止
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(servletWebRequest, ValidationCodeController.CODE_SESSION_KEY);
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(),"smsCode");
        if(StringUtils.isBlank(codeInRequest)) {
            throw new ValidationCodeException("验证码不能为空");
        }
        if(codeInSession == null) {
            throw new ValidationCodeException("验证码不存在");
        }
        if(codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(servletWebRequest, ValidationCodeController.CODE_SESSION_KEY);
            throw new ValidationCodeException("验证码过期");
        }
        if(!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidationCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(servletWebRequest, ValidationCodeController.CODE_SESSION_KEY);
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public MySecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(MySecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
