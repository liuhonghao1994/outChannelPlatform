package com.dxt.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface WriteResultofReceiptCardDao {


    @Select({"call prk_card_manage.prc_update_card_status (#{PHONE,mode=IN,jdbcType=VARCHAR},#{ICCID,mode=IN,jdbcType=VARCHAR},#{IMSI,mode=IN,jdbcType=VARCHAR}," +
        "#{VO_RETURN_CODE,mode=OUT,jdbcType=VARCHAR},#{VO_RETURN_MESSAGE,mode=OUT,jdbcType=VARCHAR})"})
    @Options(statementType= StatementType.CALLABLE)
    void getWriteResultInfo(Map<String,String> map);
}
