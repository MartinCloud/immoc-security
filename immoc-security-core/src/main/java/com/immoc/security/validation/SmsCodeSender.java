package com.immoc.security.validation;

public interface SmsCodeSender {

    void send(String mobile, String code);
}
