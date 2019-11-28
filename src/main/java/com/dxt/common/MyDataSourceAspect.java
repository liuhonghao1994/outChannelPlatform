package com.dxt.common;

import com.dxt.dao.PhoneCDRDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class MyDataSourceAspect {

    /**
     * 使用空方法定义切点表达式
     */
    @Pointcut("execution(* com.dxt.dao.*.*(..))")
    public void myDataSourceJoinPoint() {
    }

    @Before("myDataSourceJoinPoint()")
    public void setDataSourceKey(JoinPoint point){
        //连接点所属的类实例是PhoneCDRDao
        if(point.getTarget() instanceof PhoneCDRDao){
            DatabaseContextHolder.setDatabaseSource(MyDatabaseSource.cdrDB);
        } else {
            // 此处可以省略，因为defaultTargertDataSource就是使用defualtDB
            DatabaseContextHolder.setDatabaseSource(MyDatabaseSource.defualtDB);
        }
    }

    @After("myDataSourceJoinPoint()")
    public void removeDataSourceObject(JoinPoint point){
        DatabaseContextHolder.removeDatabaseSource();
    }

}
