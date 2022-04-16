# MyBlog

传统的三层架构包括**表示层、应用层和存储层**。表示层依赖于应用层，应用层依赖于存储层。因此，实体对象、DTO对象或数据库成为依赖的核心，这是数据驱动架构的典型特征。.领域驱动设计应该以领域模型为核心。清洁架构、六边形架构和洋葱架构提供领域驱动的实现架构。



## 1、数据库和实体类中，缩略名的作用

```java
   /** 评论和meta标签类
     * 项目缩略名   
     */
    private String slug;
```



## 2、MyBatis动态SQL

```xml
<!-- 根据条件获取项目 -->
    <select id="getMetasByCond" resultType="cn.luischen.model.MetaDomain"
                parameterType="cn.luischen.dto.cond.MetaCond">
        SELECT
        <include refid="BASE_COLUMN"/>
        FROM
        <include refid="BASE_TABLE"/> AS m
        <where>
            <if test="name != null">
                AND m.name = #{name,jdbcType=VARCHAR}
            </if>
            <if test="type != null">
                AND m.type = #{type, jdbcType=VARCHAR}
            </if>
        </where>
        ORDER BY m.sort
    </select>
```



### 3、 分页插件PageHelper

```xml
<!--引入PageHelper分页插件 → PageHelper-->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.0.0</version>
</dependency>
```



```yaml
#pagehelper
pagehelper:
    helperDialect: mysql  # 分页插件会自动检测当前的数据库链接，自动选择合适的分页方式。 你可以配置helperDialect属性来指定分页插件使用哪种方言。配置时，可以使用下面的缩写值：oracle,mysql,mariadb,sqlite,hsqldb,postgresql,db2,sqlserver,informix,h2,sqlserver2012,derby 特别注意：使用 SqlServer2012 数据库时，需要手动指定为 sqlserver2012，否则会使用 SqlServer2005 的方式进行分页。
    reasonable: true # reasonable：分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页，pageNum>pages(超过总数时)，会查询最后一页。默认false 时，直接根据参数进行查询。
    supportMethodsArguments: true
    params: count=countSql  # 为了支持startPage(Object params)方法，增加了该参数来配置参数映射，用于从对象中根据属性名取值， 可以配置 pageNum,pageSize,count,pageSizeZero,reasonable，不配置映射的用默认值， 默认值为pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero。
    returnPageInfo: check
```





## 4、thymeleaf 强校验

springboot使用thymeleaf模板引擎时，发现使用要求非常严格，比如`<input type="text" />`单标签结尾便会报错，只有这样`<input type="text" > </input>`才可以。

为了解决这种严格的使用规则，首先可以添加如下依赖：

```xml
<!-- https://mvnrepository.com/artifact/net.sourceforge.nekohtml/nekohtml -->
<dependency>
    <groupId>net.sourceforge.nekohtml</groupId>
    <artifactId>nekohtml</artifactId>
    <version>1.9.22</version>
</dependency>
```

然后再配置文件中添加：

```yaml
spring:
  thymeleaf:
      mode: LEGACYHTML5 # 去掉thymeleaf的严格的模板校验
```





## 5、@Transactional(rollbackFor = BusinessException.class)





## 6、@Cacheable(value = "atricleCaches", key = "'articlesByCond_' + #p1 + 'type_' + #p0.type")



> 使用缓存的步骤

```java
// 1、开启基于注解的缓存，使用 @EnableCaching 标识在 SpringBoot 的主启动类上。
    @EnableCaching
    public class MySiteApplication {

        public static void main(String[] args) {
            SpringApplication.run(MySiteApplication.class, args);
        }
    }


// 2、标注缓存注解即可Cacheable
@Override
@Cacheable(value = "atricleCaches", key = "'recentlyArticle_' + #p0") // key: 可以使用SpEL（Spring Expression Language），即Spring表达式语言，是比JSP的EL更强大的一种表达式语言。
public PageInfo<ContentDomain> getRecentlyArticle(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<ContentDomain> recentlyArticle = contentDao.getRecentlyArticle();
    PageInfo<ContentDomain> pageInfo = new PageInfo<>(recentlyArticle);
    return pageInfo;
}
// 这里使用 @Cacheable 注解就可以将运行结果缓存，以后查询相同的数据，直接从缓存中取，不需要调用方法。
// 当我们要使用root对象的属性作为key时我们也可以将“#root”省略，因为Spring默认使用的就是root对象的属性。
```

 ![image-20220402233949444](https://raw.githubusercontent.com/Dreameo/JavaLine/master/myblog/imgs/cache_spel.png)

![image-20220402234528115](https://raw.githubusercontent.com/Dreameo/JavaLine/master/myblog/imgs/image-sple.png)



## 7、druid的PSCache

>  MySQL 5.7以前的确是不支持PSCache的，但是从MySQL5.7开始，官方支持游标，所以建议开启PSCache

druid的连接池配置中有PrepareStatementCache的配置，该信息解决了sql语句开源被预编译，并且保存在PrepareStatement这个对象中，而这个对象的存储就在PrepareStatementCache，对于Oracle可以绕过数据库编译，有很大的提升，但是对于mysql，没有那么明显。这个值的设置不是越大越好，PSCache会占用JVM，占用量=连接数*PSCache设置的大小*每个PSCache占用的内存。



> druid 相关配置 ： https://www.cnblogs.com/cymiao/p/8432681.html





## 8、 logback-spring

> 简介和使用： https://blog.csdn.net/white_ice/article/details/85065219
>
> 1. [Logback](http://logback.qos.ch/) 是log4j 框架的作者开发的新一代日志框架，它效率更高、能够适应诸多的运行环境，同时天然支持SLF4J
> 2. Logback的定制性更加灵活，同时也是spring boot的内置日志框架

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出 -->
<!-- scan:当此属性设置为true时，配置文档如果发生改变，将会被重新加载，默认值为true -->
<!-- scanPeriod:设置监测配置文档是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。
                 当scan为true时，此属性生效。默认的时间间隔为1分钟。 -->
<!-- debug:当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。 -->
<configuration  scan="true" scanPeriod="10 seconds">


    <!-- name的值是变量的名称，value的值时变量定义的值。通过定义的值会被插入到logger上下文中。定义后，可以使“${}”来使用变量。 -->
    <!-- <property name="log.path" value="F:/logs" />-->
    <springProperty scope="context" name="log.path" source="logging.path" default="/home/logs/"/>
    <contextName>logback</contextName>

    <!-- 日志格式和颜色渲染 -->
    <!-- 彩色日志依赖的渲染类 -->
    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter" />
    <conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter" />
    <conversionRule conversionWord="wEx" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter" />
    <!-- 彩色日志格式 -->
    <property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>

    <!--输出到控制台-->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <!--此日志appender是为开发使用，只配置最底级别，控制台输出的日志级别是大于或等于此级别的日志信息-->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>debug</level>
        </filter>
        <encoder>
            <Pattern>${CONSOLE_LOG_PATTERN}</Pattern>
            <!-- 设置字符集 -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!--输出到文档-->
    <!--level为 DEBUG 日志，时间滚动输出  -->
    <appender name="DEBUG_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${log.path}/web_debug.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 日志归档 -->
            <fileNamePattern>${log.path}/web-debug-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录debug级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>debug</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!--level为 INFO 日志，时间滚动输出  -->
    <appender name="INFO_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${log.path}/web_info.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 每天日志归档路径以及格式 -->
            <fileNamePattern>${log.path}/web-info-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录info级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>info</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!--level为 WARN 日志，时间滚动输出  -->
    <appender name="WARN_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${log.path}/web_warn.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/web-warn-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录warn级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>warn</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!--level为 ERROR 日志，时间滚动输出  -->
    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${log.path}/web_error.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/web-error-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录ERROR级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!--
        <logger>用来设置某一个包或者具体的某一个类的日志打印级别、
        以及指定<appender>。<logger>仅有一个name属性，
        一个可选的level和一个可选的addtivity属性。
        name:用来指定受此logger约束的某一个包或者具体的某一个类。
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
              还有一个特俗值INHERITED或者同义词NULL，代表强制执行上级的级别。
              如果未设置此属性，那么当前logger将会继承上级的级别。
        addtivity:是否向上级logger传递打印信息。默认是true。
        <logger name="org.springframework.web" level="info"/>
        <logger name="org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor" level="INFO"/>
    -->

    <!--
        使用mybatis的时候，sql语句是debug下才会打印，而这里我们只配置了info，所以想要查看sql语句的话，有以下两种操作：
        第一种把<root level="info">改成<root level="DEBUG">这样就会打印sql，不过这样日志那边会出现很多其他消息
        第二种就是单独给dao下目录配置debug模式，代码如下，这样配置sql语句会打印，其他还是正常info级别：
        【logging.level.org.mybatis=debug logging.level.dao=debug】
     -->

    <!--
        root节点是必选节点，用来指定最基础的日志输出级别，只有一个level属性
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
        不能设置为INHERITED或者同义词NULL。默认是DEBUG
        可以包含零个或多个元素，标识这个appender将会添加到这个logger。
    -->

    <!--开发环境:打印控制台-->
    <springProfile name="dev">
        <root level="info">
            <appender-ref ref="CONSOLE" />
            <appender-ref ref="DEBUG_FILE" />
            <appender-ref ref="INFO_FILE" />
            <appender-ref ref="WARN_FILE" />
            <appender-ref ref="ERROR_FILE" />
        </root>
    </springProfile>

    <springProfile name="prod">
        <root level="info">
            <appender-ref ref="CONSOLE" />
            <appender-ref ref="DEBUG_FILE" />
            <appender-ref ref="INFO_FILE" />
            <appender-ref ref="ERROR_FILE" />
            <appender-ref ref="WARN_FILE" />
        </root>
    </springProfile>

    <springProfile name="test">
        <root level="info">
            <appender-ref ref="CONSOLE" />
            <appender-ref ref="DEBUG_FILE" />
            <appender-ref ref="INFO_FILE" />
            <appender-ref ref="ERROR_FILE" />
            <appender-ref ref="WARN_FILE" />
        </root>
    </springProfile>

</configuration>

```

> 日志保存地址

![image-20220403001947875](https://raw.githubusercontent.com/Dreameo/JavaLine/master/myblog/imgs/log-position.png)



## 9、时间戳格式转换



```java
    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @return
     */
    public static String fmtdate(Integer unixTime) {
        return fmtdate(unixTime, "yyyy-MM-dd");
    }

    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @param patten
     * @return
     */
    public static String fmtdate(Integer unixTime, String patten) {
        if (null != unixTime && StringUtils.isNotBlank(patten)) {
            return DateKit.formatDateByUnixTime(unixTime, patten);
        }
        return "";
    }
```







## 10、网页公共工具类

```java
// 用拦截器去给每个页面添加工具类，页面就可以直接调用工具类方法了。
```





## 11、gravatar全球通用头像





## 12、IPKit ip地址工具





## 13、 输入密码已经错误超过3次，请10分钟后尝试

> 十分钟怎么计算

```java
cache.hset("login_error_count", ip, error_count, 10 * 60); // 加入ip的过滤, 10分钟
```

## 14、Cookie 的setPath 方法

> https://blog.csdn.net/budong282712018/article/details/15215703?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_default&utm_relevant_index=1



## 15、AOP切入点

> 请求的日志

```java
@Pointcut("execution(public * cn.luischen.controller..*.*(..))")  // 切入点
    public void webLog(){}

// expression="execution(* com.controller.*.*(..))"  切入点为com.controller包下的类的任意方法
// expression="execution(* com.controller..*.*(..))"  切入点为com.controller包下子包的类的任意方法
```

https://www.cnblogs.com/feng9exe/p/10949718.html

> springboot引入aop
>
> https://blog.csdn.net/lmb55/article/details/82470388/

## 16、ThreadLocal

> https://www.cnblogs.com/fsmly/p/11020641.html
>
> https://blog.csdn.net/u010445301/article/details/111322569





## 17、异常处理

> spring异常处理：https://www.cnblogs.com/lvbinbin2yujie/p/10574812.html

> springboot全局异常：https://www.cnblogs.com/xuwujing/p/10933082.html



## 18、事务回滚

> https://blog.51cto.com/u_9177933/2978291



```text
1. 需要在启动类上添加@EnableTransactionManagement注解。

2. 当作用于类上时，该类的所有 public 方法将都具有该类型的事务属性，同时，我们也可以在方法级别使用该标注来覆盖类级别的定义。

3. 在项目中，@Transactional(rollbackFor=Exception.class)，如果类加了这个注解，那么这个类里面的方法抛出异常，就会回滚，数据库里面的数据也会回滚。

4. 在@Transactional注解中如果不配置rollbackFor属性,那么事务只会在遇到RuntimeException的时候才会回滚,加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚。

```

## 19、拦截器

定义了拦截器，需要在配置文件中进行注册，才能生效