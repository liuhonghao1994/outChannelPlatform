package com.dxt.dao;



import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface SendMessageMapper {
        @Insert("insert into sms.sms_info_man (id,msisdn,msg,sts,get_date,send_date,msg_id) values(sms.seq_manId.nextval,#{msisdn,jdbcType=VARCHAR},'【迪信通通信】'|| #{msg,jdbcType=VARCHAR},'A',sysdate,sysdate,to_char(sysdate,'yyyymmddhh24miss')||#{msisdn,jdbcType=VARCHAR})")
        void sendMessage(@Param("msisdn")String msisdn,
                     @Param("msg") String msg

        );
}

