package com.yfh.dao.impl;

import com.yfh.dao.BookDAO;
import com.yfh.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

    // 注入JdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(Book book) {
        String sql = "INSERT INTO t_book VALUES(?, ?, ?)"; // sql语句
        Object[] args = {book.getUserId(), book.getUserName(), book.getuStatus()}; // 调用方法实现, 用数组传入可变参数中
        int update = jdbcTemplate.update(sql,args );
        System.out.println("影响行数：update :\t" + update);
    }

    @Override
    public void updateBook(Book book) {
        String sql = "UPDATE t_book SET username = ?, u_status = ? WHERE user_id = ?";
        Object[] args = {book.getUserName(), book.getuStatus(), book.getUserId()};
        int update = jdbcTemplate.update(sql, args);
        System.out.println(update);
    }

    @Override
    public void deleteBook(String id) {
        String sql = "DELETE FROM t_book WHERE user_id = ?";
        int update = jdbcTemplate.update(sql, id);
        System.out.println(update);
    }

    @Override
    public int findCount() {
        String sql = "SELECT COUNT(*) FROM t_book";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    @Override
    public Book findBookById(String id) {
        String sql = "SELECT * FROM t_book WHERE user_id = ?";
        Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class), id);
        return book;
    }

    @Override
    public List<Book> findBookList() {
        String sql = "SELECT * FROM t_book";
        List<Book> bookList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
        return bookList;
    }

    @Override
    public void addBatchBook(List<Object[]> list) {
        String sql = "INSERT INTO t_book VALUES(?, ?, ?)";
        int[] ints = jdbcTemplate.batchUpdate(sql, list);// 低层就是遍历list，每条数据调用insert语句
        System.out.println(Arrays.toString(ints));
    }

    /**
     * 批量修改：
     * 跟批量增加是一样的语句
     * 批量删除
     * 方法也是batchUpdate，三者就是传入参数不一样
     */
}
