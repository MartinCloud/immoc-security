package com.immoc.security.validation;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);
}
