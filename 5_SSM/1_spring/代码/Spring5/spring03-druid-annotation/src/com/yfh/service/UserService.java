package com.yfh.service;

import org.springframework.stereotype.Component;

/**
 * 注解属性值可以不写，默认名称为类名称，首字母小写userService，比如类是User--》那么注解名id名就是user
 */

//@Component(value = "userService") // 相当于<bean id = "userService" class = "com.yfh.service.UserService">


public interface UserService {
    void add();
}
