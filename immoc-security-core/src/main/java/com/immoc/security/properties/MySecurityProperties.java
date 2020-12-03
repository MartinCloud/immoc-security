package com.immoc.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "immoc.security")
public class MySecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    private ValidationCodeProperties code = new ValidationCodeProperties();

    public ValidationCodeProperties getValidationCodeProperties() {
        return code;
    }

    public void setValidationCodeProperties(ValidationCodeProperties validationCodeProperties) {
        this.code = validationCodeProperties;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browserProperties) {
        this.browser = browserProperties;
    }
}
