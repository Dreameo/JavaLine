# SpringBoot2

# # 1、springboot-helloworld

> 官网默认方式

```java
@RestController // 相当于@Controller 和 @ResponseBody 复合注解
@EnableAutoConfiguration
public class MyApplication {

    @RequestMapping("/hello")
    public String index() {
        return "Hello SpringBoot2";
    }

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```



> 一个SpringBoot启动类，其他还是跟web应用一样写controller的写法

![image-20220325183150994](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/springboot-helloworld-error1.png)

正确包的情况为：作为子包存在

![image-20220325183322990](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/springboot-helloworld-success.png)

> 疑问：两种方式的区别？



> 全部配置可以都设置在一个配置文件中

>可以打包成一个可执行jar包，包含运行环境，直接运行jar就行。



## 2、SpringBoot自动配置原理

### 2.1 SpringBoot特点

#### 2.1.1 依赖管理

父项目作为依赖管理

![image-20220325185531990](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/pom_dependency.png)

```xml
1. 父项目作为依赖管理，几乎声明了所有开发中常用的依赖的版本号,自动版本仲裁机制
2. 不需要关注版本，自动版本仲裁
3. maven特性，就近原则，如果要使用自己的特定的版本，可以通过在pom文件中重写配置。 从spring-boot-dependencies里查看依赖的版本 用的key：
比如：mysql （key为<mysql.version>）
<properties>
        <mysql.version>5.1.43</mysql.version>
</properties>
4. 如果引入非版本仲裁的jar，要写版本号。
```



#### 2.1.2 自动配置

- 自动配置tomcat

  - 引入tomcat包

  ```xml
  web-start的父容器完成了
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-tomcat</artifactId>
      <version>2.6.5</version>
      <scope>compile</scope>
  </dependency>
  ```

  - 配置tomcat

- 自动配置Springmvc

  - SpringMVC全套组件

  ```xml
  web-start的父容器完成
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>5.3.17</version>
      <scope>compile</scope>
  </dependency>
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.3.17</version>
      <scope>compile</scope>
  </dependency>
  ```

  ```java
   public static void main(String[] args) {
          // 1. 返回IOC容器
          ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
  
          // 2. 查看容器里面的组件
          String[] beanDefinitionNames = run.getBeanDefinitionNames();
          for (String bean : beanDefinitionNames) {
              System.out.println(bean);
          }
     }
  // 配置SpringMVC一般会配置DispatcherServlet、编码过滤器之类的
  ```

  ![image-20220325191547702](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/beans.png)

  

  - 自动配置好了Web常用功能，如字符编码、文件上传等
  - 默认的包结构（只要是与主程序同包或者子包都可扫描到）
    - 如果非要改变扫描路径，可以通过扩大包来进行扫描@SpringBootApplication(scanBasePackages=**"com.yfh"**)或者使用ComponentScan();
    - @SpringBootApplication默认扫描与主程序以下的包
    - @SpringBootApplication相当于三个注解： @SpringBootConfiguration  @EnableAutoConfiguration  @ComponentScan("com.yfh.boot")
  - 各种默认配置
    - 默认配置最终都是映射到某个类上，如：MultipartPropertie （文件上传类）
  - 按需加载所有的自动配置项
    - springboot的所有自动配置功能都在spring-boot-autoconfigure中

### 2.2 、容器功能

#### @Configuration

- 基本使用

![image-20220325195441626](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/configuration-usage.png)

```java
// 配置类
/**
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * 2、配置类本身也是组件
 * 3、proxyBeanMethods：代理bean的方法
 *      Full(proxyBeanMethods=true)、【保证每个@Bean方法被调用多少次返回的组件都是单实例的】
 *      Lite(proxyBeanMethods=false)【每个@Bean方法被调用多少次返回的组件都是新创建的】
 *      组件依赖必须使用Full模式默认。其他默认是否Lite模式
 */


@Configuration(proxyBeanMethods = false)
public class MyConfig {
//    Full:外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象

    @Bean // 给容器中添加组件。以方法名作为组件的id。返回类型就是组件类型。返回的值，就是组件在容器中的实例
    public User user1() {
        User zhangsan = new User("zhangsan", 19);
        zhangsan.setPet(pet()); // proxyBeanMethods = false 但是不影响运行！！

        /**
         * user组件依赖了Pet组件
         */

        return zhangsan;
    }

    @Bean("tompet")
    public Pet pet() {
        return new Pet("petty");
    }
}


// 测试类：

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        // 1. 返回IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);

        // 2. 查看容器里面的组件
        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String bean : beanDefinitionNames) {
            System.out.println(bean);
        }

        // 3. 从容器中获取组件
        /**
         * 默认单例对象，因此不管多少次获取组件，都是从容器中获取同一个。
         */
        User user1 = run.getBean("user1", User.class);
        User user2 = run.getBean("user1", User.class);
        System.out.println(user1==user2);

        /**
         * 但是SpringBoot2在@Configuration注解中添加了proxyBeanMethods属性。
         * 如果@Configuration(proxyBeanMethods = true)代理对象调用方法。SpringBoot总会检查这个组件是否在容器中有。
         *
         */
        User user01 = run.getBean("user1", User.class);
        Pet tom = run.getBean("tompet", Pet.class);

        System.out.println("用户的宠物："+(user01.getPet() == tom));


    }
}

```

#### @Import

```java
@Import({User.class, Pet.class, Filter.class}) // 给容器中自动创建出这两个类型的组件、默认组件的名字就是全类名
public class MyConfig {
...

// 获取组件
String[] beanNamesForType = run.getBeanNamesForType(User.class);
System.out.println("============User=================");
for (String s : beanNamesForType) {
    System.out.println(s);
}

```

![image-20220325202817454](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/import-annotation.png)

#### @Conditional

> 条件装配：满足Conditional指定的条件，则进入组件注入,在Springboot低层有很多这样的注解



![image-20220325203643610](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/conditionalonbean.png)



#### @ImportResource("classpath:beans.xml")	

导入Spring的配置文件。

### 2.3 配置绑定

> 利用@ConfigurationProperties + @Component进行配置绑定

![image-20220325211404254](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/mycar-configuration-properties.png)

> 第二种方式：@EnableConfigurationProperties(Car.class)开启Car的属性配置功能，将指定的这个Car组件自动注入容器中+@ConfigurationProperties()



### 2.4 自动配置原理入门

#### 2.4.1 引导加载自动配置类

```java
// @SpringBootApplication是
 //		@SpringBootConfiguration
 //		@EnableAutoConfiguration
 //		@ComponentScan 这三个的合成注解

```

1. @SpringBootConfiguration

```java
// 点进去查看 还是一个@Configuration
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@Indexed
public @interface SpringBootConfiguration {
    @AliasFor(
        annotation = Configuration.class
    )
    boolean proxyBeanMethods() default true;
}
```

> @Configuration代表的是一个配置类

2. @Component是扫描组件类，Spring注解
3. EnableAutoConfiguration

```java
// 点进去，发现还是合成注解
@AutoConfigurationPackage
@Import({AutoConfigurationImportSelector.class}) 
public @interface EnableAutoConfiguration {
}
```

- @AutoConfigrationPackage

```java
@Import({Registrar.class}) // 给容器中导入一个组件（Registrar.class）, 点进去查看源代码
public @interface AutoConfigurationPackage {} 
```

```java
// 利用Registrar导入一系列组件：
public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
    AutoConfigurationPackages.register(registry, (String[])(new AutoConfigurationPackages.PackageImports(metadata)).getPackageNames().toArray(new String[0])); // 将指定一个包的所有组件导入进来
}
```

![image-20220325214526397](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/autoconfigurationPackage.png)

- @Import({AutoConfigurationImportSelector.class})  // 导入组件

```java
@Override
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return NO_IMPORTS;
		}
		AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(annotationMetadata);
		return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
	}
// 批量导入组件
```

![image-20220325215837243](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/import(AutoConfigurationImportSelector).png)

> 默认全部加载127个写死的组件。META/INF/ fatories.  但最终会按需加载。



#### 2.5 开发小技巧

> 安装lombok插件,简化JavaBean开发, 还能引入slf4j日志

```xml
<!--pom文件中添加依赖-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

> dev-tools : 热更新，但是只是重启，项目或者页面修改以后CTRL + 9

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

> 创建新项目的时候可以选择springboot initializr项目初始化向导，按需要加载场景，比如web、数据库、缓存。

