package com.dxt.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CardNumberMatchingDao {




    @Select({"call so.proc_num_iccid_rel_check(#{VI_BILL,mode=IN,jdbcType=VARCHAR},#{VI_ICCID,mode=IN,jdbcType=VARCHAR},"+
            "#{VO_RETURN_CODE,mode=OUT,jdbcType=VARCHAR},#{VO_ICCID,mode=OUT,jdbcType=VARCHAR},#{VO_RETURN_MESSAGE,mode=OUT,jdbcType=VARCHAR})"})
    @Options(statementType= StatementType.CALLABLE)
    void cardMatch(Map<String,String> map);

}
