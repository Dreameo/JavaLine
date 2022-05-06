package com.yfh.book.service.impl;

import com.yfh.book.dao.BookDAO;
import com.yfh.book.pojo.Book;
import com.yfh.book.service.BookService;
import com.yfh.myssm.basedao.BaseDAO;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDAO bookDAO;
    @Override
    public List<Book> getBookList() {
        return bookDAO.getBookList();
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookDAO.getBookById(bookId);
    }
}
