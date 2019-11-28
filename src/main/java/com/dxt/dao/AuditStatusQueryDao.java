package com.dxt.dao;

import com.dxt.model.AuditStatusBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditStatusQueryDao {



    @Select("select c.phone_number bill_id,a.audit_date,a.status status_code,decode(a.status,1,'待审核',2,'审核不通过',3,'审核通过','','未审核') status,\n" +
            "a.audit_msg from wx.dxt_audit_cert_user a,so.ins_user b,appserver.user_mes_from_uploadphoto c\n" +
            " where c.userid = a.user_id(+) and a.user_id = b.user_id(+)\n" +
            "and c.status = 0\n"+
            "and c.phone_number =#{phone}")
    AuditStatusBean  getAuditMes(@Param("phone") String phone);
}
