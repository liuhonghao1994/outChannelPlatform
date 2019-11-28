package com.dxt.dao;

import com.dxt.model.UploadInfoBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadInfoBeanDao {

    @Insert("insert into app_upload_infos " +
            "(version_code,app_type,info_type,content,remark) values " +
            "(#{versionCode,jdbcType=BIGINT},#{appType,jdbcType=VARCHAR},#{infoType,jdbcType=VARCHAR}," +
            "#{content,jdbcType=VARCHAR},#{remark,jdbcType=VARCHAR})")
    void insert(UploadInfoBean info);

}
