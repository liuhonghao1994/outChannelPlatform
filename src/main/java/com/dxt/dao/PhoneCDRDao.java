package com.dxt.dao;

import com.dxt.boss.model.SdrCallDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneCDRDao {

    @SelectProvider(type=CommonDaoProvider.class,method="getSdrCallDetailListByPhoneAndOppNumAndStartAndEndTime")
    @Results({
            @Result(property = "offerId",            column = "product_item_id"),
            @Result(property = "offerName",          column = "name"),
            @Result(property = "offerDesc",          column = "description"),
            @Result(property = "effectiveDate",      column = "eff_date"),
            @Result(property = "expireDate",         column = "exp_date")
    })
    List<SdrCallDetail> getSdrCallDetailListByPhoneAndOppNumAndStartAndEndTime(@Param("tablePostfix") String tablePostfix,
            @Param("phone") String phone, @Param("oppNumber") String oppNumber,
            @Param("strStartDate") String strStartDate, @Param("strEndDate") String strEndDate);


}
