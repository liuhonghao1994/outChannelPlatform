package com.dxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.dxt.model.BusiCodeBean;

@Repository
public interface BusiCodeBeanDao {

    @Select("SELECT * FROM APP_BUSI_CODE WHERE busi_code=#{busiCode}")
    @Results({
        @Result(property = "busiCode",      column = "busi_code"),
        @Result(property = "beanName",      column = "bean_name"),
        @Result(property = "description",   column = "description"),
        @Result(property = "requireLogin",  column = "requireLogin")
    })
    BusiCodeBean getBusiCodeBeanByBusiCode(String busiCode);
    
    @Select("SELECT * FROM APP_BUSI_CODE")
    @Results({
        @Result(property = "busiCode",      column = "busi_code"),
        @Result(property = "beanName",      column = "bean_name"),
        @Result(property = "description",   column = "description"),
        @Result(property = "requireLogin",  column = "require_Login")
    })
    List<BusiCodeBean> getAllBusiCodeBeans();
    
}
