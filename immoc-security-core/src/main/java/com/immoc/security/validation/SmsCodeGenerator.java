package com.immoc.security.validation;

import com.immoc.security.properties.MySecurityProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private MySecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getValidationCodeProperties().getSmsCode().getLength());
        return new ValidateCode(code, securityProperties.getValidationCodeProperties().getSmsCode().getLength());
    }

    public MySecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(MySecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
