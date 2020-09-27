import com.java.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyApp {

    @Test
    public void selectStudents() throws IOException {
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

    @Test
    public void insertStudnet(Student student) throws IOException{
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
        String sqlId = "com.java.dao.StudentDao.insertStudent";

        //7.执行sql语句,通过sqlId找到语句
        Student student1 = new Student();
        student1.setId(20004);
        student1.setName("周齐");
        student1.setEmail("zhouqi@zq.com");
        student1.setAge(22);
        int nums = sqlSession.insert(sqlId, student1);

        //8.输出结果
        System.out.println("有"+ nums + "行受影响！");

        //9.关闭SqlSession对象
        sqlSession.close();
    }
}
