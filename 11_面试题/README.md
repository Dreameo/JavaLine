### Java怎么判断字符串为空

#### 1. 关于null 和 “”的区别

- null表示不指向任何东西，调用的话会报空指针异常；空字符串""表示一个长度为0的字符串，调用是安全的。
- null不是对象，所以null没有分配空间；而空字符串""代表了一个实例化的对象，分配了空间。
- 注意：判断一个字符串是否为空，首先就要确保他不是null，然后再判断他的长度。

```java
String str = xxx;
if(str != null && str.length() != 0) { }
```

#### 2. 例子

String str1 = null;  str引用为空
String str2 = “”;  str引用一个空串
str1还不是一个实例化的对象，而str2已经实例化。
对象用equals比较，null用等号比较。
如果str1=null;下面的写法错误：
if (str1.equals("") || str1==null) //如果这样写就会报空指针异常错误
正确的写法是
if(str1 == null || str1.equals("")) //先判断是不是对象，如果是，再判断是不是空字符串



#### 3. Java判断字符串是否为空的三种方法

- 方法一：if (str == null || “”.equals(str))直观，方便，效率低；
- 方法二：if (str == null || str.length()<=0) 效率高
- 方法三：if (str == null || str.isEmpty())效率高

由于第三种方法是java SE 6.0以后才得到应用的，在早期的版本中运行可能会出现兼容性问题，推荐使用方法二。



### 为什么重写equals，一定要重写hashcode

来源：https://blog.csdn.net/qq_38923376/article/details/89208983?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_default&utm_relevant_index=2



equals和hashcode是java.lang.Object类的两个重要的方法，在实际应用中常常需要重写这两个方法，但至于为什么重写这两个方法很多人都搞不明白。以下是我的一些个人理解。
一：需要重写的情况

加入到HashSet中的自定义类对象，为确保他们不重复，需要对他们的类重写equals() 和 hashcode()的方法。

二：为什么要重写

如果不重写equals() 方法，相同的内容不同引用的对象会被当做不同的对象被加入到hashset中
Object类默认的equals比较规则就是比较两个对象的内存地址。而hashcode是本地方法，java的内存是安全的，因此无法根据散列码得到对象的内存地址，但实际上，hashcode是根据对象的内存地址经哈希算法得来的。
现在有两个Student对象：

```java
Student s1=new Student("小明",18);

Student s2=new Student("小明",18);
```


假如只重写equals而不重写hashcode，那么Student类的hashcode方法就是Object默认的hashcode方法，由于默认的hashcode方法是根据对象的内存地址经哈希算法得来的，显然此时s1!=s2,故两者的hashcode不一定相等。

然而重写了equals，且s1.equals(s2)返回true，根据hashcode的规则，两个对象相等其哈希值一定相等，所以矛盾就产生了，因此重写equals一定要重写hashcode，而且从Student类重写后的hashcode方法中可以看出，重写后返回的新的哈希值与Student的两个属性有关。

以下是关于hashcode的一些规定：

两个对象相等，hashcode一定相等

两个对象不等，hashcode不一定不等

hashcode相等，两个对象不一定相等

hashcode不等，两个对象一定不等

由于为了提高程序的效率才实现了hashcode方法，先进行hashcode的比较，如果不同，那没就不必在进行equals的比较了，这样就大大减少了equals比较的次数，这对比需要比较的数量很大的效率提高是很明显的。

### ArrayList源码分析
https://blog.csdn.net/Drink_23/article/details/108720801?spm=1001.2101.3001.6650.5&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-5.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-5.pc_relevant_default&utm_relevant_index=10
ArrayList在不指定容量的情况下创建出来的 ArrayList 是一个 [] 空数组，当这种 ArrayList 被添加数据时，会直接将内部数组的大小扩容至默认的容量大小 10（jdk8），如果指定容量大小为 1，扩容之后会是 2，其他的普遍扩容，elementData 的容量都是原来的 1.5 倍大小，
对于一些比较大的 ArrayList ，在扩容的时候，也进行了适当的限制，减小了由于数组过大而引发 OOM 出现的概率；








