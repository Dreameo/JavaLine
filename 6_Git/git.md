## 3、 Git常用命令

### 3.1 设置用户签名

> git config --global user.name 用户名
>
> git config --global user.email 邮箱 

   签名的作用是区分不同操作者身份。用户的签名信息在每一个版本的提交信息中能够看 到，以此确认本次提交是谁做的。<font color='red'>Git 首次安装必须设置一下用户签名，否则无法提交代码</font>。<font color='red'> ※注意</font>：这里设置用户签名和将来登录 GitHub（或其他代码托管中心）的账号没有任何关系。 

如果要确认设置的<font color='red'>签名</font>和<font color='red'>邮箱</font>是否设置成功，可以在<font color='red'>图像化界面</font>用户目录下查找.gitconfig打开查看；或者在<font color='red'>git命令界面</font>输入cat ~/gitconfig

![1648049150146](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/1648049150146.png)

命令行：

![image-20220324081206831](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324081206831.png)

### 3.2 初始化本地库

> 要用git管理这个目录，就要用git初始化这个目录，使得git拥有这个项目的权限

> git init

![image-20220324082038631](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324082038631.png)



### 3.3 查看本地库状态

> git status

> vim中复制为：yy， 粘贴是vv



### 3.4 添加暂存区

> git add 文件



### 3.5 提交本地库

> git commit -m "message" 文件名



### 3.6历史版本

### 3.6.1 查看精简版版本信息

> git reflog

![image-20220324094212282](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324094212282.png)

### 3.6.2 查看详细版本信息

> git log

![image-20220324094232022](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324094232022.png)

### 3.6.3 版本穿梭

> git reset --hard 版本号(版本完整版本的前几位即可)

![image-20220324094514050](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324094514050.png)

.git文件夹去查看head和master

![image-20220324094852200](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324094852200.png)



![image-20220324094758836](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324094758836.png)

> head -> master哪个分支 -> 哪个版本

![image-20220324094957007](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324094957007.png)

## 4、Git分支操作

### 4.1 分支

![image-20220324095309893](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324095309893.png)

### 4.2 分支命令

![image-20220324095532421](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324095532421.png)

> 查看和创建分支 git branch -v   git branch 分支名

![image-20220324095452829](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324095452829.png)

> 删除多余分支git branch -d 分支名

![image-20220324100428770](https://gitee.com/stevedream/drawingbed/raw/master/imgs/git/image-20220324100428770.png)

> 切换分支并修改分支里的内容git checkout 分支名

![image-20220324100827150](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324100827150.png)

> 再master分支合并 git merge 分支名

![](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324103832380.png)

> 冲突两个分支同时修改同一个位置同一个文件

![image-20220324103832380](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324103832380.png)

> 解决冲突,手动决定最新要添加的修改（<font color='red'>只会修改合并的那个分支，不会修改被合并分支的文件</font>），然后提交本地库（<font color='red'>不要带文件名，不然会报致命的错</font>）

特殊符号<font color='red'><<<<<<<HEAD</font> 当前分支代码<font color='red'> ======</font>合并过来的代码 <font color='red'>>>>>>>>>>>hot-fix</font>

![image-20220324104220012](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324104220012.png)

![image-20220324104814430](https://raw.githubusercontent.com/Dreameo/JavaLine/master/6_Git/img/image-20220324104814430.png)





## 5、远程仓库GitHub

> 取别名

>推送远程，推送的最小单位是分支git push 分支名
