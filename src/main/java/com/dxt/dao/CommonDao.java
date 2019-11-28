package com.dxt.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dxt.boss.model.MainOfferInfo;
import com.dxt.boss.model.OfferInfo;
import com.dxt.model.AppHomeInfoBean;
import com.dxt.model.AppMenuBean;
import com.dxt.model.AppServerMenu;
import com.dxt.model.CommonKeyValueBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonDao {

    @Select("SELECT district_name FROM base.bs_district t where t.region_id=#{regionId}")
    String findRegionNameByRegionId(@Param("regionId") String regionId);

    @Select("SELECT region_id as code, district_name as value " +
            "FROM base.bs_district t where t.district_type_id=2 ")
    @Results({
            @Result(property = "code", column = "code"),
            @Result(property = "value", column = "value")
    })
    List<CommonKeyValueBean> getRegionInfoList();

    @Select("SELECT avatar_url FROM app_user_avatar t where t.phone=#{phone} ")
    String getAvatarUrlByPhone(@Param("phone") String phone);

    @Insert("insert into app_user_avatar (phone, avatar_url) values " +
            " (#{phone, jdbcType=VARCHAR}, #{url, jdbcType=VARCHAR}) ")
    void insertAvatarUrl(@Param("phone") String phone, @Param("url") String url);

    @Update("update app_user_avatar t set t.avatar_url=#{url, jdbcType=VARCHAR} " +
            " where t.phone=#{phone} ")
    void updateAvatarUrlByPhone(@Param("phone") String phone, @Param("url") String url);

    @Select("SELECT t.code,t.value FROM app_sys_config t where t.effective='1' ")
    @Results({
            @Result(property = "code", column = "code"),
            @Result(property = "value", column = "value")
    })
    List<CommonKeyValueBean> getSysConfigBeanList();

    @Update("update app_sys_config t set value=#{value} where code=#{code}")
    int updateSysConfigByKey(@Param("code") String code, @Param("value") String value);

    @Select("SELECT t.value FROM app_sys_config t where t.effective='1' and code=#{code} ")
    String getSysConfigByKey(@Param("code") String code);

    @Insert("insert into app_verification_code (phone, code, type) values " +
            " (#{phone, jdbcType=VARCHAR}, #{code, jdbcType=VARCHAR}, #{type, jdbcType=VARCHAR}) ")
    void insertVerificationCode(@Param("phone") String phone, @Param("code") String code, @Param("type") String type);


    @Select("SELECT t.phone FROM app_verification_code t where t.phone=#{phone} and t.code=#{code}" +
            " and t.type=#{type} and t.effective='1' " +
            " and sysdate < (t.create_date + #{effectiveTime, jdbcType=BIGINT}/24/60/60) ")
    String checkVerificationCode(@Param("phone") String phone, @Param("code") String code,
                              @Param("type") String type, @Param("effectiveTime") long effectiveTime);

    @Insert("insert into so.sms_pending_cuc " +
            " (optsn,destnum,srctype,optcode,rtndata,port,area,smstype,pri,senddate,optdate " +
            " ,sendflag,region_id,template_id,media_task_id,batch_task_seq,op_id,org_id,complete_msg) " +
            " select to_char(sysdate, 'yyyymmddhh24miss') || so.sms_pending_cuc_fxf_seq.nextval optsn, " +
            " us.bill_id destnum,'5' srctype,'800015101015' optcode, #{content, jdbcType=VARCHAR}, " +
            " '10026' port,us.region_id area,'' smstype,'' pri,sysdate senddate,sysdate optdate,'0' sendflag, " +
            " us.region_id region_id,'11010030' template_id,'' media_task_id,'' batch_task_seq,'' op_id, " +
            " '' org_id,'' complete_msg from so.ins_user us where us.bill_id=#{phone, jdbcType=VARCHAR}  ")
    void insertVerificationCodeMsg(@Param("phone") String phone, @Param("content") String content);

    @Insert("insert into app_notice_read_log (phone, notice_id) values " +
            " (#{phone, jdbcType=VARCHAR}, #{noticeId, jdbcType=BIGINT}) ")
    void insertNoticeReadLog(@Param("phone") String phone, @Param("noticeId") long noticeId);

    @Select("select t.bill_id from wx.audit_cust_user_views t where t.cust_cert_code=#{custCertCode} ")
    List<String> getAllPhoneListByCustCertCode(@Param("custCertCode") String custCertCode);

    @Select("SELECT t.* FROM app_menu t where t.effective='1' and t.app_type=#{appType} " +
            "and t.menu_type=#{menuType} and t.app_version=#{appVersion}")
    @Results({
            @Result(property = "id",            column = "id"),
            @Result(property = "menuCode",      column = "menu_code"),
            @Result(property = "menuName",      column = "menu_name"),
            @Result(property = "parentCode",    column = "parent_code"),
            @Result(property = "picUrl",        column = "pic_url"),
            @Result(property = "linkUrl",       column = "link_url"),
            @Result(property = "sort",          column = "sort"),
            @Result(property = "menuType",      column = "menu_type"),
            @Result(property = "appType",       column = "app_type"),
            @Result(property = "appVersion",    column = "app_version"),
            @Result(property = "network",       column = "network"),
            @Result(property = "ext1",       column = "ext_1")
    })
    List<AppMenuBean> getAppMenuListByTypeAndVersion(@Param("appType") String appType, @Param("menuType") String menuType,
                                                     @Param("appVersion") String appVersion);

    @Select("SELECT t.* FROM app_menu t where t.effective='1' and t.app_type=#{appType} " +
            "and t.menu_type=#{menuType} and t.app_version=#{appVersion} " +
            "and (t.network=#{network} or t.network is null)")
    @Results({
            @Result(property = "id",            column = "id"),
            @Result(property = "menuCode",      column = "menu_code"),
            @Result(property = "menuName",      column = "menu_name"),
            @Result(property = "parentCode",    column = "parent_code"),
            @Result(property = "picUrl",        column = "pic_url"),
            @Result(property = "linkUrl",       column = "link_url"),
            @Result(property = "sort",          column = "sort"),
            @Result(property = "menuType",      column = "menu_type"),
            @Result(property = "appType",       column = "app_type"),
            @Result(property = "appVersion",    column = "app_version"),
            @Result(property = "network",       column = "network"),
            @Result(property = "ext1",          column = "ext_1")
    })
    List<AppMenuBean> getAppMenuListByTypeAndVersionAndNetwork(@Param("appType") String appType,
         @Param("menuType") String menuType, @Param("appVersion") String appVersion,
         @Param("network") String network);

    @Select("SELECT * FROM app_home_info WHERE app_type=#{appType} " +
            " and app_version=#{versionCode} and effective='1' ")
    @Results({
            @Result(property = "id",            column = "id"),
            @Result(property = "code",          column = "code"),
            @Result(property = "name",          column = "name"),
            @Result(property = "summary",       column = "summary"),
            @Result(property = "imageUrl",      column = "image_url"),
            @Result(property = "linkUrl",       column = "link_url"),
            @Result(property = "busiCode",      column = "busi_code"),
            @Result(property = "type",          column = "type"),
            @Result(property = "appType",       column = "app_type"),
            @Result(property = "appVersion",    column = "app_version"),
            @Result(property = "sort",          column = "sort"),
            @Result(property = "ext1",          column = "ext_1"),
            @Result(property = "ext2",          column = "ext_2")
    })
    List<AppHomeInfoBean> getAllEffectiveAppHomeInfoBeanList(@Param("appType") String appType,
                                                             @Param("versionCode") String versionCode);


    @Select("SELECT i.product_item_id,i.name,i.description, " +
            "to_char(i.eff_date,'yyyymmddhh24miss') as eff_date, " +
            "to_char(i.exp_date,'yyyymmddhh24miss') as exp_date, o.offer_type " +
            "from product.up_product_item i ,product.up_offer o " +
            "WHERE i.product_item_id=o.offer_id and o.offer_type<>'OFFER_VAS_PLOY' " +
            "and o.offer_type<>'OFFER_PLAN_BBOSS'")
    @Results({
            @Result(property = "offerId",            column = "product_item_id"),
            @Result(property = "offerName",          column = "name"),
            @Result(property = "offerDesc",          column = "description"),
            @Result(property = "effectiveDate",      column = "eff_date"),
            @Result(property = "expireDate",         column = "exp_date"),
            @Result(property = "offerType",          column = "offer_type"),
    })
    List<OfferInfo> getAllOfferInfoList();

    @SelectProvider(type=CommonDaoProvider.class,method="getAppVasOfferList")
    @Results({
            @Result(property = "offerId",            column = "offer_id"),
            @Result(property = "offerName",          column = "offer_name"),
            @Result(property = "productSpec",        column = "product_spec"),
            @Result(property = "offerDesc",          column = "offer_desc"),
            @Result(property = "offerType",          column = "offer_type"),
            @Result(property = "offerPrice",         column = "offer_price"),
            @Result(property = "vasType",            column = "vas_type"),
            @Result(property = "effectiveDate",      column = "effective_date"),
            @Result(property = "expireDate",         column = "expire_date")
    })
    List<OfferInfo> getAppVasOfferList(final Map<String, String> map);

    @Select("SELECT offer_id, offer_name, product_spec, offer_desc, offer_price, " +
            "offer_type, vas_type, to_char(effective_date,'yyyymmdd') as effective_date, " +
            "to_char(expire_date,'yyyymmdd') as expire_date, network " +
            "from app_vas_offer t " +
            "WHERE t.offer_id=#{offerId} ")
    @Results({
            @Result(property = "offerId",            column = "offer_id"),
            @Result(property = "offerName",          column = "offer_name"),
            @Result(property = "productSpec",        column = "product_spec"),
            @Result(property = "offerDesc",          column = "offer_desc"),
            @Result(property = "offerType",          column = "offer_type"),
            @Result(property = "vasType",            column = "vas_type"),
            @Result(property = "offerPrice",         column = "offer_price"),
            @Result(property = "effectiveDate",      column = "effective_date"),
            @Result(property = "expireDate",         column = "expire_date")
    })
    OfferInfo getAppVasOfferByOfferId(@Param("offerId") String offerId);
}
