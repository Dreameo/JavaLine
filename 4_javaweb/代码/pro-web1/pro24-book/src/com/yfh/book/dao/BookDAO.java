package com.yfh.book.dao;

import com.yfh.book.pojo.Book;

import java.util.List;

public interface BookDAO {

//    List<Book> getBookList(Integer minPrice, Integer maxPrice, Integer pageNo);

    /**
     * 查询所有
     */
    List<Book> getBookList();

    Book getBookById(Integer BookId);
}
