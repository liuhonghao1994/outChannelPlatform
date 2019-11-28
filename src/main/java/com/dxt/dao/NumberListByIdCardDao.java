package com.dxt.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NumberListByIdCardDao {


    @Select("select t.bill_id from wx.audit_cust_user_views t where t.Cust_Cert_Code =#{card}")
    List<String> getNumberListByIdCard(@Param("card") String card);
}
