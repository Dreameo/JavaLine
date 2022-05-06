package com.yfh.service;

import com.yfh.pojo.Book;

import java.util.List;

public interface BookService {
    // 添加图书操作
    void addBook(Book book);

    // 修改图书操作
    void updateBook(Book book);

    // 删除方法
    void deleteBook(String id);

    // 查询表的记录数
    int findCount();


    // 查询返回对象
    Book findBookById(String id);

    // 返回对象集合
    List<Book> findBookList();

    // 批量增加
    public void addBatchBook(List<Object[]> list);
}
