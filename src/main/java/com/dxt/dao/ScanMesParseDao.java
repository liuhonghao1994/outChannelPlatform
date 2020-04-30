package com.dxt.dao;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;


import java.util.Map;

@Repository
public interface ScanMesParseDao {
    @Insert("insert into scan_from_mes_parse values(#{iccid,jdbcType=VARCHAR},#{imsi,jdbcType=VARCHAR},#{pin1,jdbcType=VARCHAR},#{puk1,jdbcType=VARCHAR},#{pin2,jdbcType=VARCHAR},#{puk2,jdbcType=VARCHAR},#{adm,jdbcType=VARCHAR},#{uimid,jdbcType=VARCHAR},#{imsi_g,jdbcType=VARCHAR},#{smsp,jdbcType=VARCHAR},#{imsi_lte,jdbcType=VARCHAR},sysdate,#{file_name,jdbcType=VARCHAR})")
    void insetMesInfoIntoParseMes(Map map);
}
