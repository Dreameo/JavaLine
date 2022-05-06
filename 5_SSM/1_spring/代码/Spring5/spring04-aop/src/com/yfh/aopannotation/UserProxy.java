package com.yfh.aopannotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// 增强类
@Component
@Aspect // 要生成代理对象
public class UserProxy {
    // 前置通知
    // before表示前置通知
    @Before(value = "execution(* com.yfh.aopannotation.User.add(..))") // *中间要空格
    public void before() {
        System.out.println("before...");
    }
    
    
}
