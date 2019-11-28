package com.dxt.common;

public class AppConstant {

    public interface REQUEST_REPONSE_PARAM {
        String PARAM_IN_PHONE = "phone";
        String PARAM_IN_BILLINGCYCLE = "billingCycle";
        String PARAM_IN_QUERYTYPE = "queryType";
        String PARAM_IN_STRSTARTDATE = "strStartDate";
        String PARAM_IN_STRENDDATE = "strEndDate";
        String PARAM_IN_PASSWORD = "password";
        String PARAM_IN_NEWPWD = "newPwd";
        String PARAM_IN_OFFERID = "offerId";
        String PARAM_IN_IMEI = "imei";
        String PARAM_IN_PUSHID = "pushId";
        String PARAM_IN_KEEPLOGIN = "keepLogin";
        String PARAM_IN_OPERTYPE="operType";

        String PARAM_OUT_USERINFO = "userBasicInfo";
        String PARAM_OUT_USERAVATARURL = "userAvatarUrl";
        String PARAM_OUT_BALANCEINFO = "balanceInfo";
        String PARAM_OUT_REALTIMEBILLINFO = "realTimeBillInfo";
        String PARAM_OUT_MONTHBILLINFO = "monthBillInfo";
        String PARAM_OUT_MONTHBILLINFOLIST = "monthBillInfoList";
        String PARAM_OUT_CALLCDR = "callCDR";
        String PARAM_OUT_NEICDR = "netCDR";
        String PARAM_OUT_SMSMMSMAINCDR = "smsMmsMainCDR";
        String PARAM_OUT_PAYANDWRITEOFFRECLIST = "payAndWriteoffRecList";
        String PARAM_OUT_MONTHPAYANDWRITEOFFRECLIST = "monthPayAndWriteoffRecList";
        String PARAM_OUT_USERBUSINESSRECORDLIST = "userBusiRecordList";
        String PARAM_OUT_MONTHUSERBUSINESSRECORDLIST = "monthUserBusiRecordList";
        String PARAM_OUT_FREERESOURCEINFOLIST = "freeResourceInfoList";
        String PARAM_OUT_USEDRESINFO = "usedResInfo";
        String PARAM_OUT_OFFERINFOLIST = "offerInfoList";
        String PARAM_OUT_MAINOFFERINFO = "mainOfferInfo";
        String PARAM_OUT_OFFERINFO = "offerInfo";
        String PARAM_OUT_AVAILMAINOFFERLIST = "availMainOfferList";
        String PARAM_OUT_AVAILVASOFFERLIST = "availVasOfferList";
        String PARAM_OUT_SERVERMENU = "serverMenu";
        String PARAM_OUT_MINEMENU = "mineMenu";
        String PARAM_OUT_PACKAGEMENU = "packageMenu";

    }



    public interface SYS_CONFIG_KEY {
        String KEY_ADMIN_USERNAME = "ADMIN_USERNAME";
        String KEY_ADMIN_PASSWORD = "ADMIN_PASSWORD";
        String KEY_TEST_MODE = "TEST_MODE";
        String KEY_TIMESTAMP_MAX_DIFF = "TIMESTAMP_MAX_DIFF";
        String KEY_BUSICODE_GETVERSION = "BUSICODE_GETVERSION";
        String KEY_USER_STATE_OK = "USER_STATE_OK";
        String KEY_USER_OSSTATUS_OK = "USER_OSSTATUS_OK";
        String KEY_VERIFICATION_CODE_EFFECTIVE_TIME = "VERIFICATION_CODE_EFFECTIVE_TIME";
        String KEY_VERIFICATION_CODE_TYPE_PASSWORD = "VERIFICATION_CODE_TYPE_PASSWORD";
        String KEY_UPLOAD_FILE_DIR = "UPLOAD_FILE_DIR";
        String KEY_UPLOAD_FILE_URL = "UPLOAD_FILE_URL";
        String KEY_UPLOAD_FILE_FTP = "UPLOAD_FILE_FTP";
        String KEY_UPLOAD_FILE_FTP_IP = "UPLOAD_FILE_FTP_IP";
        String KEY_UPLOAD_FILE_FTP_PORT = "UPLOAD_FILE_FTP_PORT";
        String KEY_UPLOAD_FILE_FTP_NAME = "UPLOAD_FILE_FTP_NAME";
        String KEY_UPLOAD_FILE_FTP_PWD = "UPLOAD_FILE_FTP_PWD";
        String KEY_UPLOAD_FILE_FTP_BASEPATH = "UPLOAD_FILE_FTP_BASEPATH";
        String KEY_QUERY_USED_RES_URL = "QUERY_USED_RES_URL";
        String KEY_APP_SESSION_ID_STR = "APP_SESSION_ID_STR";
        String KEY_APP_SESSION_EFFECTIVE_TIME = "APP_SESSION_EFFECTIVE_TIME";
        String KEY_REFRESH_CACHE_VERIFICATION_CODE = "REFRESH_CACHE_VERIFICATION_CODE";
        String KEY_CLEAR_SESSION_VERIFICATION_CODE = "CLEAR_SESSION_VERIFICATION_CODE";
        String KEY_UIP_SOCKET_IP = "UIP_SOCKET_IP";
        String KEY_UIP_SOCKET_PORT = "UIP_SOCKET_PORT";
        String KEY_UIP_SOCKET_TIMEOUT = "UIP_SOCKET_TIMEOUT";
        String KEY_SYS_DISTRIBUTED_DEPLOYMENT = "SYS_DISTRIBUTED_DEPLOYMENT";
        /**
         * 推送服务相关配置
         */
        String KEY_PUSH_APPID = "PUSH_APPID";
        String KEY_PUSH_APPSECRET = "PUSH_APPSECRET";
        String KEY_PUSH_URL = "PUSH_URL";

        String KEY_PUSH_APPKEY = "PUSH_APPKEY";
        String KEY_PUSH_MASTERSECRET = "PUSH_MASTERSECRET";

    }

    /**
     * 推送服务相关常量
     */
    public interface PUSH_CONSTANT {
        String PARAM_TYPE = "paramType";
        String PARAM_ALL = "ALL";
        String PARAM_TARGET = "paramTarget";
        String PARAM_OS = "paramOS";

    }

    public enum REQUEST_SOURCE {
        APP("app"),
        OPEN("open");

        private String value;

        REQUEST_SOURCE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        private void setValue(String value) {
            this.value = value;
        }
    }

    public enum APP_TYPE {
        ANDROID("android"),
        IOS("ios");

        private String value;

        APP_TYPE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        private void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 系统固定参数
     */
    public interface SYS_CONSTANT {
        /**
         * 访问权限需要登录
         */
        String REQUIRE_LOGIN = "1";
        /**
         * 产品ID干扰码
         * 从boss获取的offerId都要加此干扰码传递到外部
         */
        Long PROD_INTERFERENCE_CODE = 22334998877L;
        /**
         * 正序排序(升序)
         */
        String SORT_ASC = "asc";
        /**
         * 倒序排序(降序)
         */
        String SORT_DESC = "desc";
        /**
         * session非长期有效（有失效时长）
         */
        String APPUSER_SESSION_NO_EFFECTIVE = "0";

        String CONSTANT_TURE = "true";
    }

    /**
     * 返回错误码
     */
    public interface REPONSE_CODE {
        String CODE = "code";
        /**
         * 通用请求成功代码
         */
        String OK = "0000";
        /**
         * 业务错误提醒，用于msg信息直接展示给用户的场景
         */
        String BUSI_WARNING = "1000";
        /**
         * 登录提醒，用于msg信息直接展示给用户的场景
         */
        String LOGIN_WARNING = "2000";
        /**
         * 业务错误提醒，用于msg信息不可直接展示给用户的场景，即联调或分析使用
         */
        String BUSI_ERROR = "8000";
        /**
         * 系统级错误，msg信息供联调或分析使用
         */
        String SYS_ERROE = "9999";
    }

    /**
     * 返回信息
     */
    public interface REPONSE_MSG {
        String MSG = "msg";
        String SYS_REQUEST_OK_MSG = "成功！";
        String SYS_ERROR_MSG = "系统错误，请稍后再试！";
        String SYS_LOGIN_OK_MSG = "登录成功！";
        String SYS_LOGIN_FIRST_MSG = "请先登录！";
        String SYS_LOGIN_PASS_ERROR_MSG = "用户名或密码错误！";
        String SYS_LOGOUT_OK_MSG = "退出登录成功！";
        String SYS_PARAM_ERROR_MSG = "请求参数有误，请检查参数是否正确！";
        String SYS_PHONE_STATE_ERROR_MSG = "号码非正常状态！";
        String SYS_VERIFIY_ERROR_MSG = "验证未通过！";
        String BUSI_ERROR_MSG = "此业务您暂时无法办理！";
        String SYS_PARAM_ERROR_SECRET_KEY="密钥鉴权失败";
        String SYS_REQUEST_FAIL_MSG="失败!";
    }

    /**
     * app端相关固定配置
     */
    public interface APP_CONSTANT {
        /**
         * app首页相关信息
         */
        String HOME_TYPE_FUNCTION = "function";
        String HOME_TYPE_NEWS = "news";
        String HOME_TYPE_AD = "ad";
        String HOME_TYPE_PROMOTION = "promotion";
        String HOME_STR_LIST_FUCTION = "homeFunctionList";
        String HOME_STR_LIST_NEWS = "homeNewsList";
        String HOME_STR_LIST_AD = "homeAdList";
        String HOME_STR_LIST_PROMOTION = "promotionList";
        /**
         * app版本相关信息
         */
        String VERSION_FORCE_UPDATE = "1";
        String VERSION_REQUEST_TYPE_LAST = "0";
        String VERSION_REQUEST_TYPE_DESIGNATED = "1";
        /**
         * app上传信息
         */
        String UPLOAD_TYPE_USER = "user";
        String UPLOAD_TYPE_ERROR = "error";
        String UPLOAD_TYPE_SUGGESTION = "suggestion";
    }

    public interface USER_INFO {
        String PWD_VERIFY_TYPE_OLDPWD = "1";
        String PWD_VERIFY_TYPE_VERIFICATIONCODE = "2";
    }

    
}
