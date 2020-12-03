package com.immoc.security.controller;

import com.immoc.security.properties.MySecurityProperties;
import com.immoc.security.validation.ImageCode;
import com.immoc.security.validation.SmsCodeSender;
import com.immoc.security.validation.ValidateCode;
import com.immoc.security.validation.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ValidationCodeController {

    public static final String IMAGE_SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
    public static final String CODE_SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request),IMAGE_SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createSms(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        ValidateCode smsCode =  smsCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request),CODE_SESSION_KEY, smsCode);
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
        smsCodeSender.send(mobile, smsCode.getCode());
    }

}
