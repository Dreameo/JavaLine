package com.yfh.ssm.service.impl;

import com.yfh.ssm.mapper.BookMapper;
import com.yfh.ssm.pojo.Books;
import com.yfh.ssm.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

//    public void setBookMapper(BookMapper bookMapper) {
//        this.bookMapper = bookMapper;
//    }

    @Override
    public Books getBookById(Integer bookId) {
        System.out.println("bookservice..getbook by id.....");
        return bookMapper.getBookById(bookId);
//        return null;
    }


}
