package com.dxt.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeepOfUserMes {


        //向表中插入一条数据
    @Insert("insert into user_mes_from_uploadphoto values(#{userid,jdbcType=VARCHAR},#{phone_number,jdbcType=VARCHAR},#{card_type,jdbcType=VARCHAR},#{card_number,jdbcType=VARCHAR},#{user_name,jdbcType=VARCHAR},#{card_address,jdbcType=VARCHAR},#{take_effect_time,jdbcType=VARCHAR},#{failure_time,jdbcType=VARCHAR},#{insert_time,jdbcType=VARCHAR},#{status,jdbcType=INTEGER},#{is_needpas,jdbcType=INTEGER})")
    void keepUserMes(@Param("userid") String userid,
                     @Param("phone_number") String phone_number,
                     @Param("card_type") String card_type,
                     @Param("card_number") String card_number,
                     @Param("user_name") String user_name,
                     @Param("card_address") String card_address,
                     @Param("take_effect_time") String take_effect_time,
                     @Param("failure_time") String failure_time,
                     @Param("insert_time") String insert_time,
                     @Param("status") int status,
                     @Param("is_needpas") int is_needpas);

    @Select("select userid from user_mes_from_uploadphoto")
    List<String> getUseridFromUserMes();

    @Update("update user_mes_from_uploadphoto t set t.status=1  where t.userid=#{userid,jdbcType=VARCHAR}")
    void updateStatus(@Param("userid") String userid);
}
