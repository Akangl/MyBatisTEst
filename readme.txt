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
                使用的是Statement对象执行sql,效率比PrepareStatement低。

                $:可以替换表名或者列名;不推荐使用
                区别:
                    1.#使用?在sql语句中做站位的,使用PreparedStatement执行sql,效率高
                    2.#能避免sql注入,更安全
                    3.$不使用占位符,是字符串的连接方式,使用Statement对象执行sql,效率低
                    4.$由sql注入的风险,缺乏安全性
                    5.$可以替换表名或者列名