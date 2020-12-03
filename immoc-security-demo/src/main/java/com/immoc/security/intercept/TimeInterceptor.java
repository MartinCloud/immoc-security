package com.immoc.security.intercept;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object hanlder) throws Exception {
        System.out.println("pre handler");

        String beanName = ((HandlerMethod)hanlder).getBean().getClass().getName();
        String methodName = ((HandlerMethod)hanlder).getMethod().getName();
        System.out.println(beanName + " : " + methodName);
        long startTime = new Date().getTime();
        httpServletRequest.setAttribute("startTime", startTime);
        return true;  //返回值决定后续是方法否执行
    }

    /**
     * 如果方法执行异常，不会进入到post handler
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("post handler");
        Long startTime = (Long) httpServletRequest.getAttribute("startTime");
        Long currTime = new Date().getTime();
        System.out.println("interceptor time consumer: " +  (currTime - startTime));
    }

    /**
     * 不管成功失败都会进入到after completion 方法
     * 如果方法执行异常，且没有被异常处理器处理，可以输出异常
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("after completion");
        Long startTime = (Long) httpServletRequest.getAttribute("startTime");
        Long currTime = new Date().getTime();
        System.out.println("after completion time consumer: " +  (currTime - startTime));
        System.out.println("exception is " + e);
    }
}
