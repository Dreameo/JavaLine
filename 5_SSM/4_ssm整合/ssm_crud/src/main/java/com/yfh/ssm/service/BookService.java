package com.yfh.ssm.service;

import com.yfh.ssm.pojo.Books;

public interface BookService {

    /**
     * service中根据id查询书本信息
     */
    Books getBookById(Integer bookId);
}
