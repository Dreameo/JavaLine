## Servlet

### 1. 设置编码

```text
tomcat8之前，设置编码：
  1)get请求方式：
    //get方式目前不需要设置编码（基于tomcat8）
    //如果是get请求发送的中文数据，转码稍微有点麻烦（tomcat8之前）
    String fname = request.getParameter("fname");
    //1.将字符串打散成字节数组
    byte[] bytes = fname.getBytes("ISO-8859-1");
    //2.将字节数组按照设定的编码重新组装成字符串
    fname = new String(bytes,"UTF-8");
  2)post请求方式：
    request.setCharacterEncoding("UTF-8");
tomcat8开始，设置编码，只需要针对post方式
    request.setCharacterEncoding("UTF-8");

```

**注意**：
       **需要注意的是，设置编码(post)这一句代码必须在所有的获取参数动作之前**

### 2. Servlet的继承关系 - 重点查看的是服务方法（service()）

```text
2. Servlet的继承关系 - 重点查看的是服务方法（service()）
    1. 继承关系
      javax.servlet.Servlet接口
          javax.servlet.GenericServlet抽象类
              javax.servlet.http.HttpServlet抽象子类

    2. 相关方法
      javax.servlet.Servlet接口:
        void init(config) - 初始化方法
        void service(request,response) - 服务方法
        void destory() - 销毁方法

      javax.servlet.GenericServlet抽象类：
        void service(request,response) - 仍然是抽象的

      javax.servlet.http.HttpServlet 抽象子类：
        void service(request,response) - 不是抽象的
        1. String method = req.getMethod(); 获取请求的方式
        2. 各种if判断，根据请求方式不同，决定去调用不同的do方法
            if (method.equals("GET")) {
                this.doGet(req,resp);
            } else if (method.equals("HEAD")) {
                this.doHead(req, resp);
            } else if (method.equals("POST")) {
                this.doPost(req, resp);
            } else if (method.equals("PUT")) {
        3. 在HttpServlet这个抽象类中，do方法都差不多:
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String protocol = req.getProtocol();
            String msg = lStrings.getString("http.method_get_not_supported");
            if (protocol.endsWith("1.1")) {
                resp.sendError(405, msg);
            } else {
                resp.sendError(400, msg);
            }
        }
```

 **3.小结：**
    **1) 继承关系： HttpServlet -> GenericServlet -> Servlet**
    **2) Servlet中的核心方法： init() , service() , destroy()**
    **3) 服务方法： 当有请求过来时，service方法会自动响应（其实是tomcat容器调用的）**
            **在HttpServlet中我们会去分析请求的方式：到底是get、post、head还是delete等等**
            **然后再决定调用的是哪个do开头的方法**
            **那么在HttpServlet中这些do方法默认都是405的实现风格-要我们子类去实现对应的方法，否则默认会报405错误**

    4) **因此，我们在新建Servlet时，我们才会去考虑请求方法，从而决定重写哪个do方法**

### 3. Servlet的生命周期

```text
3. Servlet的生命周期
    1） 生命周期：从出生到死亡的过程就是生命周期。对应Servlet中的三个方法：init(),service(),destroy()
    2） 默认情况下：
        第一次接收请求时，这个Servlet会进行实例化(调用构造方法)、初始化(调用init())、然后服务(调用service())
        从第二次请求开始，每一次都是服务
        当容器关闭时，其中的所有的servlet实例会被销毁，调用销毁方法
    3） 通过案例我们发现：
        - Servlet实例tomcat只会创建一个，所有的请求都是这个实例去响应。
        - 默认情况下，第一次请求时，tomcat才会去实例化（反射），初始化，然后再服务.这样的好处是什么？ 
        																提高系统的启动速度 。 这样的缺点是什么？ 第一次请求时，耗时较长。
        - 因此得出结论： 如果需要提高系统的启动速度，当前默认情况就是这样。如果需要提高响应速度，我们应该设置Servlet的初始化时机。
    4） Servlet的初始化时机：
        - 默认是第一次接收请求时，实例化，初始化
        - 我们可以通过<load-on-startup>来设置servlet启动的先后顺序,数字越小，启动越靠前，最小值0
    5） Servlet在容器中是：单例的、线程不安全的
        - 单例：所有的请求都是同一个实例去响应
        - 线程不安全：一个线程需要根据这个实例中的某个成员变量值去做逻辑判断。但是在中间某个时机，另一个线程改变了这个成员变量的值，从而导致第一个线程的执行路径发生了变化
        - 我们已经知道了servlet是线程不安全的，给我们的启发是： 尽量的不要在servlet中定义成员变量。如果不得不定义成员变量，那么不要去：①不要去修改成员变量的值 ②不要去根据成员变量的值做一些逻辑判断

```

**线程不安全：**

![image-20220302231503253](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220302231503253.png)

**Servlet可以多个mapping：**

![image-20220302210915036](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220302210915036.png)



### 4. Http协议

```text
4. Http协议
    1） Http 称之为 超文本传输协议
    2） Http是无状态的
    3） Http请求响应包含两个部分：请求和响应
      - 请求：
        请求包含三个部分： 1.请求行 ； 2.请求消息头 ； 3.请求主体
        1)请求行包含是三个信息： 1. 请求的方式 ； 2.请求的URL ； 3.请求的协议（一般都是HTTP1.1）
        
        2)请求消息头中包含了很多客户端需要告诉服务器的信息，比如：我的浏览器型号、版本、我能接收的内容的类型、我给你发的内容的类型、内容的长度等等
        3)请求体，三种情况
          get方式，没有请求体，但是有一个queryString
          post方式，有请求体，form data
          json格式，有请求体，request payload
      - 响应：
        响应也包含三个部分： 1. 响应响应行 ； 2.响应响应头 ； 3.响应体
        1)响应行包含三个信息：1.协议 2.响应状态码(200) 3.响应状态(ok)
        2)响应头：包含了服务器的信息；服务器发送给浏览器的信息（内容的媒体类型、编码、内容长度等）
        3)响应体：响应的实际内容（比如请求add.html页面时，响应的内容就是<html><head><body><form....）
```

### 5. 会话

```text
5. 会话
    1） Http是无状态的
        - HTTP 无状态 ：服务器无法判断这两次请求是同一个客户端发过来的，还是不同的客户端发过来的
        - 无状态带来的现实问题：第一次请求是添加商品到购物车，第二次请求是结账；如果这两次请求服务器无法区分是同一个用户的，那么就会导致混乱
        - 通过会话跟踪技术来解决无状态的问题。

    2） 会话跟踪技术
        - 客户端第一次发请求给服务器，服务器获取session，获取不到，则创建新的，然后响应给客户端
        - 下次客户端给服务器发请求时，会把sessionID带给服务器，那么服务器就能获取到了，那么服务器就判断这一次请求和上次某次请求是同一个客户端，从而能够区分开客户端
        - 常用的API：
          request.getSession() -> 获取当前的会话，没有则创建一个新的会话
          request.getSession(true) -> 效果和不带参数相同
          request.getSession(false) -> 获取当前会话，没有则返回null，不会创建新的

          session.getId() -> 获取sessionID
          session.isNew() -> 判断当前session是否是新的
          session.getMaxInactiveInterval() -> session的非激活间隔时长，默认1800秒
          session.setMaxInactiveInterval()
          session.invalidate() -> 强制性让会话立即失效
          ....

    3） session保存作用域
      - session保存作用域是和具体的某一个session对应的
      - 常用的API：
        void session.setAttribute(k,v)
        Object session.getAttribute(k)
        void removeAttribute(k)
```

![image-20220302233308592](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220302233308592.png)

![image-20220302235843839](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220302235843839.png)

### 6. 服务器内部转发以及客户端重定向

```text
6. 服务器内部转发以及客户端重定向
    1） 服务器内部转发 : request.getRequestDispatcher("...").forward(request,response);
      - 一次请求响应的过程，对于客户端而言，内部经过了多少次转发，客户端是不知道的
      - 地址栏没有变化
    2） 客户端重定向： response.sendRedirect("....");
      - 两次请求响应的过程。客户端肯定知道请求URL有变化
      - 地址栏有变化

```

**服务器内部转发：**

![image-20220303081240981](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220303081240981.png)



**客户端重定向：**

![image-20220303081329403](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220303081329403.png)

`Servlet从3.0开始支持注解，@Servlet("/demo06"),就可以把web.xml删除了`



### 7. Thymeleaf - 视图模板技术

![image-20220303085038840](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220303085038840.png)

```text

7. Thymeleaf - 视图模板技术
    1） 添加thymeleaf的jar包
    2） 新建一个Servlet类ViewBaseServlet
    3） 在web.xml文件中添加配置(上下文参数)
       - 配置前缀 view-prefix
       - 配置后缀 view-suffix
    4） 使得我们的Servlet继承ViewBaseServlet

    5） 根据逻辑视图名称 得到 物理视图名称
    //此处的视图名称是 index
    //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
    //逻辑视图名称 ：   index
    //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
    //所以真实的视图名称是：      /       index       .html
    super.processTemplate("index",request,response);
    6） 使用thymeleaf的标签
      th:if   ,  th:unless   , th:each   ,   th:text
```

```java
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList();

        // 保存到作用域
        HttpSession session = req.getSession();
        session.setAttribute("fruitList", fruitList);
        // 此处的视图名称为：index
        // 那么thymeleaf会将这个 逻辑视图名称  对应到  物理视图名称  上去
        // 逻辑视图名称：index
        // 物理视图名称： view-prefix + 逻辑视图名称 + view-suffix
        // 所以真实的视图名称是  /       index     .html
        super.processTemplate("index", req, resp);
    }
}

```



### 响应码

```text
// 200 : 正常响应
// 404 : 找不到资源
// 405 : 请求方式不支持
// 500 : 服务器内部错误
// 302 ： 客户端重定向
```

## 出现的问题：

### 问题1：module改名，重新部署出现问题（未解决）

![image-20220303124207442](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220303124207442.png)

### 解决问题1：

![image-20220304083827625](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220304083827625.png)

## Servlet保存作用域

### 1. request保存作用域

![image-20220303162316808](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220303162316808.png)



### 2. session保存作用域

`一次会话范围有效`

![image-20220303162815224](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220303162815224.png)

### 3. application保存作用域

![image-20220303163044239](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220303163044239.png)



## Servlet路径问题

![image-20220303164824023](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220303164824023.png)

![image-20220303165805304](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220303165805304.png)



## thymeleaf出现的问题

add页面th标签失效 https://blog.51cto.com/u_13447469/3873853



## Servlet-MVC

![image-20220304153715161](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220304153715161.png)



### 过程：

将参数的获取、方法的调用、和视图的处理都提到DispatcherServlet中央控制器中

```text
review:
1. 最初的做法是： 一个请求对应一个Servlet，这样存在的问题是servlet太多了
2. 把一些列的请求都对应一个Servlet, IndexServlet/AddServlet/EditServlet/DelServlet/UpdateServlet -> 合并成FruitServlet
   通过一个operate的值来决定调用FruitServlet中的哪一个方法
   使用的是switch-case
3. 在上一个版本中，Servlet中充斥着大量的switch-case，试想一下，随着我们的项目的业务规模扩大，那么会有很多的Servlet，也就意味着会有很多的switch-case，这是一种代码冗余
   因此，我们在servlet中使用了反射技术，我们规定operate的值和方法名一致，那么接收到operate的值是什么就表明我们需要调用对应的方法进行响应，如果找不到对应的方法，则抛异常
4. 在上一个版本中我们使用了反射技术，但是其实还是存在一定的问题：每一个servlet中都有类似的反射技术的代码。因此继续抽取，设计了中央控制器类：DispatcherServlet
   DispatcherServlet这个类的工作分为两大部分：
   1.根据url定位到能够处理这个请求的controller组件：
    1)从url中提取servletPath : /fruit.do -> fruit
    2)根据fruit找到对应的组件:FruitController ， 这个对应的依据我们存储在applicationContext.xml中
      <bean id="fruit" class="com.atguigu.fruit.controllers.FruitController/>
      通过DOM技术我们去解析XML文件，在中央控制器中形成一个beanMap容器，用来存放所有的Controller组件
    3)根据获取到的operate的值定位到我们FruitController中需要调用的方法
   2.调用Controller组件中的方法：
    1) 获取参数
       获取即将要调用的方法的参数签名信息: Parameter[] parameters = method.getParameters();
       通过parameter.getName()获取参数的名称；
       准备了Object[] parameterValues 这个数组用来存放对应参数的参数值
       另外，我们需要考虑参数的类型问题，需要做类型转化的工作。通过parameter.getType()获取参数的类型
    2) 执行方法
       Object returnObj = method.invoke(controllerBean , parameterValues);
    3) 视图处理
       String returnStr = (String)returnObj;
       if(returnStr.startWith("redirect:")){
        ....
       }else if.....
```



### 错误

**解决**：**integer整数参数与获取到的String类型不匹配，需要进行判断并强转**

![image-20220305104921954](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220305104921954.png)

**解决：主义applicationContext.xml单词不要写错**



![image-20220305193849148](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220305193849148.png)

### servlet 初始化方法

```text
1. 再次学习Servlet的初始化方法
 1) Servlet生命周期：实例化、初始化、服务、销毁
 2) Servlet中的初始化方法有两个：init() , init(config)
   其中带参数的方法代码如下：
   public void init(ServletConfig config) throws ServletException {
     this.config = config ;
     init();
   }
   另外一个无参的init方法如下：
   public void init() throws ServletException{
   }
   如果我们想要在Servlet初始化时做一些准备工作，那么我们可以重写init方法
   我们可以通过如下步骤去获取初始化设置的数据
   - 获取config对象：ServletConfig config = getServletConfig();
   - 获取初始化参数值： config.getInitParameter(key);
 3) 在web.xml文件中配置Servlet
    <servlet>
        <servlet-name>Demo01Servlet</servlet-name>
        <servlet-class>com.atguigu.servlet.Demo01Servlet</servlet-class>
        <init-param>
            <param-name>hello</param-name>
            <param-value>world</param-value>
        </init-param>
        <init-param>
            <param-name>uname</param-name>
            <param-value>jim</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>Demo01Servlet</servlet-name>
        <url-pattern>/demo01</url-pattern>
    </servlet-mapping>
 4) 也可以通过注解的方式进行配置：
 @WebServlet(urlPatterns = {"/demo01"} ,
     initParams = {
         @WebInitParam(name="hello",value="world"),
         @WebInitParam(name="uname",value="jim")
     })

2. 学习Servlet中的ServletContext和<context-param>
    1) 获取ServletContext，有很多方法
       在初始化方法中： ServletContxt servletContext = getServletContext();
       在服务方法中也可以通过request对象获取，也可以通过session获取：
       request.getServletContext(); session.getServletContext()
    2) 获取初始化值：
       servletContext.getInitParameter();

3. 什么是业务层
    1) Model1和Model2
    MVC : Model（模型）、View（视图）、Controller（控制器）
    视图层：用于做数据展示以及和用户交互的一个界面
    控制层：能够接受客户端的请求，具体的业务功能还是需要借助于模型组件来完成
    模型层：模型分为很多种：有比较简单的pojo/vo(value object)，有业务模型组件，有数据访问层组件
        1) pojo/vo : 值对象
        2) DAO ： 数据访问对象
        3) BO ： 业务对象

    2) 区分业务对象和数据访问对象：
      1） DAO中的方法都是单精度方法或者称之为细粒度方法。什么叫单精度？一个方法只考虑一个操作，比如添加，那就是insert操作、查询那就是select操作....
      2） BO中的方法属于业务方法，也实际的业务是比较复杂的，因此业务方法的粒度是比较粗的
          注册这个功能属于业务功能，也就是说注册这个方法属于业务方法。
          那么这个业务方法中包含了多个DAO方法。也就是说注册这个业务功能需要通过多个DAO方法的组合调用，从而完成注册功能的开发。
          注册：
                1. 检查用户名是否已经被注册 - DAO中的select操作
                2. 向用户表新增一条新用户记录 - DAO中的insert操作
                3. 向用户积分表新增一条记录（新用户默认初始化积分100分） - DAO中的insert操作
                4. 向系统消息表新增一条记录（某某某新用户注册了，需要根据通讯录信息向他的联系人推送消息） - DAO中的insert操作
                5. 向系统日志表新增一条记录（某用户在某IP在某年某月某日某时某分某秒某毫秒注册） - DAO中的insert操作
                6. ....
    3) 在库存系统中添加业务层组件

4. IOC
    1) 耦合/依赖
      依赖指的是某某某离不开某某某
      在软件系统中，层与层之间是存在依赖的。我们也称之为耦合。
      我们系统架构或者是设计的一个原则是： 高内聚低耦合。
      层内部的组成应该是高度聚合的，而层与层之间的关系应该是低耦合的，最理想的情况0耦合（就是没有耦合）
    2) IOC - 控制反转 / DI - 依赖注入






```

### 事务

DAO层最好不要用try，不然外层抓不到异常

![image-20220306093822171](G:\development\4_javaWeb\笔记\Servlet.assets\image-20220306093822171.png)

### 过滤器

过滤器的应用：

设置编码格式，事务管理



### 监听器

ioc容器应该在程序启动的时候就创建，等程序要使用对象的时候就赋予值，这样保证运行的性能。

取舍：宁愿启动慢一点、运行时性能好一点。
