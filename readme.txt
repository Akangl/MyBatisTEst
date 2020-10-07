第一章：
    第一个入门的mybatis例子
    实现步骤:
        1．新建的student表
        2．加入maven的mybatis坐标,mysql驱动的坐标
        3．创建实体类，student--保存表中的一行数据的
        4．创建持久层的dao接口，定义操作数据库的方法
        5．创建一个mybatis使用的配置文件
            叫做sql映射文件:写sql语句的。一般一个表一个sql映射文件。
            这个文件是xml文件。
                在接口所在的目录下；
                与接口名字应该保存相同。
        6．创建mybatis的主配置文件:
            一个项目就一个主配置文件。
            主配置文件提供了数据库的连接信息和sql映射文件的位置信息
        7.创建使用mybatis类，
            通过mybatis访问数据库


第二章：
    1.主要类的介绍
        1)Resources:
            mybatis中的一个类,负责读取主配置文件
            InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        2)SqlSessionFactoryBuilder:
            //创建SqlSessionFactory对象
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        3)SqlSessionFactory:
            重量级对象,创建这个对象耗时比较长,使用资源比较多;在整个项目中有一个就满足整个项目。
          SqlSessionFactory接口:
                接口实现类:DefaultSqlSessionFactory
          SqlSessionFactory作用:
                获取SqlSession对象
                SqlSession sqlSession = sqlSessionFactory.openSession();
          openSession()方法:
                openSession():获取是非自动提交事务的获取SqlSession对象
                 openSession(boolean):
                    true:获取自动提交事务的的SqlSession;
                    false:获取非自动提交事务的的SqlSession。

        4)SqlSession:
            SqlSession接口:定义了操作数据的方法
                例如:
                    selectOne(),selectlist(),insert(),update(),delete(),commit(),rollback()
            SqlSession实现类:
                DefaultSqlSession

            使用要求:
                SqlSession对象不是线程安全,需要在方法内部使用,在执行sql语句之前,使用openSession()获取sqlSqlSession对象,
                在执行完sql语句后,需要关闭它,执行SqlSession.close(),这样能保证它的使用是线程安全。
                14开始


第三章:
    1.动态代理:使用SqlSession.getMapper(dao接口.class)获取这个dao接口对象;
    2.传入参数:从Java代码中把数据传入到mapper文件的sql语句中
        1):parameterType:
            写在mapper文件中的一个属性。
            表示dao接口中的方法的参数的数据类型。
        例如:StudentDao接口
            public Student selectStudentById(Integet id)

        2)一个简单数据类型
            简单类型:mybatis把Java的基本数据类型和String类型叫做简单类型
            在mapper文件获取简单类型的一个参数的值,使用 #{任意字符}
            接口:
                public Student selectStudentById(Integet id)
            mapper:
                select * from student where id=#{id}
        3).多个参数
           (1).@Param命名参数：
                接口public List<Student> selectMuliParam(@Param("myname") String name,@Param("myage") Integer age)
                @Param("参数名") String name
                mapper文件:
                    <select>
                        select * from student where name =#{myname} or age =#{myage}
                    </select>
           (2).使用java对象
                使用Java对象的属性值,作为参数实际值
                语法:#{属性名}
           (3).按位置
           语法:#{arg位置},从0开始
                mybatis 3.4版本之前：第一个位置是#{0},第二个是#{1}......
                mybatis 3.4版本之后：第一个位置是#{arg0},第二个是#{arg1}......
                接口:
                    List<Student> selectByNameAndAge(String name,int age)
                mapper文件:
                    <select>
                        select * from student where name=#{arg0} or age=#{arg1}
                    </select>
           (4).使用Map
                使用Map向mapper文件一次传入多个参数,Map集合使用String的key,Object类型的值存储参数。
                语法:#{key}
                例如:
                    Map<String,Object> data = new HashMap<String,Object>();
                    data.put("myname","何伸);
                    data.put("myage",20)
                接口方法:
                    List<Student> selectMulitMap(Map<String,Object> map);
           (5).#和$
                select * from student where id=#{id}
                    #的结果：select * from student where id=?
                select * from student where id=${id}
                    $的结果：select * from student where id=20003
                String sql = "select * from student where id=" + "1001";(字符串连接)
                使用的是Statement对象执行sql,效率比PreparedStatement低。

        $:可以替换表名或者列名;不推荐使用
        区别:
            1.#使用?在sql语句中做站位的,使用PreparedStatement执行sql,效率高
            2.#能避免sql注入,更安全
            3.$不使用占位符,是字符串的连接方式,使用Statement对象执行sql,效率低
            4.$由sql注入的风险,缺乏安全性
            5.$可以替换表名或者列名

    3.mybatis的输出结果
        mybatis执行了sql语句,得到Java对象

        1).resultType:指sql语句执行完毕后,数据转换为的Java对象,Java类型是任意的。
           resultType结果类型它的值类型:
                1.类型的全限定名称;
                2.类型的别名(java.lang.Integer == int)
        处理方式:
            1.mybatis执行sql语句,然后mybatis调用类的无参构造函数,创建对象。
            2.mybatis把ResultSet指定列值赋给同名的属性。

            <select id="selectStudentById"  resultType="com.java.domain.Student">
                select * from student where id=#{id}
            </select>

            对等的JDBC:
                ResultSet resultSet = executeQuery("select * from student");
                while(resultSet.next()){
                    Student student = new Student();
                    student.setId(resultSet.getInt("id"));
                    student.setName(resultSet.getString("name"));
                }

        2)定义自定义类型的别名:
            1).在mybatis主配置文件中定义,使用<typeAlias>定义别名
            2).在resultType中使用自定义别名

        3).resultMap:结果映射
          指定列名和Java对象属性的对应关系
            1).自定义列值赋值给某个属性
            2).当你的列名和属性名不一样时,一定要使用resultMap


第四章:
    动态sql:sql的内容是变化的,可以根据条件获取到不同的sql语句
            主要是where部分发生变化

    动态sql的实现:使用的是mybatis提供的标签,<if>,<where>,<foreach>
        1).<if>:是判断条件的
            语法:
                <if test = "判断Java对象的属性值">
                    部分sql语句
                </if>

        2).<where>:包含多个<if>的，当多个<if>有一个成立,<where>会自动增加一个where关键字,会去除<if>多余的and,or等

        3).<foreach>:循环Java中的数组,list集合的。主要用在SQL的in语句中。
            学生的id 20001,20002,20003的三个学生
            select * from student id in (20001,20002,20003);

            public List<Student> selectFor(List<Integer> idList);

            List<Integer> list = new ArrayList<>();
            list.add(20001);
            list.add(20002);
            list.add(20003);

            studentDao.selectFor(list);

            语法:<foreach collection="" item="" open="" close="" separator=""> </foreach>
                collection:表示接口中的方法参数的类型,如果是数组使用array,如果是集合使用list;
                item:自定义的,表示数组和集合成员的变量;
                open:循环开始时的字符;
                close:循环结束时的字符;
                separator:集合成员之间的分隔符。

        4).代码片段:
            <sql>标签用于定义SQL片段,以便其他SQL标签重复使用;
            其他标签使用该SQL片段,需使用<include>子标签;
            该<sql>标签可以定义SQL语句中的任何部分,所以<include>子标签可以放在动态SQL的任何位置;

            接口方法：
               List<Student> selectStudentSqlFragment(List<Student> studentList);

            mapper文件:
                <sql id = "studentSql">//id:片段的自定义名称
                    重复使用的代码(select * from student)
                </sql>

                <select id = "" resultType = "">
                    //引用sql片段
                    <include refid="studentSql">
                    ......
                </select>

第五章:
    1.数据库的属性配置文件:
        把数据库连接信息放到一个单独的文件中。和Mybatis主配置文件分开。
        目的是便于修改，保存，处理多个数据库的信息

        1)在resources目录中定义一个属性配置文件,名字为(xxx.properties),例如：jdbc.properties
        在属性配置文件中,定义数据,格式是   key = value
        key:一般使用多级目录
        例如:
            jdbc.driver=com.mysql.cj.jdbc.Driver
            jdbc.url=jdbc:mysql://......
            jdbc.user=root
            jdbc.passwd=******

        2).在mybatis的主配置文件,使用<properties>指定文件的位置
            在需要使用值的地方，${key}

    2.mapper文件,使用package指定路径:
        使用包名
            name:xml文件(mapper文件)所在的包名,这个包中所有的xml文件一次都能加载给mybatis
            使用package的要求:
                1).mapper文件名称需要和接口名称一样,区分大小写的一样
                2).mapper文件需要和dao接口需要在同一目录
        <mappers>
            <package name="com.java.dao"/>
        </mappers>

第六章:PageHelper
