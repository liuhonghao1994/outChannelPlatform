package com.dxt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dxt.boss.model.MainOfferInfo;
import com.dxt.boss.model.OfferInfo;
import com.dxt.common.MyPascalNameFilter;
import com.dxt.common.MyRandomUtil;
import com.dxt.dao.CommonDao;
import com.dxt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxt.common.AppConstant;
import com.dxt.dao.BusiCodeBeanDao;
import com.dxt.dao.VersionBeanDao;

@Service
public class CacheManager {
    
    @Autowired
    private BusiCodeBeanDao busiCodeBeanDao;
    @Autowired
    private VersionBeanDao versionBeanDao;
    @Autowired
    private CommonDao commonDao;


    private static ConcurrentHashMap<String, BusiCodeBean> busiMap;
    private static ConcurrentHashMap<String, String> sysConfigMap;
    private static ConcurrentHashMap<String, String> regionInfoMap;
    private static ConcurrentHashMap<String, AppServerMenu> appServerMenuMap;
    private static ConcurrentHashMap<String, AppPackageMenu> appPackageMenuMap;
    private static ConcurrentHashMap<String, AppMineMenu> appMineMenuMap;
    private static ConcurrentHashMap<String, JSONArray> appHomeInfoMap;
    private static ConcurrentHashMap<String, OfferInfo> allOfferInfoMap;
    private static ConcurrentHashMap<String, List<OfferInfo>> vasOfferInfoMap;

    /**
     * 当前最新版本
     */
    private static AppVersionBean androidLastVersionBean = null;
    private static AppVersionBean iosLastVersionBean = null;
    /**
     * 当前最新强制升级版本
     */
    private static AppVersionBean androidForceVersionBean = null;
    private static AppVersionBean iosForceVersionBean = null;
    
    
    /**
     * @desc: 刷新缓存
     * @return
     */
    public boolean initCache() {
        boolean ret = false;
        try {
            initBusiMap();//加载业务编码，把所有的busicode码放入map集合当中
            initVersionBean();//加载版本信息
            initSysConfigMap();//将系统参数放入map集合
            initAppServerMenuMap();//创建map
            initAppMineMenuMap();
            initAppPackageMenuMap();
            initAppHomeInfoMap();
            initAllOfferInfoMap();
            initVasOfferInfoMap();
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private void initVasOfferInfoMap() {
        try {
            if (null == vasOfferInfoMap) {
                vasOfferInfoMap = new ConcurrentHashMap<>();
            } else {
                vasOfferInfoMap.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAllOfferInfoMap() {
        try {
            if (null == allOfferInfoMap) {
                allOfferInfoMap = new ConcurrentHashMap<>();
            } else {
                allOfferInfoMap.clear();
            }
            List<OfferInfo> list = commonDao.getAllOfferInfoList();
            for (OfferInfo info : list) {
                if (info.getOfferType().equals(OfferInfo.OFFERTYPE_OFFER_VAS_OTHER)) {
                    OfferInfo tmp = commonDao.getAppVasOfferByOfferId(info.getOfferId());
                    if (null != tmp) {
                        info.setVasType(tmp.getVasType());
                    }
                }
                allOfferInfoMap.put(info.getOfferId(), info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAppServerMenuMap() {
        if (null == appServerMenuMap) {
            appServerMenuMap = new ConcurrentHashMap<>();
        } else {
            appServerMenuMap.clear();
        }
    }

    private void initAppMineMenuMap() {
        if (null == appMineMenuMap) {
            appMineMenuMap = new ConcurrentHashMap<>();
        } else {
            appMineMenuMap.clear();
        }
    }

    private void initAppPackageMenuMap() {
        if (null == appPackageMenuMap) {
            appPackageMenuMap = new ConcurrentHashMap<>();
        } else {
            appPackageMenuMap.clear();
        }
    }

    /**
     * @desc:加载业务编码
     */
    private void initBusiMap() {
        try {
            if (null == busiMap) {
                busiMap = new ConcurrentHashMap<>();
            } else {
                busiMap.clear();
            }
            List<BusiCodeBean> list = busiCodeBeanDao.getAllBusiCodeBeans();
            for (BusiCodeBean busiCodeBean : list) {
                busiMap.put(busiCodeBean.getBusiCode(), busiCodeBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc:通过业务code获取业务对象
     * @param code
     * @return
     */
    public BusiCodeBean getBusiByCode(String code) {
        if (null == busiMap) {
            initBusiMap();
        }
        return (null != busiMap && null != code && busiMap.containsKey(code) && !""
                .equals(code)) ? busiMap.get(code) : null;
    }

    /**
     * @desc:加载系统参数
     */
    private void initSysConfigMap() {
        try {
            if (null == sysConfigMap) {
                sysConfigMap = new ConcurrentHashMap<>();
            } else {
                sysConfigMap.clear();
            }
            List<CommonKeyValueBean> list = commonDao.getSysConfigBeanList();
            for (CommonKeyValueBean commonKeyValueBean : list) {
                sysConfigMap.put(commonKeyValueBean.getCode(), commonKeyValueBean.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc:通过key获取参数value
     * @param key
     * @return
     */
    public String getSysConfigByCode(String key) {
        if (null == sysConfigMap) {
            initSysConfigMap();
        }
        return (null != sysConfigMap && null != key && sysConfigMap.containsKey(key) && !""
                .equals(key)) ? sysConfigMap.get(key) : null;
    }

    /**
     * @desc:加载地市信息 TODO
     */
    private void initRegionInfoMap() {
        try {
            if (null == regionInfoMap) {
                regionInfoMap = new ConcurrentHashMap<>();
            } else {
                regionInfoMap.clear();
            }
            List<CommonKeyValueBean> list = commonDao.getRegionInfoList();
            for (CommonKeyValueBean commonKeyValueBean : list) {
                regionInfoMap.put(commonKeyValueBean.getCode(), commonKeyValueBean.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc:通过key获取地市value
     * @param key
     * @return
     */
    public String getRegionInfoByCode(String key) {
        if (null == regionInfoMap) {
            initRegionInfoMap();
        }
        return (null != regionInfoMap && null != key && regionInfoMap.containsKey(key) && !""
                .equals(key)) ? regionInfoMap.get(key) : null;
    }

    /**
     * 加载版本信息
     */
    private void loadVersionBean(String type) {
        try {
            if (AppConstant.APP_TYPE.ANDROID.getValue().equals(type)) {
                if (null != androidLastVersionBean) {
                    androidLastVersionBean = null;
                }
                androidLastVersionBean = versionBeanDao.getLastVersionBeanByType(type);
                if (null != androidForceVersionBean) {
                    androidForceVersionBean = null;
                }
                androidForceVersionBean = versionBeanDao.getForceVersionBeanByType(type);
            }
            
            if (AppConstant.APP_TYPE.IOS.getValue().equals(type)) {
                if (null != iosLastVersionBean) {
                    iosLastVersionBean = null;
                }
                iosLastVersionBean = versionBeanDao.getLastVersionBeanByType(type);
                if (null != iosForceVersionBean) {
                    iosForceVersionBean = null;
                }
                iosForceVersionBean = versionBeanDao.getForceVersionBeanByType(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化重要版本信息
     * 每次客户端升级后，需要刷新缓存
     * @return
     */
    public boolean initVersionBean() {
        boolean ret = false;
        try {
            loadVersionBean(AppConstant.APP_TYPE.ANDROID.getValue());
            loadVersionBean(AppConstant.APP_TYPE.IOS.getValue());
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
            androidLastVersionBean = null;
            androidForceVersionBean = null;
            iosLastVersionBean = null;
            iosForceVersionBean = null;
        }
        return ret;
    }

    public AppVersionBean getLastVersionBean(String type) {
        if (AppConstant.APP_TYPE.ANDROID.getValue().equals(type)) {
            if (null == androidLastVersionBean) {
                initVersionBean();
            }
            return androidLastVersionBean;
        }
        if (AppConstant.APP_TYPE.IOS.getValue().equals(type)) {
            if (null == iosLastVersionBean) {
                initVersionBean();
            }
            return iosLastVersionBean;
        }
        return null;
    }

    public AppVersionBean getForceVersionBean(String type) {
        if (AppConstant.APP_TYPE.ANDROID.getValue().equals(type)) {
            if (null == androidForceVersionBean) {
                initVersionBean();
            }
            return androidForceVersionBean;
        }
        if (AppConstant.APP_TYPE.IOS.getValue().equals(type)) {
            if (null == iosForceVersionBean) {
                initVersionBean();
            }
            return iosForceVersionBean;
        }
        return null;
    }

    public AppServerMenu getAppServerMenu(String appType, String menuType, String appVersion) {
        String key = appType + "_" + menuType + "_" + appVersion;
        if (null == appServerMenuMap) {
            initAppServerMenuMap();
        }
        AppServerMenu appServerMenu = appServerMenuMap.get(key);
        if (null == appServerMenu) {
            appServerMenu = getAppServerMenuFromDB(appType, menuType, appVersion);
        }
        appServerMenuMap.put(key, appServerMenu);
        return appServerMenu;
    }

    private AppServerMenu getAppServerMenuFromDB(String appType, String menuType, String appVersion) {
        AppServerMenu appServerMenu = new AppServerMenu();
        List<AppMenuBean> list = commonDao.getAppMenuListByTypeAndVersion(appType, menuType, appVersion);
        appServerMenu.createAppServerMenuByAppMenuBeanList(list);
        return appServerMenu;
    }

    public AppMineMenu getAppMineMenu(String appType, String menuType, String appVersion) {
        String key = appType + "_" + menuType + "_" + appVersion;
        if (null == appMineMenuMap) {
            initAppMineMenuMap();
        }
        AppMineMenu appMineMenu = appMineMenuMap.get(key);
        if (null == appMineMenu) {
            appMineMenu = getAppMineMenuFromDB(appType, menuType, appVersion);
        }
        appMineMenuMap.put(key, appMineMenu);
        return appMineMenu;
    }

    private AppMineMenu getAppMineMenuFromDB(String appType, String menuType, String appVersion) {
        AppMineMenu appMineMenu = new AppMineMenu();
        List<AppMenuBean> list = commonDao.getAppMenuListByTypeAndVersion(appType, menuType, appVersion);
        appMineMenu.createAppMineMenuByAppMenuBeanList(list);
        return appMineMenu;
    }

    public AppPackageMenu getAppPackageMenu(String appType, String menuType, String appVersion, String network) {
        String key = appType + "_" + menuType + "_" + appVersion;
        if (null == network) {
            key += "_all";
        } else {
            key += "_" + network;
        }
        if (null == appPackageMenuMap) {
            initAppPackageMenuMap();
        }
        AppPackageMenu appPackageMenu = appPackageMenuMap.get(key);
        if (null == appPackageMenu) {
            appPackageMenu = getAppPackageMenuFromDB(appType, menuType, appVersion, network);
        }
        appPackageMenuMap.put(key, appPackageMenu);
        return appPackageMenu;
    }

    private AppPackageMenu getAppPackageMenuFromDB(String appType, String menuType, String appVersion, String network) {
        AppPackageMenu appPackageMenu = new AppPackageMenu();
        List<AppMenuBean> list;
        if (null == network) {
            list = commonDao.getAppMenuListByTypeAndVersion(appType, menuType, appVersion);
        } else {
            list = commonDao.getAppMenuListByTypeAndVersionAndNetwork(appType, menuType, appVersion, network);
        }
        appPackageMenu.createAppPackageMenuByAppMenuBeanList(list);
        return appPackageMenu;
    }

    public JSONArray getAppHomeInfoContent(String appType, String versionCode, String infoType) {
        String key = appType + "_" + versionCode + "_" + infoType;
        if (null == appHomeInfoMap) {
            addAppHomeInfo(appType, versionCode);
        }
        JSONArray jsonArray = appHomeInfoMap.get(key);
        if (null == jsonArray || 0 == jsonArray.size()) {
            addAppHomeInfo(appType, versionCode);
            jsonArray = appHomeInfoMap.get(key);
        }
        if (null == jsonArray || 0 == jsonArray.size()) {
            return new JSONArray();
        }
        return jsonArray;
    }

    private void initAppHomeInfoMap() {
        if (null == appHomeInfoMap) {
            appHomeInfoMap = new ConcurrentHashMap<>();
        } else {
            appHomeInfoMap.clear();
        }
    }

    private void addAppHomeInfo(String appType, String versionCode) {
        if (null == appHomeInfoMap) {
            appHomeInfoMap = new ConcurrentHashMap<>();
        }
        List<AppHomeInfoBean> list = commonDao.getAllEffectiveAppHomeInfoBeanList(appType, versionCode);
        List<AppHomeFuction> appHomeFuctionList = new ArrayList<>();
        List<AppHomeNews> appHomeNewsList = new ArrayList<>();
        List<AppHomeAdvertisement> appHomeAdvertisementList = new ArrayList<>();
        List<AppPromotion> appPromotionList = new ArrayList<>();
        for (AppHomeInfoBean appHomeInfoBean : list) {
            if (AppConstant.APP_CONSTANT.HOME_TYPE_FUNCTION.equals(appHomeInfoBean.getType())) {
                appHomeFuctionList.add(new AppHomeFuction(appHomeInfoBean));
            } else if (AppConstant.APP_CONSTANT.HOME_TYPE_NEWS.equals(appHomeInfoBean.getType())) {
                appHomeNewsList.add(new AppHomeNews(appHomeInfoBean));
            } else if (AppConstant.APP_CONSTANT.HOME_TYPE_AD.equals(appHomeInfoBean.getType())) {
                appHomeAdvertisementList.add(new AppHomeAdvertisement(appHomeInfoBean));
            } else if (AppConstant.APP_CONSTANT.HOME_TYPE_PROMOTION.equals(appHomeInfoBean.getType())) {
                appPromotionList.add(new AppPromotion(appHomeInfoBean));
            } else {
            }
        }
        String preKey = appType + "_" + versionCode + "_";
        appHomeInfoMap.put(preKey + AppConstant.APP_CONSTANT.HOME_TYPE_FUNCTION,
                JSONArray.parseArray(JSON.toJSONString(appHomeFuctionList, new MyPascalNameFilter())));
        appHomeInfoMap.put(preKey + AppConstant.APP_CONSTANT.HOME_TYPE_NEWS,
                JSONArray.parseArray(JSON.toJSONString(appHomeNewsList, new MyPascalNameFilter())));
        appHomeInfoMap.put(preKey + AppConstant.APP_CONSTANT.HOME_TYPE_AD,
                JSONArray.parseArray(JSON.toJSONString(appHomeAdvertisementList, new MyPascalNameFilter())));
        appHomeInfoMap.put(preKey + AppConstant.APP_CONSTANT.HOME_TYPE_PROMOTION,
                JSONArray.parseArray(JSON.toJSONString(appPromotionList, new MyPascalNameFilter())));
    }

    public void updateSysConfigValue(String key, String value) {
        if (null == key) {
            return;
        }
        if (null == value) {
            value = MyRandomUtil.getRandomString(10);
        }
        sysConfigMap.put(key, value);
        commonDao.updateSysConfigByKey(key, value);
    }

    public OfferInfo getOfferInfoByOfferId(String offerId) {
        if (null == allOfferInfoMap) {
            initAllOfferInfoMap();
        }
        return allOfferInfoMap.get(offerId);
    }

    public List<OfferInfo> getVasOfferInfoByMap(final Map<String, String> map) {
        if (null == vasOfferInfoMap) {
            vasOfferInfoMap = new ConcurrentHashMap<>();
        }
        StringBuffer buf = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            buf.append(entry.getValue());
        }
        String key = buf.toString();
        List<OfferInfo> list = vasOfferInfoMap.get(key);
        if (null == list || 0 == list.size()) {
            list = commonDao.getAppVasOfferList(map);
        }
        if (null == list || 0 == list.size()) {
            return null;
        }
        vasOfferInfoMap.put(key, list);
        return list;
    }

}
