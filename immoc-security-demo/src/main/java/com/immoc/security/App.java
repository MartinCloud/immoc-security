package com.immoc.security;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@RestController
@EnableSwagger2
public class App {

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }

    @GetMapping("/hello")
    @ApiOperation("")
    public String hello() {
        return "hello spring security";
    }

}
