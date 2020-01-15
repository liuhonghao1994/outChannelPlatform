package com.dxt.dao;

import com.dxt.boss.model.UnuseBeanOfQueryPhone;
import com.dxt.boss.model.UseBeanOfQueryPhone;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryPhoneBySimDao {

        @Select("SELECT s.icc_id,decode(s.manage_status ,2,'可售',8,'预留',3,'预占','作废')   status\n" +
                "FROM so.res_sim_card_origin s   \n" +
                "WHERE substr(S.ICC_ID, 1, 19) = SUBSTR(#{iccid_sn}, 1, 19)\n" +
                "or S.ICC_ID =#{iccid_sn}\n" +
                "or  S.SN like SUBSTR(#{iccid_sn}, 1, 18) || '%'")
        UnuseBeanOfQueryPhone getUnUseChartOfQueryPhone(@Param("iccid_sn") String iccid_sn);



        @Select("SELECT u.bill_id,s.icc_id,decode(u.state ,1,'在用',4,'预开户',5,'已销户',6,'已销户','作废') status\n" +
                "FROM so.res_sim_card_used s  ,so.ins_user  u\n" +
                "WHERE s.imsi= u.sub_bill_id(+)\n" +
                "and( S.ICC_ID like SUBSTR(#{iccid_sn}, 1, 19) || '%'\n" +
                "or S.ICC_ID =#{iccid_sn}\n" +
                "or S.SN like  SUBSTR(#{iccid_sn}, 1, 18) || '%')")
        UseBeanOfQueryPhone getUseChartOfQueryPhone(@Param("iccid_sn") String iccid_sn);

}
