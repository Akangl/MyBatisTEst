package com.java;

import com.java.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyApp {
    public static void main(String[] args) throws IOException {
        //访问MyBatis读取Student数据
        //1.定义mybatis主配置文件的名称,从类路径的根开始(target/classes)
        String config = "mybatis.xml";

        //2.读取这个config表示的文件
        InputStream inputStream = Resources.getResourceAsStream(config);

        //3.创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        //4.创建SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

        //【重要】5.获取SqlSession对象,从SqlSessionFactory中获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //【重要】6.指定要执行的sql语句的标识。sql映射文件中的namesapce + "." + 标签id的值
        String sqlId = "com.java.dao.StudentDao" + "." + "selectStudent";

        //7.执行sql语句,通过sqlId找到语句
        List<Student> studentList = sqlSession.selectList(sqlId);

        //8.输出结果
        studentList.forEach(stu -> System.out.println(stu));

        //9.关闭SqlSession对象
        sqlSession.close();

    }
}
