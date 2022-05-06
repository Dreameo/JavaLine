package com.yfh.beans;

public class Order {
    private String oName;

    public Order(){
        System.out.println("执行无参构造方法第一步，创建bean实例：oName = " + oName);
    }

    public void setoName(String oName) {
        this.oName = oName;
        System.out.println("第二步，调用set方法给属性注入值");
    }

    // 创建一个执行的初始化方法
    public void initMethod() {
        System.out.println("第三步：执行初始化方法");
    }

    // 创建一个销毁的方法
    public void destoryMethod() {
        System.out.println("第五步：销毁");
    }
}
