package com.dxt.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ObtainUsageInfoDao {


    @Select({"call prk_card_manage.prc_get_card_source(#{PHONE,mode=IN,jdbcType=VARCHAR},#{ICCID,mode=IN,jdbcType=VARCHAR}," +
            "#{ICCID,mode=OUT,jdbcType=VARCHAR},#{IMSI,mode=OUT,jdbcType=VARCHAR},#{PIN1,mode=OUT,jdbcType=VARCHAR}," +
            "#{PIN2,mode=OUT,jdbcType=VARCHAR},#{PUK1,mode=OUT,jdbcType=VARCHAR},#{PUK2,mode=OUT,jdbcType=VARCHAR}," +
            "#{SMS_CENTER,mode=OUT,jdbcType=VARCHAR},#{RETURN_CODE,mode=OUT,jdbcType=VARCHAR},#{RETURN_MESSAGE,mode=OUT,jdbcType=VARCHAR})"})
    @Options(statementType= StatementType.CALLABLE)
    void getUsageInfoMessages(Map<String,String> params);
}
