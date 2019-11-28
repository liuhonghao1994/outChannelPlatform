package com.dxt.dao;

import com.dxt.boss.model.PhoneModle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FindAllPhoneNumberOfUserDao {

    @Select("select t.* from wx.audit_cust_user_views t where t.Cust_Cert_Code = #{cust_cert_code}")
    List<PhoneModle> getAllPhoneNumberOfUser(@Param("cust_cert_code") String cust_cert_code);
}
