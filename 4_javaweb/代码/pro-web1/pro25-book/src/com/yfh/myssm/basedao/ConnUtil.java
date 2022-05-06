package com.yfh.myssm.basedao;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnUtil {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
//    public static final String DRIVER = "com.mysql.jdbc.Driver";
//    public static final String URL = "jdbc:mysql://localhost:13306/fruitdb?useUnicode=true&characterEncoding=utf-8&useSSL=false";
//    public static final String USER = "root";
//    public static final String PWD = "root";

    public static Connection createConnection() {
        try {
            Properties pros = new Properties();
            InputStream is = ConnUtil.class.getClassLoader().getResourceAsStream("druid_dev.properties");
            pros.load(is);
            DataSource dataSource = DruidDataSourceFactory.createDataSource(pros);
            return dataSource.getConnection();
//            //1.加载驱动
//            Class.forName(DRIVER);
//            //2.通过驱动管理器获取连接对象
//            return DriverManager.getConnection(URL, USER, PWD);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Connection getConnection() {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = createConnection();
            threadLocal.set(conn);
        }
        return conn;
    }

    public static void CloseConn() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null) {
            return;
        }
        if (!conn.isClosed()) {
            conn.close();
            threadLocal.set(null);
        }
    }

}
