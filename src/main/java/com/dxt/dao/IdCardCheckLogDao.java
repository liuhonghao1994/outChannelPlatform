package com.dxt.dao;

import com.dxt.model.IdCardCheckLogBean;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface IdCardCheckLogDao {
    @Insert("insert into app_idcard_check_log(name,id_card,result_id,result_code,result_msg,plat) " +
            "values(#{name},#{idCard},#{resultRequestId},#{resultCode},#{resultMsg},#{plat})")
    void insertIdCardCheckLog(IdCardCheckLogBean idCardCheckLogBean);
}
