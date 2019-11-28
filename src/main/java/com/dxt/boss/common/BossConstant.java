package com.dxt.boss.common;

public class BossConstant {
    public interface ID_CONSTANTS {
        int MIN_SEQ = 1001;
        int MAX_SEQ = 10000;
        String TYPE_LONG = "long";
        String TYPE_SHORT = "short";
    }


    /**
     * UIP通用参数及常量
     */
    public interface UIP_COMMON_PARAM {
        String STR_UIP_RET_RESPONSE = "Response";
        String STR_UIP_RET_ERRORINFO = "ErrorInfo";
        String STR_UIP_RET_RETINFO = "RetInfo";
        String STR_UIP_RET_CODE = "Code";
        String STR_UIP_RET_CODE_0000 = "0000";
        /**
         * 查询无结果
          */
        String STR_UIP_RET_CODE_1045 = "1045";
        /**
         * 业务执行失败
         */
        String STR_UIP_RET_CODE_3004 = "3004";
        String STR_UIP_RET_MESSAGE = "Message";

        String STR_INTERFACE_NAME = "STR_INTERFACE_NAME";
        String STR_BUSI_PARAMS = "BusiParams";
        String STR_BUSI_CODE = "BusiCode";
        String STR_PUB_INFO = "PubInfo";
        String STR_REQUEST = "Request";

        String STR_TRANSACTION_ID = "TransactionId";
        String STR_TRANSACTION_TIME = "TransactionTime";
        String STR_INTERFACE_ID = "InterfaceId";
        String STR_INTERFACE_TYPE = "InterfaceType";
        String STR_OP_ID = "OpId";
        String STR_COUNTY_CODE = "CountyCode";
        String STR_ORG_ID = "OrgId";
        String STR_REGION_CODE = "RegionCode";
        String STR_CLIENT_IP = "ClientIP";

        String PARAM_INTERFACE_ID = "";
        String PARAM_INTERFACE_TYPE = "4";
        String PARAM_OP_ID = "40061860";
        String PARAM_COUNTY_CODE = "400";
        String PARAM_ORG_ID = "40061860";
        String PARAM_REGION_CODE = "4001";
        String PARAM_CLIENT_IP = "";

        String TRANSACTION_ID_PREFIX = "DXTAPP";
    }

    /**
     * UIP请求参数
     */
    public interface UIP_REQUEST_PARAM {
        String PARAM_SERVICENUM = "ServiceNum";
        String PARAM_BILLINGCYCLE = "BillingCycle";
        String PARAM_SUBJECTLEVEL = "SubjectLevel";
        String PARAM_NQUERYMODE = "nQueryMode";
        String PARAM_STRPHONE = "strPhone";
        String PARAM_ISMERGE = "isMerge";
        String PARAM_STARTDATE = "bgnDate";
        String PARAM_ENDDATE = "endDate";
        String PARAM_OPFLAG = "OpFlag";
        String PARAM_BUSITYPE = "BusiType";
        String PARAM_STARTTIME = "StartTime";
        String PARAM_ENDTIME = "EndTime";
        String PARAM_QUERYTYPE = "QueryType";
        String PARAM_STRSTARTDATE = "strStartDate";
        String PARAM_STRENDDATE = "strEndDate";
        String PARAM_PASSWORD = "Password";
        String PARAM_ISVERIFYOLDPWD = "IsVerifyOldPwd";
        String PARAM_NEWPWD = "NewPwd";
        String PARAM_ISVERIFYCERTCARD = "IsVerifyCertCard";
        String PARAM_ISSMSNOTIFY = "IsSMSNotify";
        String PARAM_OFFERID = "OfferId";
        String PARAM_VASOFFERINFO = "VasOfferInfo";
        String PARAM_OPERTYPE="OperType";
    }

    /**
     * UIP返回参数
     */
    public interface UIP_RESPONSE_PARAM {
        String PARAM_REALTIMEBILLINFO = "RealTimeBillInfo";
        String PARAM_CALLCDR = "CallCDR";
        String PARAM_NETCDR = "NetCDR";
        String PARAM_SMSMMSMAINCDR = "SmsMmsMainCDR";
        String PARAM_PAYANDWRITEOFFREC = "PayAndWriteoffRec";
        String PARAM_BUSIRECINFO = "BusiRecInfo";
        String PARAM_FREERESOURCEINFO = "FeeResourceInfo";
        String PARAM_RESULT = "result";
        String PARAM_OFFERINFO = "OfferInfo";
        String PARAM_OPERATORCODE = "OperatorCode";
        String PARAM_AVAILOFFERS = "AvailOffers";

        String PARAM_ISWEAKPWD = "IsWeakPwd";
        String PARAM_RETRYTIMES = "RetryTimes";
        String PARAM_ISWEAKPWD_VALUE = "0";
        String PARAM_USERINFO = "UserInfo";
        String PARAM_ACCTINFO = "AcctInfo";
        String PARAM_CUSTINFO = "CustInfo";
        String PARAM_REGIONID = "RegionId";
        String PARAM_OSSTATUS = "OsStatus";
        String PARAM_STATE = "State";
        String PARAM_ACCSTATUS = "AccStatus";
        String PARAM_CUSTNAME = "CustName";
        String PARAM_CUSTCERTTYPE = "CustCertType";
        String PARAM_CUSTCERTCODE = "CustCertCode";
        String PARAM_OFFERID = "OfferId";
        String PARAM_USERID = "UserId";
        String PARAM_ACCID = "AccId";
        String PARAM_OFFERNAME = "OfferName";
        String PARAM_OFFERTYPE = "OfferType";
        String PARAM_OFFERTYPE_OFFER_PLAN_GSM = "OFFER_PLAN_GSM";
        String PARAM_DONECODE = "DoneCode";

    }


    public interface UIP_API {
        /**
         * 活动订购
         */

        String OI_ORDER_PROMOTION="OI_OrderPromotion";
        /**
         * 验证用户服务密码
         */
        String OI_VERIFY_USER_PASSWORD = "OI_VerifyUserPassword";
        /**
         * 查询用户信息(全部)
         */
        String OI_GET_USER_ALL_INFO = "OI_GetUserAllInfo";
        /**
         * 查询用户信息(基本)
         */
        String OI_GET_USER_BASE_INFO = "OI_GetUserBaseInfo";
        /**
         * 获取系统日期
         */
        String OI_GET_SYS_DATE_TIME = "OI_GetSysDateTime";

        /**
         * 查询帐户余额
         */
        String OI_GET_ACCOUNT_BALANCE = "OI_GetAccountBalance";
        /**
         * 查询用户实时话费
         */
        String OI_GET_USER_REAL_TIME_BILL = "OI_GetUserRealTimeBill";
        /**
         * 查询用户历史欠费
         */
        String OI_GET_USER_HIS_OWED_BILL = "OI_GetUserHisOwedBill";
        /**
         * 查询用户免费资源（非合并）
         */
        String OI_GET_USER_FREE_RES = "OI_GetUserFreeRes";
        /**
         * 查询用户免费资源（合并）
         */
        String OI_GET_USER_FREE_RES_COM = "OI_GetUserFreeResCom";
        /**
         * 查询用户免费资源使用情况（合并）
         */
        String OI_GET_USER_CUR_AND_LAST_FREE_RES_COM = "OI_GetUserCurAndLastFreeResCom";
        /**
         * 修改用户服务密码
         */
        String OI_UPDATE_USER_PASSWORD = "OI_UpdateUserPassword";
        /**
         * 查询客户下的手机号码
         */
        String OI_GET_CUSTOMER_PHONE_NUMBER = "OI_GetCustomerPhoneNumber";
        /**
         * 查询客户信息
         */
        String OI_GET_CUSTOMER_INFO = "OI_GetCustomerInfo";
        /**
         * 查询用户帐单（详细）
         */
        String OI_GET_USER_BILL_DETAIL = "OI_GetUserBillDetail";
        /**
         * 查询通话详单
         */
        String OI_GET_CALL_CDR = "OI_GetCallCDR";
        /**
         * 查询上网详单
         */
        String OI_GET_NET_CDR = "OI_GetNetCDR";
        /**
         * 查询短彩信详单
         */
        String OI_GET_SMS_AND_MMS_CDR = "OI_GetSmsAndMMSCDR";
        /**
         * 查询充值记录
         */
        String OI_GET_PAY_AND_WRITEOFF_REC = "OI_GetPayAndWriteoffRec";
        /**
         * 查询用户业务受理记录
         */
        String OI_GET_USER_BUSINESS_RECORD = "OI_GetUserBusinessRecord";
        /**
         * 查询用户指定策划信息
         */
        String OI_GET_USER_SPEC_OFFER = "OI_GetUserSpecOffer";
        /**
         * 查询用户主策划（或套餐）
         */
        String OI_GET_USER_MAIN_OFFER = "OI_GetUserMainOffer";
        /**
         * 查询可订购的主策划
         */
        String OI_GET_USER_AVAIL_MAIN_OFFER = "OI_GetUserAvailMainOffer";
        /**
         * 查询可订购的增值策划
         */
        String OI_GET_USER_AVAIL_VAS_OFFER = "OI_GetUserAvailVasOffer";
        /**
         * 查所属网络（路由接口）
         */
        String OI_QUERY_CARRIER_OPERATOR = "OI_QueryCarrierOperator";
        /**
         * 修改用户主策划（或套餐）
         */
        String OI_UPDATE_USER_MAIN_OFFER = "OI_UpdateUserMainOffer";
        /**
         * 订购增值策划
         */
        String OI_ORDER_VAS_OFFER = "OI_OrderVasOffer";


    }

}
