package com.java.dao;

import com.java.domain.Student;

import java.util.List;

public interface StudentDao {
    //查询Student所有表的数据
    public List<Student> selectStudent();
}
