package com.java.dao;

import com.java.domain.Student;
import com.java.vo.QueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentDao {
    //查询Student所有表的数据
    public List<Student> selectStudent();

    //插入数据方法
    //参数:student    表示要插入的数据库的数据
    //返回值:int       表示执行insert操作后的影响数据库的行数
    public int insertStudent(Student student);


    /*
        一个简单数据类型
            简单类型:mybatis把Java的基本数据类型和String类型叫做简单类型
            在mapper文件获取简单类型的一个参数的值,使用 #{任意字符}
     */
    public Student selectStudentById(@Param("id") Integer id);

    public Student selectStudentByName(String name);

    public List<Student> selectStudentByAge(Integer age);

    /*
        多个参数:
            1.命名参数,在形参定义前面加入@Param("自定义参数名")
     */
    List<Student> selectMulitParam(@Param("myname") String name, @Param("myage") Integer age);

    List<Student> selectXhnlParam(@Param("myid")Integer id,@Param("myage") Integer age);

    /*
            2.多个参数,使用Java对象作为接口中方法的参数
     */
    List<Student> selectMulitObject(QueryParam queryParam);

    List<Student> selectMulitStudent(Student student);

    /*
        3.按位置
     */
    List<Student> selectByNameAndAge(String name,int age);

    /*
        4.使用Map集合
     */
    List<Student> selectMulitMap(Map<String,Object> map);

    List<Student> selectUseOredr(@Param("colName")String colName);
}
