package com.itheima.dao;

import com.itheima.entity.Book;

import java.util.List;

public interface IBookDao {
    /**
     * 查询全部图书列表
     */
    List<Book> findAllBooks();
}
