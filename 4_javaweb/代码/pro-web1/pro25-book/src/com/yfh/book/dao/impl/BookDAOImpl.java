package com.yfh.book.dao.impl;

import com.yfh.book.dao.BookDAO;
import com.yfh.book.pojo.Book;
import com.yfh.myssm.basedao.BaseDAO;

import java.util.List;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {
    @Override
    public List<Book> getBookList() {
        return super.executeQuery("SELECT * FROM t_book WHERE bookStatus = ?", 1);
    }

    @Override
    public Book getBookById(Integer BookId) {
        return super.load("SELECT * FROM t_book WHERE id = ?", BookId);
    }
}
