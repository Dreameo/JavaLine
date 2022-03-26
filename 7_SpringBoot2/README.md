# SpringBoot2



## 1、springboot-helloworld

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
>
> ​	其实是一样的，@SpringBootApplication是由三个注解共同组成的注解。



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

- 默认的包结构（只要是与主程序(启动程序)同包或者子包都可扫描到）

  - 如果非要改变扫描路径，可以通过扩大包来进行扫描@SpringBootApplication(scanBasePackages=**"com.yfh"**)或者使用ComponentScan();
  - @SpringBootApplication默认扫描与主程序以下的包
  - @SpringBootApplication相当于三个注解： @SpringBootConfiguration  @EnableAutoConfiguration  @ComponentScan("com.yfh.boot")

- 各种默认配置
  - 默认配置最终都是映射到某个类上，如：MultipartPropertie （文件上传类）
  - 配置文件的值最终会绑定到每个类上，每个类会在容器中创建对象

- 按需加载所有的自动配置项

  - 有很多启动场景（starter）
  - 可以根据需求自己引入哪些场景，引入的才会进行自动配置
  - springboot的所有自动配置功能都在spring-boot-autoconfigure中


### 2.2 、容器功能

> 用一些注解将组件加入到容器中WEB中常用的注解以及SpringBoot中的注解。 

#### 添加组件

##### @Configuration

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

> 2、这些WEB常用注解 @Bean、@Component、@Controller、@Service、@Repository同样也可以用来添加组件

> 3、 @ComponentScan、@Import 包扫描以及引入类组件

#####  @ComponentScan、@Import

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

> 4、条件装配：满足Conditional指定的条件，则进入组件注入,在Springboot低层有很多这样的注解(自动配置包)
>
> @Conditional有很多子注解



![image-20220325203643610](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/conditionalonbean.png)



#### 原生配置文件引入

> 原生配置文件引入，如果程序编写了配置文件，可以使用注解将其引入，将组件加入到容器中。

#### @ImportResource("classpath:beans.xml")	

比如导入Spring的配置文件。

![image-20220326113844528](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/importResource.png)

####  配置绑定

> 1、利用@ConfigurationProperties + @Component进行配置绑定 只有

![image-20220325211404254](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/mycar-configuration-properties.png)

> 2、第二种方式：@EnableConfigurationProperties(Car.class)开启Car的属性配置功能，将指定的这个Car组件自动注入容器中+@ConfigurationProperties()

![image-20220326120714907](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/EnableConfiguration.png)

```java
//1、开启Car配置绑定功能
//2、把这个Car这个组件自动注册到容器中

@EnableConfigurationProperties(Car.class) // 开启 Car 的属性配置并自动注入到容器中
public class MyConfiguration {...}

@ConfigurationProperties(prefix = "mycar")
public class Car {...}
```





### 2.4 自动配置原理入门

#### 2.4.1 引导加载自动配置类@SpringBootApplication

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
>
> 虽然我们127个场景的所有自动配置启动的时候默认全部加载。xxxxAutoConfiguration
> 按照条件装配规则（@Conditional），最终会按需配置。

```java
1、利用getAutoConfigurationEntry(annotationMetadata);给容器中批量导入一些组件
2、调用List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes)获取到所有需要导入到容器中的配置类
3、利用工厂加载 Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader)；得到所有的组件
4、从META-INF/spring.factories位置来加载一个文件。
	默认扫描我们当前系统里面所有META-INF/spring.factories位置的文件
    spring-boot-autoconfigure-2.3.4.RELEASE.jar包里面也有META-INF/spring.factories
    
```

总结：

- SpringBoot先加载所有的自动配置类  xxxxxAutoConfiguration
- 每个自动配置类按照条件进行生效，默认都会绑定配置文件指定的值。xxxxProperties里面拿。xxxProperties和配置文件进行了绑定
- 生效的配置类就会给容器中装配很多组件
- 只要容器中有这些组件，相当于这些功能就有了
- 定制化配置

- - 用户直接自己@Bean替换底层的组件
  - 用户去看这个组件是获取的配置文件什么值就去修改。

<font color='red'>**xxxxxAutoConfiguration ---> 组件  --->** **xxxxProperties里面拿值  ----> application.properties**</font>

### 2.5 开发小技巧

> 安装lombok插件,简化JavaBean开发, 还能引入slf4j日志

各注解功能：

https://www.cnblogs.com/ooo0/p/12448096.html

```xml
<!--pom文件中添加依赖-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

> dev-tools : 热更新，但是只是重启，项目或者页面修改以后CTRL + F9

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

> 创建新项目的时候可以选择springboot initializr项目初始化向导，按需要加载场景，比如web、数据库、缓存。

## SpringBoot2核心功能

### 3、配置文件

#### 1、文件类型

> 有properties，yaml（可以简写yml都能识别）

#### 2、yaml

> AML 是 "YAML Ain't Markup Language"（YAML 不是一种标记语言）的递归缩写。在开发的这种语言时，YAML 的意思其实是："Yet Another Markup Language"（仍是一种标记语言）。 

非常适合用来做以<font color='red'>数据为中心</font>的配置文件

1. 基本语法
   - key：value； kv之间有空格
   - 大小写敏感
   - 使用缩进表示层级关系
   - 缩进不允许使用tab，只允许空格(idea使用tab也没事)
   - 缩进的空格数不重要，只要相同层级的元素左对齐即可
   - '#'表示注释
   - 字符串无需加引号，如果要加，<font color='red'>''与""表示字符串内容 会被 转义/不转义</font>

2. 数据类型

​		<font color='red'>字面量</font>：单个的、不可再分的值。<font color='red'>date、boolean、string、number、null</font>

```yaml
k:v
```

​		<font color='red'>对象</font>：键值对的集合。<font color='red'>map、hash、set、object </font>

```yaml
#行内写法： 
k: {k1:v1,k2:v2,k3:v3}
#或
k: 
  k1: v1
  k2: v2
  k3: v3
```

 	<font color='red'>数组</font>： 一组按次序排列的值。<font color='red'>array、list、queue</font>

```yaml
#行内写法：  
k: [v1,v2,v3]
#或者
k:
 - v1
 - v2
 - v3
```

```java
package com.yfh.springboot03yaml.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Data
@ToString
@ConfigurationProperties(prefix = "person") // 记得要与配置文件前缀绑定
public class Person {
    private String userName;
    private Boolean boss;
    private Date birth;
    private Pet pet;
    private String[] interest;
    private List<String> animal;
    private Map<String, Object> score;
    private Set<Double> salarys;
    private Map<String, List<Pet>> allPets;
}

```

```yaml
# person对象测试
person:
  userName: zhangsan
  boss: true
  birth: 2020/09/08
  age: 20
  pet:
    name: xiaoming
    weight: 22.0
  interest: [篮球,羽毛球]
  animal:
    - jerry
    - morry
  score:
    english: [12,34,56] #数组
    math:
      first: 30
      second: 20
      third: 33
    chinese: {first: 20, second: 20}
  salarys: [2222,344.23,44324]
  allPets:
    sick:
      - {name: tom22, weight: 20.0}
      - {name: tom23, weight: 90.0}
    health: [{name: tom24, weight: 22.0},{name: ttt}]

```

> 自己写的类，与配置绑定时，写yaml配置未见，没有自动提示，而springboot内置都有提示

复制依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

### 4、Web开发

#### 4.1 静态资源访问

```text
只要静态资源放在类路径下： /static (or /public or /resources or /META-INF/resources
访问 ： 当前项目根路径/ + 静态资源名
```

>  原理： 静态映射/**。请求进来，先去找Controller看能不能处理。不能处理的所有请求又都交给静态资源处理器。静态资源也找不到则响应404页面

![image-20220326175157441](https://raw.githubusercontent.com/Dreameo/JavaLine/master/7_SpringBoot2/imgs/static-images.png)



> 也可以改变默认的静态资源路径

```yaml
spring:
  mvc:
    static-path-pattern: /res/** #改变默认请求映射
    # 当前项目 + static-path-pattern + 静态资源名 = 静态资源文件夹下找
  web:
    resources:
      static-locations: # 改变静态资源存放位置，可以放多个位置，所以是一个数组配置
        [classpath:/test/]
```

> 自动映射 /[webjars](http://localhost:8080/webjars/jquery/3.5.1/jquery.js)/**
>
> 将一些前端常用资源以jar的形式来使用



> 建议：静态资源路径可以不自定义设置，使用默认，但是尽量配置一个访问前缀

#### 4.2 欢迎页

- 静态资源路径下  index.html

- - 可以配置静态资源路径
  - 但是不可以配置静态资源的访问前缀。否则导致 index.html不能被默认访问

- > favicon.ico 放在静态资源目录下即可,页面标题前面的图标

```yaml
spring:
#  mvc:
#    static-path-pattern: /res/**   这个会导致 Favicon 功能失效
```













面试题：
页面开发，cookie禁用了，session里面的内容怎么使用；

路径重写传递jsessionid, 把cookie的值使用矩阵变量的方式传递。
