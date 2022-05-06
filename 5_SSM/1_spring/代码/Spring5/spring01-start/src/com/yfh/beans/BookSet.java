package com.yfh.beans;

/**
 * 使用set方法注入属性值
 * 1. 定义类的属性
 * 2. 创建对应的set方法
 * 3. 在Spring配置文件中bean标签里面property进行注入值
 */
public class BookSet {
    private String name;

    public BookSet() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
