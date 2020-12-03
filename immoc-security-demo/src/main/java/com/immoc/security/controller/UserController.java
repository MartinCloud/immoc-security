package com.immoc.security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.immoc.security.model.User;
import com.immoc.security.model.dto.UserQueryCondition;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/user/me")
    public Object getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/user/me2")
    public Object getCurrUser(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/user/me3")
    public Object getCurrUser3(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @JsonView(User.UserSimpleView.class)
    public List<User> query(@RequestParam(required = false, name="nickName", defaultValue = "Tom") String username) {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    @RequestMapping(value = "/userCondition", method = RequestMethod.GET)
    public List<User> queryCondition(UserQueryCondition user) {
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    @RequestMapping(value = "/userPage", method = RequestMethod.GET)
    public List<User> queryPage(UserQueryCondition user, @PageableDefault(page=2,size=10,sort="username,asc") Pageable pageable) {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    @RequestMapping(value = "/userPage/{id:\\d+}", method = RequestMethod.GET)
    @JsonView(User.UserDetailView.class)
    public User getUserInfo(@PathVariable(name = "id") String id) {
        User user = new User();
        user.setUserName("tom");
        return user;
    }

    /**
     * 必须加@ReqestBody
     * @param user
     * @return
     */
    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());
        user.setId("1");
        return user;
    }

    @PutMapping("/user/{id:\\+d}")
    public User updateUser(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
                FieldError filedError = (FieldError) error;
                String message = filedError.getField() + filedError.getDefaultMessage();
                System.out.println(message);
            });
        }
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());
        user.setId("1");
        return user;
    }

    @DeleteMapping("/user/{id:\\+d}")
    public void deleteUser(@PathVariable String id) {

        System.out.printf("id : " + id);;
    }

}
