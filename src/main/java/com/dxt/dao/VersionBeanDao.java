package com.dxt.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.dxt.model.AppVersionBean;

@Repository
public interface VersionBeanDao {

    @Select("SELECT * FROM (SELECT * FROM APP_VERSION_HISTORY WHERE type=#{type} " + 
            "ORDER BY VERSION_CODE DESC) WHERE ROWNUM=1 ")
    @Results({
        @Result(property = "id",            column = "id"),
        @Result(property = "versionCode",   column = "version_code"),
        @Result(property = "versionName",   column = "version_name"),
        @Result(property = "updateType",    column = "update_type"),
        @Result(property = "url",           column = "url"),
        @Result(property = "versionDesc",   column = "version_description"),
        @Result(property = "type",          column = "type"),
        @Result(property = "uploadTime",    column = "upload_time"),
        @Result(property = "updateSummary",    column = "update_summary")
    })
    AppVersionBean getLastVersionBeanByType(String type);

    @Select("SELECT * FROM (SELECT * FROM APP_VERSION_HISTORY WHERE type=#{type} and update_type=1 " +
            "ORDER BY VERSION_CODE DESC) WHERE ROWNUM=1 ")
    @Results({
            @Result(property = "id",            column = "id"),
            @Result(property = "versionCode",   column = "version_code"),
            @Result(property = "versionName",   column = "version_name"),
            @Result(property = "updateType",    column = "update_type"),
            @Result(property = "url",           column = "url"),
            @Result(property = "versionDesc",   column = "version_description"),
            @Result(property = "type",          column = "type"),
            @Result(property = "uploadTime",    column = "upload_time"),
            @Result(property = "updateSummary", column = "update_summary")
    })
    AppVersionBean getForceVersionBeanByType(String type);

    @Select("SELECT * FROM APP_VERSION_HISTORY WHERE type=#{type} and version_code=#{versionCode} ")
    @Results({
            @Result(property = "id",            column = "id"),
            @Result(property = "versionCode",   column = "version_code"),
            @Result(property = "versionName",   column = "version_name"),
            @Result(property = "updateType",    column = "update_type"),
            @Result(property = "url",           column = "url"),
            @Result(property = "versionDesc",   column = "version_description"),
            @Result(property = "type",          column = "type"),
            @Result(property = "uploadTime",    column = "upload_time"),
            @Result(property = "updateSummary", column = "update_summary")
    })
    AppVersionBean getDesignatedVersionBeanByType(@Param("type") String type, @Param("versionCode") String versionCode);
    
}
