package com.dxt.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoSynchronizationDao {


    @Select("select user_id from so.ins_user  where bill_id = #{phone}")
    String getUUid(@Param("phone") String phone);



    @Select("select count(1) cnt from wx.dxt_audit_cert_user t where t.user_id =#{user_id} and t.status<>2")
    Integer getPhotos(@Param("user_id") String user_id);
}
