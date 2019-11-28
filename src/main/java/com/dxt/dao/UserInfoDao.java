package com.dxt.dao;

import com.dxt.boss.model.UserInfoModle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao {
        @Select("select u.bill_id,decode(u.state,1,'正常',4,'预开户',5,'营业销户',6,'账务销户','不存在') state,\n" +
                "decode(o.os_status,\n" +
                "'0000000000000000000000000000000000000000000000000000000000000000','正常',\n" +
                "'0010000000000000000000000000000000000000000000000000000000000000','停机保号',\n" +
                "'0000001000000000000000000000000000000000000000000000000000000000','营业停', \n" +
                "'0000000000000010000000000000000000000000000000000000000000000000','呼出限制',\n" +
                "'0000000000000001000000000000000000000000000000000000000000000000','帐务停',  \n" +
                "'0000000000000000000000000100000000000000000000000000000000000000','管理停',\n" +
                "'0010000000000000000000000100000000000000000000000000000000000000','管理停+停机保号',\n" +
                "'0000001000000000000000000100000000000000000000000000000000000000','管理停+营业停',\n" +
                "'0000000000000010000000000100000000000000000000000000000000000000','管理停+呼出限制',\n" +
                "'0000000000000001000000000100000000000000000000000000000000000000','管理停+帐务停',\n" +
                "'0010000000000001000000000100000000000000000000000000000000000000','管理停+帐务停+停机保号',\n" +
                "'0000001000000001000000000100000000000000000000000000000000000000','管理停+帐务停+营业停',\n" +
                "'0010000000000001000000000000000000000000000000000000000000000000','帐务停+停机保号',\n" +
                "'0000001000000001000000000000000000000000000000000000000000000000','帐务停+营业停',\n" +
                "'0000001000000010000000000000000000000000000000000000000000000000','呼出限制+营业停' )  os_status,\n" +
                "offer.offer_id,offer.order_name,c.cust_name,\n" +
                "c.cust_cert_type,c.cust_cert_code,c.cust_cert_address,u.create_date,u.effective_date,u.user_id,u.expire_date\n" +
                "from so.ins_user u,so.ins_offer offer,so.ins_user_os_state o,party.cm_indiv_customer  c\n" +
                "where u.user_id=o.user_id\n" +
                "and u.cust_id=c.cust_id\n" +
                "and u.bill_id=#{phone}\n" +
                "and offer.expire_date>sysdate\n" +
                "and offer.user_id=u.user_id\n" +
                "and offer.offer_type='OFFER_PLAN_GSM'")
        UserInfoModle getUserInfoMes(@Param("phone") String phone);
}
