import com.java.dao.StudentDao;
import com.java.domain.Student;
import com.java.impl.StudentDaoImpl;
import com.java.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestMyBatis {
/*
    没有使用接口
 */
    @Test
    public void selectStudents() throws IOException {
//        //访问MyBatis读取Student数据
//        //1.定义mybatis主配置文件的名称,从类路径的根开始(target/classes)
//        String config = "mybatis.xml";
//
//        //2.读取这个config表示的文件
//        InputStream inputStream = Resources.getResourceAsStream(config);
//
//        //3.创建SqlSessionFactoryBuilder对象
//        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//
//        //4.创建SqlSessionFactory对象
//        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

        //【重要】5.获取SqlSession对象,从SqlSessionFactory中获取SqlSession
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        //【重要】6.指定要执行的sql语句的标识。sql映射文件中的namesapce + "." + 标签id的值
        String sqlId = "com.java.dao.StudentDao" + "." + "selectStudent";

        //7.执行sql语句,通过sqlId找到语句
        List<Student> studentList = sqlSession.selectList(sqlId);

        //8.输出结果
        studentList.forEach(stu -> System.out.println(stu));

        //9.关闭SqlSession对象
        sqlSession.close();
    }

    @Test
    public void testInsert() throws IOException{
//        //访问MyBatis读取Student数据
//        //1.定义mybatis主配置文件的名称,从类路径的根开始(target/classes)
//        String config = "mybatis.xml";
//
//        //2.读取这个config表示的文件
//        InputStream inputStream = Resources.getResourceAsStream(config);
//
//        //3.创建SqlSessionFactoryBuilder对象
//        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//
//        //4.创建SqlSessionFactory对象
//        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

        //【重要】5.获取SqlSession对象,从SqlSessionFactory中获取SqlSession
        SqlSession sqlSession = MyBatisUtils.getSqlSession(true);

        //【重要】6.指定要执行的sql语句的标识。sql映射文件中的namesapce + "." + 标签id的值
        String sqlId = "com.java.dao.StudentDao.insertStudent";

        //7.执行sql语句,通过sqlId找到语句
        Student student1 = new Student();
        student1.setId(20006);
        student1.setName("张虎");
        student1.setEmail("zhanghu@zh.com");
        student1.setAge(23);
        int nums = sqlSession.insert(sqlId, student1);
        //mybatis默认是不自动提交事务的,所以在insert,update,delete后面要手工提交事务
//        sqlSession.commit();

        //8.输出结果
        System.out.println("有"+ nums + "行受影响！");

        //9.关闭SqlSession对象
        sqlSession.close();
    }

/*
    使用接口
 */
    @Test
    public void testSelectStudents(){
        //com.java.dao.StudentDao
        StudentDao studentDao = new StudentDaoImpl();
        /*
            List<Student> studentList = studentDao.selectStudent();调用
            1.dao对象,类型是StudentDao,全限定名称是:com.java.dao.StudentDao
            全限定名称和namespace是一样的。

            2.方法名称,selectStudent,这个方法就是mapper文件中的id的值selectStudent

            3.通过Dao方法的返回值也可以确定MyBatis要调用的SqlSession的方法
                如果返回值是List,调用的是SqlSession.selectList()方法;
                覆盖返回值是int,或是非List的,看mapper文件中的标签<insert>,<update>就会调用SqlSession的insert,update方法

            MyBatis的动态代理：
                MyBatis根据dao的方法的调用,获取执行sql语句的信息；
                MyBatis根据你的dao接口,创建出一个dao接口的实现类,并创建这个类的对象；
                完成SqlSession,访问数据库。

                
         */
        List<Student> studentList = studentDao.selectStudent();
        for (Student student : studentList){
            System.out.println(student);
        }
    }
    @Test
    public void testInsertStudent(){
        StudentDao studentDao = new StudentDaoImpl();
        Student student = new Student();
        student.setId(20007);
        student.setName("周商");
        student.setEmail("zhoushang@zs.com");
        student.setAge(21);
        int nums = studentDao.insertStudent(student);
        System.out.println("受影响的行数" + nums);
    }

}
