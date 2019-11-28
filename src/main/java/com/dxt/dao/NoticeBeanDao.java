package com.dxt.dao;

import com.dxt.model.NoticeBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeBeanDao {

    @Select("SELECT * FROM app_notice WHERE app_type=#{appType} " +
            " and version_code=#{versionCode} and sysdate between effective_date and expire_date ")
    @Results({
            @Result(property = "id",            column = "id"),
            @Result(property = "versionCode",   column = "version_code"),
            @Result(property = "appType",       column = "app_type"),
            @Result(property = "title",         column = "title"),
            @Result(property = "summary",       column = "summary"),
            @Result(property = "url",           column = "url"),
            @Result(property = "remark",        column = "remark")
    })
    List<NoticeBean> getEffectiveNoticeBeanList(@Param("appType") String appType,
                                                @Param("versionCode") String versionCode);


}
