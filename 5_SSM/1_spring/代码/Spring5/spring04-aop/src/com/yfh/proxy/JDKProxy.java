package com.yfh.proxy;

import com.yfh.dao.UserDAO;
import com.yfh.dao.impl.UserDAOImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKProxy {
    public static void main(String[] args) {
        // 1.创建接口实现类的代理对像
        Class[] interfaces = {
                UserDAO.class
        };
        UserDAO userDAO = new UserDAOImpl();

        UserDAO dao = (UserDAO) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDAOProxy(userDAO));
        int reslut = dao.add(1, 3);
        System.out.println(reslut);

    }
}

/**
 * 代理类
 */

class UserDAOProxy implements InvocationHandler {
    // 1. 把要代理的对象，传递过来
    // 通过有参构造
    private Object obj;

    public UserDAOProxy(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在增强方法之前的处理
        System.out.println("增强方法之前的执行..." + method.getName() + ":传递的参数..." + Arrays.toString(args));

        // 增强的方法
        Object res = method.invoke(obj, args);

        // 在增强方法之后的处理
        System.out.println("增强方法之后的执行..." + obj);

        return res;
    }
}
