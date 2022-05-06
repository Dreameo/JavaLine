package com.yfh.dao;

import com.yfh.pojo.Book;

import java.util.List;

public interface BookDAO {
    // 添加图书操作
    void add(Book book);

    void updateBook(Book book);

    void deleteBook(String id);

    int findCount();

    Book findBookById(String id);

    List<Book> findBookList();

    void addBatchBook(List<Object[]> list);
}
