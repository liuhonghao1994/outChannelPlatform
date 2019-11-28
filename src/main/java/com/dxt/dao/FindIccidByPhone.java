package com.dxt.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface FindIccidByPhone {
    @Select("select s.icc_id from so.ins_user u,so.res_sim_card_used s\n" +
            "where u.sub_bill_id=s.imsi\n" +
            "and s.manage_status=5\n" +
            "and u.bill_id=#{phone}")
    String getIccid(@Param("phone") String phone);
}
