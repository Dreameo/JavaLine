package com.yfh.ssm;

import com.yfh.ssm.mapper.BookMapper;
import com.yfh.ssm.pojo.Books;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class BooksTest {

    @Test
    public void getBookById() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");

        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        BookMapper mapper = sqlSession.getMapper(BookMapper.class);

        Books book = mapper.getBookById(1);

        System.out.println(book);

    }
}
