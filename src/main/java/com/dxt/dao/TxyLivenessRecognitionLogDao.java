package com.dxt.dao;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TxyLivenessRecognitionLogDao {

    @Insert("insert into dxt_txy_Live_recognition_log(txy_order_id,resCode,resMsg,plat,responseStr) " +
            "select #{txyOrderId},#{resCode},#{resMsg},#{plat},#{responseStr} from dual a ")
    void insertTxyLivenessRecognitionLog(Map map);

}