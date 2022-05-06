package com.yfh.ssm.mapper;

import com.yfh.ssm.pojo.Books;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMapper {

    /**
     * 根据id查找书本信息
     */
    @Select("SELECT * FROM t_book WHERE bookID = #{bookId}")
    Books getBookById(@Param("bookId") Integer bookId);
}
