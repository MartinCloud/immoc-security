package com.immoc.security.config;

import com.immoc.security.auth.ImmocAuthenticationFailureHandler;
import com.immoc.security.auth.ImmocAuthenticationSuccessHandler;
import com.immoc.security.auth.SmsCodeAuthenticationSecurityConfig;
import com.immoc.security.filter.SmsCodeFilter;
import com.immoc.security.filter.ValidateCodeFilter;
import com.immoc.security.properties.MySecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MySecurityProperties securityProperties;
    @Autowired
    private ImmocAuthenticationSuccessHandler immocAuthenticationSuccessHandler;
    @Autowired
    private ImmocAuthenticationFailureHandler immocAuthenticationFailureHandler;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()             //身份认证，表单登录
            .loginPage("./immoc_login.html")     // 登录页面
            .loginProcessingUrl("/authentication/login")   // 替换spring security默认的login请求
            .and()
            .authorizeRequests()
            .antMatchers("/immoc_login.html").permitAll() // 这个不需要认证
            .anyRequest()             // 下面的需要授权
            .authenticated()
            .and()                    // 跨站域名伪造
            .csrf().disable();
    }*/
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setCreateTableOnStartup(true); // 第二次运行报错
        return tokenRepository;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(immocAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);

        SmsCodeFilter smsCodeFilter  = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(immocAuthenticationFailureHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()             //身份认证，表单登录
                .loginPage("/authentication/require")     // 登录页面
                .loginProcessingUrl("/authentication/login")   // 替换spring security默认的login请求
                .successHandler(immocAuthenticationSuccessHandler)
                .failureHandler(immocAuthenticationFailureHandler)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",
                        securityProperties.getBrowser().getLoginPage(),
                        "/code/*").permitAll() // 这个不需要认证
                .anyRequest()             // 下面的需要授权
                .authenticated()
                .and()                    // 跨站域名伪造
                .csrf().disable()
                .apply(smsCodeAuthenticationSecurityConfig);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
