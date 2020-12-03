package com.immoc.security.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.immoc.security.validate.MyConstraint;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @JsonView 使用步骤
 * 1. 使用接口来申明多个视图
 * 2. 在值对象的get方法上指定视图
 * 3. 在Controller方法上指定视图
 */

public class User {


    public interface UserSimpleView {};

    public interface UserDetailView extends UserSimpleView{};

    @MyConstraint(message = "this is a test")
    private String userName;

    @NotBlank                     //校验不为空，和@Valid配合使用
    private String password;

    private String id;

    @Past(message = "生日必须是过去时间")
    private Date birthday;

    @JsonView(UserSimpleView.class)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
