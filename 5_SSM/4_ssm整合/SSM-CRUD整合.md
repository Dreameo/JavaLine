# SSM-CRUD整合

## 1、整合整体概况

![image-20220324210727584](https://raw.githubusercontent.com/Dreameo/JavaLine/master/5_SSM/4_ssm%E6%95%B4%E5%90%88/imgs/image01-build-overview.png)



## 2、spring所需依赖：

**ioc依赖的jar包：**

![1647937110856](https://raw.githubusercontent.com/Dreameo/JavaLine/master/5_SSM/4_ssm整合/imgs/2_ioc_packages)

**aop依赖的jar包：**

![image-20220324183033243](https://raw.githubusercontent.com/Dreameo/JavaLine/master/5_SSM/4_ssm%E6%95%B4%E5%90%88/imgs/image-aop-packages.png)

**在maven工程中只要添加context依赖，因为存在传递依赖，因此其他依赖也随之被导入。也可一个一个地添加依赖**

![image-20220324211442014](https://raw.githubusercontent.com/Dreameo/JavaLine/master/5_SSM/4_ssm%E6%95%B4%E5%90%88/imgs/image-springcontext-dependency.png)

## 3、在pom.xml中添加依赖所需要的坐标

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.yfh</groupId>
    <artifactId>ssmbuild</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>


    <!--版本信息-->
    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <spring.version>5.0.2.RELEASE</spring.version>
        <slf4j.version>1.6.6</slf4j.version>
        <log4j.version>1.2.12</log4j.version>
        <shiro.version>1.2.3</shiro.version>
        <mysql.version>5.1.3</mysql.version>
        <mybatis.version>3.5.7</mybatis.version>
        <spring.security.version>5.0.1.RELEASE</spring.security.version>
    </properties>

    <!--版本绑定-->
    <dependencies>
        <!--spring start-->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.6.8</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!--springmvc
        1. spring-webmvc
        2. ServletAPI
        3. Spring5和Thymeleaf整合包
        -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
        <!-- Spring5和Thymeleaf整合包 -->
        <dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf-spring5</artifactId>
            <version>3.0.12.RELEASE</version>
        </dependency>


        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>${spring.version}</version>
        </dependency>


        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>

        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.0</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>


        <!-- log start -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>${log4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <!-- log end -->


        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>${mybatis.version}</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.3.0</version>
        </dependency>

        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
            <type>jar</type>
            <scope>compile</scope>
        </dependency>

    </dependencies>


</project>
```

## 4、编写实体类（pojo）、dao、service



```java
public class Book {
    private Integer bookId;
    private String bookName;
    private String bookCounts;
    private String detail;
}
// 构造方法
// getter/setter方法
// toString方法

```

```java
public interface BookDAO {

    /**
     * 增加书本信息
     */
    int addBook(Book book);


    /**
     * 删除书本信息
     */
    int deleteBookById(Integer bookId);


    /**
     * 修改书本信息
     */
    int updateBook(Book book);


    /**
     * 根据id查询书本信息
     */
    Book getBookById(Integer bookId);


    /**
     * 查询所有书本信息
     */
    List<Book> getAllBook();
}

```

服务层接口：BookService和实现类BookServiceImpl：

```java
public class BookServiceImpl implements BookService {
    
    private BookDAO bookDAO;
    
    @Override
    public int addBook(Book book) {
        return 0;
    }

    @Override
    public int deleteBookById(Integer bookId) {
        return 0;
    }

    @Override
    public int updateBook(Book book) {
        return 0;
    }

    @Override
    public Book getBookById(Integer bookId) {
        return null;
    }

    @Override
    public List<Book> getAllBook() {
        System.out.println("业务层：查询所有书本信息...");
        return null;
    }
}
```



## 5、 测试Spring是否可以运行

> pom文件坐标都设置好了之后，就可以先配置Spring的运行环境，测试没问题，然后再配置两外两个其他（SpringMVC和Mybatis均可）



> 在资源目录下创建applicationContext.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd">
    
    
    <!--开启注解扫描，要扫描的是service和dao层的注解，要忽略web层注解，因为web层让SpringMVC框架去管理 要关闭使用默认过滤器，不然就算设置了下面的过滤标准都会被默认过滤器覆盖use-default-filters="false"-->
    <context:component-scan base-package="com.yfh.ssm" use-default-filters="true" >
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
    
</beans>
```

> 在BookServiceImpl类上加上注解@Service, 然后创建测试类查看Spring是否正常运行

```java
  @Test
    public void testService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        BookService bookService = context.getBean("bookServiceImpl", BookServiceImpl.class);

        bookService.getAllBook(); 

    }

// 输出 ： 业务层：查询所有书本信息...

// 因此Spring在业务层运行正常，可以进行配置SpringMVC的环境了

```



## 6、 SpringMVC环境配置

>  一个SpringMVC-config.xml文件，一个web.xml入口文件(DispatcherServlet中央控制器，过滤器，监听器)

springmvc-config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 扫描controller的注解，别的不扫描  要关闭使用默认过滤器，不然就算设置了下面的过滤标准都会被默认过滤器覆盖use-default-filters="false"-->
    <context:component-scan base-package="com.yfh.ssm" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--配置视图解析器 Thymeleaf视图解析器 如果是jsp的话配的是 InternalResourceViewResolver -->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">

                        <!-- 视图前缀 -->
                        <property name="prefix" value="/WEB-INF/templates/"/>

                        <!-- 视图后缀 -->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8" />
                    </bean>
                </property>
            </bean>
        </property>
    </bean>

    <!--3. 过滤静态文件：
     处理静态资源，例如html、js、css、jpg
    若只设置该标签，则只能访问静态资源，其他请求则无法访问
    此时必须设置<mvc:annotation-driven/>解决问题
   -->
    <mvc:default-servlet-handler/>
    <!-- 开启mvc注解驱动 -->
    <mvc:annotation-driven/>


</beans>
```

<font color='red'>正确理解use-default-filters="false"</font> ---> `https://blog.csdn.net/wdr2003/article/details/84502777`

web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--配置前端控制器：DispatcherServlet, 服务器启动必须加载，需要加载springmvc.xml配置文件-->
    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 配置初始化参数，创建完DispatcherServlet对象，加载springmvc.xml配置文件 -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-config.xml</param-value>
        </init-param>
        <!--设置启动时间，尽量在服务器启动时将一些耗时的操作完成-->
        <!-- 服务器启动的时候，让DispatcherServlet对象创建 -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <!-- / 拦截除.jsp的外所有请求  /* 拦截所有请求-->
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>


    <filter>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>


</web-app>
```



> 创建控制器和页面，发送请求查看控制器中是否能够调Service中的方法，如果成功的话，那么就进行Spring和SpringMVC的整合了

BookControler:

```java
@Controller
@RequestMapping("/book")
public class BookController {
    @RequestMapping("/index") // 进入首页
    public String index() {

        return "index";
    }
    @RequestMapping("/test") // 测试链接是否可以跳转
    public String test() {
        return "success";
    }
```

> 创建页面，然后配置tomcat服务器 测试是否正常运行

index和success页面均可进入。

> 整合Spring和SpringMVC，思想：启动tomcat服务器的时候，需要加载spring配置文件，才能将bean交给spring管理

![1647947865145](https://raw.githubusercontent.com/Dreameo/JavaLine/master/5_SSM/4_ssm%E6%95%B4%E5%90%88/imgs/image-springmvc-spring.png)

> 在web.xml中配置监听器
>
> 在项目启动的时候，就去加载applicationContext.xml的配置文件，在web.xml中配置 ContextLoaderListener监听器（该监听器默认只能加载WEB-INF目录下的applicationContext.xml的配置文 件）

```xml
    <!-- 配置Spring的监听器 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <!-- 配置加载类路径的配置文件 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <!--配置加载类路径的配置文件-->
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
```

> 在Controller中注入Service,调用方法是否成功

```xml
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/index") // 进入首页
    public String index() {

        return "index";
    }

    @RequestMapping("/test") // 测试链接是否可以跳转
    public String test() {
        System.out.println("表现层：查询所有书本信息...");
        bookService.getAllBook();
        return "success";
    }
```

![image-20220325003056144](https://raw.githubusercontent.com/Dreameo/JavaLine/master/5_SSM/4_ssm整合/imgs/test-success.png)

整合成功。



## 7、MyBatis环境配置

> 创建mybatis-config.xml配置文件 和 dao层对应的Mapper映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--引入properties文件 jdbc-->
    <properties resource="jdbc.properties"></properties>

    <!--取别名-->
    <typeAliases>
        <package name="com.yfh.ssm.pojo"/>
    </typeAliases>

    <!--设置连接数据库的环境-->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.user}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>

    <!--引入映射文件-->
    <mappers>
        <!--整个包的mapper文件-->
        <!--<mapper resource="mappers/UserMapper.xml"/>-->
        <package name="com.yfh.ssm.dao"/>
    </mappers>
</configuration>
```

> jdbc.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:13306/ssmbuild?characterEncoding=utf8
# useSSL=true&useUnicode=true& 加上报错
jdbc.user=root
jdbc.password=root
```

> BookDAO.xml (Mapper文件)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yfh.ssm.dao.BookDAO">

    <!--int addBook(Book book)-->
    <insert id="addBook" parameterType="Book">
        INSERT INTO t_book VALUES(0, #{book.bookName}, #{book.bookCounts}, #{book.detail})
    </insert>

    <!--int deleteBookById(Integer bookId);-->
    <delete id="deleteBookById" parameterType="Integer">
        DELETE FROM t_book WHERE bookID = #{bookId}
    </delete>

    <!--int updateBook(Book book);-->
    <update id="updateBook" parameterType="Book">
        UPDATE t_book SET bookName = #{book.bookName}, bookCounts = #{book.bookCounts}, detail = #{book.detail} WHERE bookID = #{book.bookId}
    </update>


    <!--Book getBookById(Integer bookId);-->
    <select id="getBookById" resultType="Book">
        SELECT * FROM t_book WHERE bookID = #{bookId}
    </select>

    <!--List<Book> getAllBook();-->
    <select id="getAllBook" resultType="Book">
        SELECT * FROM t_book
    </select>
    
</mapper>
```

> 单元测试

```java
@Test
public void testDAO() throws IOException {
    InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
    SqlSession sqlSession = sqlSessionFactory.openSession(true);
    BookDAO mapper = sqlSession.getMapper(BookDAO.class);

    List<Book> allBook = mapper.getAllBook();
    allBook.forEach(System.out::println);
}
```

![image-20220325111240408](https://raw.githubusercontent.com/Dreameo/JavaLine/master/5_SSM/4_ssm%E6%95%B4%E5%90%88/imgs/mybatis-test.png)

> 测试完成没问题，可以整合Spring和MyBatis了

## 8、Spring整合MyBatis

**思路：因为Mybatis中，我们的mapper（DAO）是一个接口，但是程序可以给我们生成一个代理对象，代理对象SqlSession，生成的代理对象怎么存到ioc容器中，Service注入这个对象就可以了。**

> 把mybatis-config.xml配置文件中的内容配置到applicationContext.xml配置文件中

在Spring配置文件中需要配3个东西

1. 连接池
2. SqlSesionFactory工厂，生成sqlsession，sqlsesion可以创建代理，有了代理可以将代理ioc容器中
3. 配置Dao接口包，要知道哪些包存到ioc中

<font color='red'>其实就是将mybatis-config中的配置转移到spring配置文件中，然后再配置sqlsessionfactory工厂。</font>

> applicationContext.xml

```xml
 <!--引入properties文件 jdbc-->
    <context:property-placeholder location="jdbc.properties"/>

    <!--配置c3p0连接池-->
    <!--DriverManagerDataSource只是作为一个成熟的连接池的简单替代，只不过实现相同的标准接口，不支持连接池，每次连接数据库都是创建新的连接对象，所以不要用这个了-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.user}"/>
        <property name="password" value="${jdbc.password}"/>

        <!-- c3p0连接池的私有属性 -->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!-- 关闭连接后不自动commit -->
        <property name="autoCommitOnClose" value="false"/>
        <!-- 获取连接超时时间 -->
        <property name="checkoutTimeout" value="10000"/>
        <!-- 当获取连接失败重试次数 -->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

    <!--配置SqlSessionFactory对象-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!-- 配置MyBatis全局配置文件:mybatis-config.xml -->
        <property name="configLocation" value="mybatis-config.xml"/>
    </bean>

    <!--配置扫描Dao接口包，动态实现Dao接口注入到spring容器中-->
    <bean id="scannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>-->
        <property name="basePackage" value="com.yfh.ssm.dao"/>
    </bean>

```

> 将dao注入Service中，测试是否可以获取数据

![image-20220325115747377](https://raw.githubusercontent.com/Dreameo/JavaLine/master/5_SSM/4_ssm%E6%95%B4%E5%90%88/imgs/ssmbuild-success.png)

<font color='red'>这里可能会出现一个c3p0结果错误：</font>参考链接解决：

https://blog.csdn.net/xiao_count/article/details/102870993

> 测试成功了，可以完成后续步骤了，展示页面。



## 9、出现问题

> 定制扫描规则的理解

<font color='red'>正确理解use-default-filters="false"</font> 

https://blog.csdn.net/wdr2003/article/details/84502777`

>  c3p0报错：

https://blog.csdn.net/xiao_count/article/details/102870993