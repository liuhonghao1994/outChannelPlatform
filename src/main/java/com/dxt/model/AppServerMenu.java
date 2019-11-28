package com.dxt.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppServerMenu {

    private List<FirstLevelMenu> firstLevelList = new ArrayList<>();

    public List<FirstLevelMenu> getFirstLevelList() {
        return firstLevelList;
    }

    public void setFirstLevelList(List<FirstLevelMenu> firstLevelList) {
        this.firstLevelList = firstLevelList;
    }

    public void createAppServerMenuByAppMenuBeanList(List<AppMenuBean> list) {
        Iterator<AppMenuBean> iterator = list.iterator();
        while(iterator.hasNext()){
            AppMenuBean appMenuBean = iterator.next();
            if (null == appMenuBean.getParentCode()) {
                firstLevelList.add(new FirstLevelMenu(appMenuBean));
                iterator.remove();
            }
        }
        for (FirstLevelMenu firstLevelMenu : firstLevelList) {
            iterator = list.iterator();
            while (iterator.hasNext()) {
                AppMenuBean appMenuBean = iterator.next();
                if (appMenuBean.getParentCode().equals(firstLevelMenu.getFirstLevelCode())) {
                    firstLevelMenu.getSecondLevelList().add(new SecondLevelMenu(appMenuBean));
                    iterator.remove();
                }
            }
        }
    }

    static class FirstLevelMenu {

        private String firstLevelCode;
        private String firstLevelName;
        private Integer firstLevelSort;
        private List<SecondLevelMenu> secondLevelList;

        public FirstLevelMenu(AppMenuBean appMenuBean) {
            this.firstLevelCode = appMenuBean.getMenuCode();
            this.firstLevelName = appMenuBean.getMenuName();
            this.firstLevelSort = Integer.valueOf(appMenuBean.getSort());
            this.secondLevelList = new ArrayList<>();
        }

        public String getFirstLevelCode() {
            return firstLevelCode;
        }

        public void setFirstLevelCode(String firstLevelCode) {
            this.firstLevelCode = firstLevelCode;
        }

        public String getFirstLevelName() {
            return firstLevelName;
        }

        public void setFirstLevelName(String firstLevelName) {
            this.firstLevelName = firstLevelName;
        }

        public Integer getFirstLevelSort() {
            return firstLevelSort;
        }

        public void setFirstLevelSort(Integer firstLevelSort) {
            this.firstLevelSort = firstLevelSort;
        }

        public List<SecondLevelMenu> getSecondLevelList() {
            return secondLevelList;
        }

        public void setSecondLevelList(List<SecondLevelMenu> secondLevelList) {
            this.secondLevelList = secondLevelList;
        }
    }

    static class SecondLevelMenu {

        private String secondLevelCode;
        private String secondLevelName;
        private Integer secondLevelSort;
        private String secondLevelPicUrl;
        private String secondLevelLinkUrl;

        public SecondLevelMenu(AppMenuBean appMenuBean) {
            this.secondLevelCode = appMenuBean.getMenuCode();
            this.secondLevelName = appMenuBean.getMenuName();
            this.secondLevelSort = Integer.valueOf(appMenuBean.getSort());
            this.secondLevelPicUrl = appMenuBean.getPicUrl();
            this.secondLevelLinkUrl = appMenuBean.getLinkUrl();
        }

        public String getSecondLevelCode() {
            return secondLevelCode;
        }

        public void setSecondLevelCode(String secondLevelCode) {
            this.secondLevelCode = secondLevelCode;
        }

        public String getSecondLevelName() {
            return secondLevelName;
        }

        public void setSecondLevelName(String secondLevelName) {
            this.secondLevelName = secondLevelName;
        }

        public Integer getSecondLevelSort() {
            return secondLevelSort;
        }

        public void setSecondLevelSort(Integer secondLevelSort) {
            this.secondLevelSort = secondLevelSort;
        }

        public String getSecondLevelPicUrl() {
            return secondLevelPicUrl;
        }

        public void setSecondLevelPicUrl(String secondLevelPicUrl) {
            this.secondLevelPicUrl = secondLevelPicUrl;
        }

        public String getSecondLevelLinkUrl() {
            return secondLevelLinkUrl;
        }

        public void setSecondLevelLinkUrl(String secondLevelLinkUrl) {
            this.secondLevelLinkUrl = secondLevelLinkUrl;
        }
    }

}
