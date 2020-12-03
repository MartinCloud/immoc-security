package com.immoc.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("登录用户名： " + username);
        // TODO 根据用户查找用户信息
        // String password = "数据库读取 ";数据库应该是加过密的
        String password = passwordEncoder.encode("123456");
        // 判断用户是否被冻结
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

}
