package com.immoc.security.config;

import com.immoc.security.filter.TimeFilter;
import com.immoc.security.intercept.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 不带@Component注解的filter
 * 1. 可以配置在哪些url上过滤
 * 2. 继承WebMvcConfigurerAdapter
 *    实现方法addInterceptors()
 *    是为了配合interceptor使用
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private TimeInterceptor timeInterceptor;

    @Bean
    public FilterRegistrationBean timeFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TimeFilter timeFilter = new TimeFilter();
        registrationBean.setFilter(timeFilter);
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        //configurer.registerCallableInterceptors();
        //configurer.registerDeferredResultInterceptors();
        //configurer.setTaskExecutor();
    }
}
