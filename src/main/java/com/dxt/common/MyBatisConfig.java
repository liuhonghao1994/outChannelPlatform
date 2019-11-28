package com.dxt.common;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * springboot集成mybatis的基本入口
 * 1）创建数据源(如果采用的是默认的tomcat-jdbc数据源，则不需要)
 * 2）创建SqlSessionFactory
 * 3）配置事务管理器，除非需要使用事务，否则不用配置
 */
@Configuration
@MapperScan(basePackages="com.dxt.dao",sqlSessionFactoryRef = "sessionFactory")
public class MyBatisConfig {

    @Autowired
    private Environment env;

    /**
     * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
     */
    @Bean(name="myDefaultDataSource")
    public DataSource myDefaultDataSource() {
        //使用DruidDataSource数据源
        DruidDataSource dataSources = new DruidDataSource();
        //获取驱动
        dataSources.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        //获取访问路径
        dataSources.setUrl(env.getProperty("spring.datasource.url"));
        //获取用户名
        dataSources.setUsername(env.getProperty("spring.datasource.username"));
        //获取用户密码
        dataSources.setPassword(env.getProperty("spring.datasource.password"));
        return dataSources;
    }

    @Bean(name="myCdrDataSource")
    public DataSource myCdrDataSource() {
        //使用DruidDataSource数据源
        DruidDataSource dataSources = new DruidDataSource();
        //获取驱动
        dataSources.setDriverClassName(env.getProperty("spring.datasource-cdr.driver-class-name"));
        //获取访问路径
        dataSources.setUrl(env.getProperty("spring.datasource-cdr.url"));
        //获取用户名
        dataSources.setUsername(env.getProperty("spring.datasource-cdr.username"));
        //获取用户密码
        dataSources.setPassword(env.getProperty("spring.datasource-cdr.password"));
        return dataSources;
    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("myDefaultDataSource") DataSource myDefaultDataSource,
                                        @Qualifier("myCdrDataSource") DataSource myCdrDataSource) {
        ConcurrentHashMap<Object, Object> targetDataSources = new ConcurrentHashMap<>();
        targetDataSources.put(MyDatabaseSource.defualtDB, myDefaultDataSource);
        targetDataSources.put(MyDatabaseSource.cdrDB, myCdrDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dataSource.setTargetDataSources(targetDataSources);
        // 默认的datasource设置为myTestDbDataSource
        dataSource.setDefaultTargetDataSource(myDefaultDataSource);
        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean(name="sessionFactory")
    public SqlSessionFactory sessionFactory(@Qualifier("myDefaultDataSource") DataSource myDefaultDataSource,
                                            @Qualifier("myCdrDataSource") DataSource myCdrDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(this.dataSource(myDefaultDataSource, myCdrDataSource));
        // 下边仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        // 本项目全部使用注解，所以进行注释
//        sessionFactoryBean.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));// 指定基包
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().
//                getResources(env.getProperty("mybatis.mapperLocations")));
        return sessionFactoryBean.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager (DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
