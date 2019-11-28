package com.dxt.dao;

import com.dxt.boss.model.OfferInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class CommonDaoProvider {

    public String getSdrCallDetailListByPhoneAndOppNumAndStartAndEndTime(@Param("tablePostfix") String tablePostfix,
        @Param("phone") String phone, @Param("oppNumber") String oppNumber,
        @Param("strStartDate") String strStartDate, @Param("strEndDate") String strEndDate){
        return new SQL(){
            {
                SELECT("*");
                FROM("ud.voice_" + tablePostfix);
                if(null != phone){
                    WHERE("user_number = #{phone}");
                }
                if(null != oppNumber){
                    WHERE("opp_number = #{oppNumber}");
                }
                if(null != strStartDate){
                    WHERE("to_char(start_time, 'yyyymmdd') >= #{strStartDate}");
                }
                if(null != strEndDate){
                    WHERE("to_char(start_time, 'yyyymmdd') <= #{strEndDate}");
                }
            }
        }.toString();
    }


    public String getAppVasOfferList(final Map<String, String> map){
        return new SQL(){
            {
                SELECT("offer_id, offer_name, product_spec, offer_desc, offer_price, " +
                        "offer_type, vas_type, to_char(effective_date,'yyyymmdd') as effective_date, " +
                        "to_char(expire_date,'yyyymmdd') as expire_date, network");
                FROM("app_vas_offer");
                WHERE("effective = '1'");
                if(null != map.get("offerType")){
                    WHERE("offer_type = #{offerType}");
                }
                if(null != map.get("vasType")){
                    if (map.get("vasType").toString().equals(OfferInfo.VASTYPE_All)) {
                        // 全部查询，不做过滤
                    } else if (map.get("vasType").toString().equals(OfferInfo.VASTYPE_EXCLUDE_GPRS)) {
                        // 排除流量包，选择服务类和其他增值类
                        WHERE("vas_type in ('2','3')");
                    } else {
                        WHERE("vas_type = #{vasType}");
                    }
                }
                if(null != map.get("network")){
                    WHERE("network = #{network}");
                }
            }
        }.toString();
    }


}
