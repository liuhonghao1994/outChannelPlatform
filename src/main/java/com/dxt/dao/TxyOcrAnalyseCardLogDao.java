package com.dxt.dao;

import com.dxt.model.TxyOcrAnalyseCardLogBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface TxyOcrAnalyseCardLogDao {

    @Insert("insert into dxt_txy_ocr_analyse_card_log(txy_order_id,responsestr,plat,card_type) " +
            "select #{txyOrderId},#{responseStr},#{plat},#{cardType} from dual a ")
    void insertTxyOcrAnalyseCardLog(TxyOcrAnalyseCardLogBean info);

}
