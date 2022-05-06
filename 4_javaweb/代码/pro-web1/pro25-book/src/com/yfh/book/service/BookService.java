package com.yfh.book.service;

import com.yfh.book.pojo.Book;

import java.util.List;

public interface BookService {

    List<Book> getBookList();

    Book getBookById(Integer bookId);
}
