package com.dxt.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface VerificationIdCardDao {


    @Select({"call appserver.prk_card_manage.prc_check_cust_cert_code(#{in_custcertcode,mode=IN,jdbcType=VARCHAR},#{in_num,mode=IN,jdbcType=VARCHAR},#{vo_return_cod,mode=OUT,jdbcType=VARCHAR}," +
            "#{vo_return_message,mode=OUT,jdbcType=VARCHAR})"})
    @Options(statementType= StatementType.CALLABLE)
    void verificationIdCard(Map<String,String> map);





    @Select({"call wx.prk_sim_active_online.prc_check_cust_cert_code(#{in_custcertcode,mode=IN,jdbcType=VARCHAR},#{in_openid,mode=IN,jdbcType=VARCHAR},#{vo_return_cod,mode=OUT,jdbcType=VARCHAR}," +
            "#{vo_return_message,mode=OUT,jdbcType=VARCHAR})"})
    @Options(statementType= StatementType.CALLABLE)
    void verificationIdCardOld(Map<String,String> map);
}
