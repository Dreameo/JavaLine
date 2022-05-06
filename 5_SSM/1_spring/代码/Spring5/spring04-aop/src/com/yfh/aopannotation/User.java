package com.yfh.aopannotation;

import org.springframework.stereotype.Component;

// 被增强类
@Component
public class User {
    public void add() {
        System.out.println("add 方法...");
    }
}
