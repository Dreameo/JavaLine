package com.yfh.service.impl;

import com.yfh.dao.BookDAO;
import com.yfh.pojo.Book;
import com.yfh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    // 注入DAO
    @Autowired
    private BookDAO bookDAO;

    @Override
    public void addBook(Book book) {
        bookDAO.add(book);
    }

    @Override
    public void updateBook(Book book) {
        bookDAO.updateBook(book);
    }

    @Override
    public void deleteBook(String id) {
        bookDAO.deleteBook(id);
    }

    @Override
    public int findCount() {
        return bookDAO.findCount();
    }

    @Override
    public Book findBookById(String id) {
        return bookDAO.findBookById(id);
    }

    @Override
    public List<Book> findBookList() {
        return bookDAO.findBookList();
    }

    @Override
    public void addBatchBook(List<Object[]> list) {
        bookDAO.addBatchBook(list);
    }
}
