package com.dxt.dao;

import com.dxt.model.CustCertInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CustCertInfoDao {

    @Select("select t.id,t.name,t.idcode,t.birthday,t.gender from dxtcharge.oip_idinfo t" +
            " where  t.idcode = #{idCode,jdbcType=VARCHAR} and t.name = #{name,jdbcType=VARCHAR}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "idCode", column = "idCode"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "gender", column = "gender")
    })
    CustCertInfo getCustInfoByIdCodeAndName(@Param("idCode") String idCode,
                                            @Param("name") String name);

    @Select("select t.id,t.name,t.idcode,t.birthday,t.gender from dxtcharge.oip_idinfo t" +
            " where  t.idcode = #{idCode,jdbcType=VARCHAR}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "idCode", column = "idCode"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "gender", column = "gender")
    })
    CustCertInfo getCustInfoByIdCode(@Param("idCode") String idCode);

    @Insert("insert into dxtcharge.oip_idinfo(id,name,idcode,birthday,gender) " +
            "select #{id}," +
            "#{name},#{idCode},substr(#{idCode},7,8)," +
            "decode(mod(substr(#{idCode},17,1),2),0,'女',1,'男'," +
            "mod(substr(#{idCode},17,1),2)) from dual a where  not exists " +
            "(select 1 from dxtcharge.oip_idinfo a where a.idcode = #{idCode} and a.name = #{name})")
    void insertCustCertInfo(CustCertInfo info);

}
