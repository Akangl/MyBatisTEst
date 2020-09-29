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