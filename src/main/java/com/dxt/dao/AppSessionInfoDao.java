package com.dxt.dao;

import com.dxt.model.AppSessionInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface AppSessionInfoDao {

    @Select("select * from app_session_info t where t.phone=#{phone,jdbcType=VARCHAR} and (t.effective='1' " +
            " or sysdate < (t.last_login_time + #{effectiveTime, jdbcType=BIGINT}/24/60/60)) ")
    @Results({
            @Result(property = "sessionId", column = "session_id"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "imei", column = "imei"),
            @Result(property = "pushId", column = "push_id"),
            @Result(property = "url", column = "url"),
            @Result(property = "lastLoginTime", column = "last_login_time"),
            @Result(property = "effective", column = "effective"),
            @Result(property = "acctId", column = "acct_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "network", column = "network"),
            @Result(property = "osType", column = "os_type")
    })
    AppSessionInfo getEffectiveAppSessionInfoByPhone(@Param("phone") String phone,
                                                     @Param("effectiveTime") long effectiveTime);

    @Select("select * from app_session_info t where t.session_id=#{sessionId,jdbcType=VARCHAR} and (t.effective='1' " +
            " or sysdate < (t.last_login_time + #{effectiveTime, jdbcType=BIGINT}/24/60/60)) ")
    @Results({
            @Result(property = "sessionId", column = "session_id"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "imei", column = "imei"),
            @Result(property = "pushId", column = "push_id"),
            @Result(property = "url", column = "url"),
            @Result(property = "lastLoginTime", column = "last_login_time"),
            @Result(property = "effective", column = "effective"),
            @Result(property = "acctId", column = "acct_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "network", column = "network"),
            @Result(property = "osType", column = "os_type")
    })
    AppSessionInfo getEffectiveAppSessionInfoBySessionId(@Param("sessionId") String sessionId,
                                                     @Param("effectiveTime") long effectiveTime);


    @Insert("insert into app_session_info (session_id,phone,imei,push_id,acct_id,user_id,network,effective,os_type) values " +
            "(#{sessionId,jdbcType=VARCHAR},#{phone,jdbcType=VARCHAR},#{imei,jdbcType=VARCHAR}," +
            "#{pushId,jdbcType=VARCHAR},#{acctId,jdbcType=VARCHAR},#{userId,jdbcType=VARCHAR}," +
            "#{network,jdbcType=VARCHAR},#{effective,jdbcType=VARCHAR},#{osType,jdbcType=VARCHAR})")
    void insertAppSessionInfo(AppSessionInfo info);

    @Update("update app_session_info t set t.last_login_time=sysdate where t.session_id=#{sessionId}")
    int updateAppSessionInfoLastLoginTime(AppSessionInfo info);

    @Delete("delete app_session_info t where t.session_id=#{sessionId}")
    int deleteAppSessionInfo(String sessionId);

    @Delete("delete app_session_info t where effective='0' and " +
            "sysdate > (t.last_login_time + #{effectiveTime, jdbcType=BIGINT}/24/60/60)")
    int deleteAppSessionInfoBatch(long effectiveTime);


    
}
