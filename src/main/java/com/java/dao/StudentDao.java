package com.java.dao;

import com.java.domain.Student;

import java.util.List;

public interface StudentDao {
    //查询Student所有表的数据
    public List<Student> selectStudent();

    //插入数据方法
    //参数:student    表示要插入的数据库的数据
    //返回值:int       表示执行insert操作后的影响数据库的行数
    public int insertStudent(Student student);
}
