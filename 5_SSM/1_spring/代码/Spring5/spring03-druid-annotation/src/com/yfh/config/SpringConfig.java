package com.yfh.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 作为配置类，用来代替xml文件
@ComponentScan(basePackages = {"com.yfh"})
public class SpringConfig {

}
