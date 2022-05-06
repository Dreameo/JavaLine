package com.yfh.myssm.trans;

import com.yfh.myssm.basedao.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    //    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    // 开启事务 connection.setAutoCommit--->焦点怎么获取connection对象
    public static void beginTrans() throws SQLException {
        ConnUtil.getConnection().setAutoCommit(false);
    }


    // 提交事务
    public static void commit() throws SQLException {
        ConnUtil.getConnection().commit();
    }

    // 回滚事务

    public static void rollback() throws SQLException {
        ConnUtil.getConnection().rollback();
    }

}


/**
 * version1.0 : 每个方法的过程都类似----》转移到ConnUtil中去（version2.0）
 * <p>
 * // 开启事务 connection.setAutoCommit--->焦点怎么获取connection对象
 * public void beginTrans() throws SQLException {
 * Connection conn = threadLocal.get();
 * if (conn != null) {
 * conn.setAutoCommit(false);
 * } else {
 * conn = ConnUtil.getConnection();
 * threadLocal.set(conn);
 * }
 * }
 * <p>
 * <p>
 * // 提交事务
 * public void commit() throws SQLException {
 * Connection conn = threadLocal.get();
 * if (conn == null) {
 * conn = ConnUtil.getConnection();
 * threadLocal.set(conn);
 * }
 * conn.commit();
 * }
 * <p>
 * // 回滚事务
 * <p>
 * public void rollback() throws SQLException {
 * Connection conn = threadLocal.get();
 * if(conn == null) {
 * conn = ConnUtil.getConnection();
 * threadLocal.set(conn);
 * }
 * conn.rollback();
 * }
 */
