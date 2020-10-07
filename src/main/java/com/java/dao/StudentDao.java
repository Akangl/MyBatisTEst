package com.java.dao;

import com.java.domain.MyStudent;
import com.java.domain.Student;
import com.java.vo.QueryParam;
import com.java.vo.ViewStudent;
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



    ViewStudent selectStudentReturnViewStudent(@Param("myid")Integer id);

    int countStudent();

    List<Student> selectByAge(@Param("myage")Integer age);

//    定义方法返回Map
    Map<Object, Object> selectMapById(Integer id);

    /*
        使用resultMap定义映射关系
     */
    List<Student> selectAllStudents();

//    列名和属性名不相同,方法一
    List<MyStudent> selectMyStudent();

//    方法二
    List<MyStudent> selectDiffColProperty();


//    模糊查询,第一种:通过Java代码指定like的内容
    List<Student> selectLikeOne(String name);

//    模糊查询,第二种:通过在mapper文件中拼接like的内容
    List<Student> selectLikeTwo(String name);


/*
    动态sql：
        1.<if>
            动态sql使用Java对象作为参数
 */
List<Student> selectStudentIf(Student student);

    List<Student> selectStudentIfTwo(Student student);

    /*
        2.<where>
     */
    List<Student> selectStudentWhere(Student student);

    /*
        3.<foreach>用法一
     */
    List<Student> selectForeachOne(List<Integer> idList);

    /*
        3.<foreach>用法二
     */
    List<Student> selectForeachTwo(List<Student> studentList);

    List<Student> selectForeachTwotwo(List<Student> studentList);

    /*
        Sql代码片段,复用一些语法
     */
    List<Student> selectStudentSqlFragment(List<Student> studentList);

    /*
        使用PageHelper分页数据
     */
    List<Student> selectAll();
}
