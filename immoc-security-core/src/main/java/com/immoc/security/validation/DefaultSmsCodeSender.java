package com.immoc.security.validation;

public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机发送验证码");
    }
}
