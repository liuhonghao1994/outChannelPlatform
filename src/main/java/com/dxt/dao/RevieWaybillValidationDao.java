package com.dxt.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RevieWaybillValidationDao {

    @Select("select count(1) cnt from wx.dxt_audit_cert_user t where t.bill_id =#{bill_id} and t.status in (1,4) and t.is_recovery = 0")
    Integer getRevieWaybillValidation(@Param("bill_id") String bill_id);
}

