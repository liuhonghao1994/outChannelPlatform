package com.dxt.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface FillChangeCardDao {
        @Select({"call so.proc_user_charge_card(#{VI_BILL,mode=IN,jdbcType=VARCHAR},#{VI_ICCID,mode=IN,jdbcType=VARCHAR}," +
                "#{VO_RETURN_CODE,mode=OUT,jdbcType=VARCHAR},#{VO_RETURN_MESSAGE,mode=OUT,jdbcType=VARCHAR},#{VO_ICCID,mode=OUT,jdbcType=VARCHAR},#{VO_ICCID_OLD,mode=OUT,jdbcType=VARCHAR},#{VO_IMSI_OLD,mode=OUT,jdbcType=VARCHAR})"})
        @Options(statementType= StatementType.CALLABLE)
        void  fillOrChangeCard(Map<String,String> map);


        @Select("select bill_id from so.ins_user where bill_id=#{phone}")
        String numberIsValid(@Param("phone") String phone);
}
