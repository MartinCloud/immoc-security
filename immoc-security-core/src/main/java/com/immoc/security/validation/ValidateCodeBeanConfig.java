package com.immoc.security.validation;

import com.immoc.security.properties.MySecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private MySecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")   // 先找，没有，则用下面配置
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsCodeSender")   // 先找，没有，则用下面配置
    public SmsCodeSender smsCodeGenerator() {
        return new DefaultSmsCodeSender();
    }
}
