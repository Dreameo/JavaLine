package com.yfh.beans;

/**
 * Spring有参构造方法属性注入
 * 1. 定义类的属性
 * 2. 创建有参构造
 * 3. 在Spring配置文件中注入属性值
 */
public class BookArgs {
    private String name;
    private String author;

    // 有参数得构造方法
    public BookArgs(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String out(){
        return "这里name的值为：" + name + "， author的值为：" + author;
    }
}
