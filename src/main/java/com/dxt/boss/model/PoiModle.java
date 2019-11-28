package com.dxt.boss.model;

import java.util.List;

public class PoiModle {


    /**
     * status : 0
     * message : ok
     * total : 400
     * results : [{"name":"华为手机维修中心","location":{"lat":39.968029,"lng":116.4552},"address":"曙光里甲10号苏宁电器","province":"北京市","city":"北京市","area":"朝阳区","detail":1,"uid":"8a870592defea7bc2fb17eb3"},{"name":"OPPO官方(通州区梨园客服中心)","location":{"lat":39.889571,"lng":116.6741},"address":"北京市通州区云景东路苏宁易购西南50米","province":"北京市","city":"北京市","area":"通州区","street_id":"62b78ad020699fa1cb213d61","telephone":"(010)81520980","detail":1,"uid":"62b78ad020699fa1cb213d61"},{"name":"七彩虹手机维修中心","location":{"lat":39.912212,"lng":116.446532},"address":"北京市朝阳区建外门外大街乙24号","province":"北京市","city":"北京市","area":"朝阳区","street_id":"f7b2cb33a6a9122379dbd279","telephone":"13371790018","detail":1,"uid":"f7b2cb33a6a9122379dbd279"},{"name":"千机网手机维修加盟店(北京海淀花园路店)","location":{"lat":39.980131,"lng":116.371941},"address":"北京市海淀区花园路7号新时代大厦旁","province":"北京市","city":"北京市","area":"海淀区","street_id":"cb1db6e08050109585154148","telephone":"13391565656","detail":1,"uid":"cb1db6e08050109585154148"},{"name":"电脑手机维修(西客站店)","location":{"lat":39.905891,"lng":116.333878},"address":"北蜂窝中路乙8号","province":"北京市","city":"北京市","area":"海淀区","detail":1,"uid":"91f7d0df9b4b4e2615a1902b"},{"name":"电脑手机维修","location":{"lat":39.729174,"lng":116.167262},"address":"长虹东路","province":"北京市","city":"北京市","area":"房山区","detail":1,"uid":"9240ee0ec5f9eaa1c91b957b"},{"name":"电脑监控手机维修店","location":{"lat":39.774776,"lng":116.191415},"address":"北京市房山区合景岭峰8号院10号楼底商","province":"北京市","city":"北京市","area":"房山区","detail":1,"uid":"3a221829c1b06aaa5fd4fe42"},{"name":"天技手机维修","location":{"lat":39.989277,"lng":116.443067},"address":"文学馆路34号楼附近","province":"北京市","city":"北京市","area":"朝阳区","street_id":"9741512af9069d83f4e73ba3","detail":1,"uid":"9741512af9069d83f4e73ba3"},{"name":"千机网手机维修加盟店(北京科贸店)","location":{"lat":39.989197,"lng":116.323969},"address":"北京市海淀区中关村大街18号中关村科贸电子城2A017室","province":"北京市","city":"北京市","area":"海淀区","street_id":"e2294f4493e7e916c51b75dd","telephone":"13910923876","detail":1,"uid":"e2294f4493e7e916c51b75dd"},{"name":"OPPO官方(北京市西城区马甸客服中心)","location":{"lat":39.975042,"lng":116.391125},"address":"北三环中路国美西60米","province":"北京市","city":"北京市","area":"西城区","street_id":"80ae7c915a66c9b7a1046418","telephone":"(010)81520980,(010)81528910","detail":1,"uid":"80ae7c915a66c9b7a1046418"}]
     */

    private int status;
    private String message;
    private int total;
    private List<ResultsBean> results;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * name : 华为手机维修中心
         * location : {"lat":39.968029,"lng":116.4552}
         * address : 曙光里甲10号苏宁电器
         * province : 北京市
         * city : 北京市
         * area : 朝阳区
         * detail : 1
         * uid : 8a870592defea7bc2fb17eb3
         * street_id : 62b78ad020699fa1cb213d61
         * telephone : (010)81520980
         */

        private String name;
        private LocationBean location;
        private String address;
        private String province;
        private String city;
        private String area;
        private int detail;
        private String uid;
        private String street_id;
        private String telephone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getDetail() {
            return detail;
        }

        public void setDetail(int detail) {
            this.detail = detail;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStreet_id() {
            return street_id;
        }

        public void setStreet_id(String street_id) {
            this.street_id = street_id;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public static class LocationBean {
            /**
             * lat : 39.968029
             * lng : 116.4552
             */

            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }
    }
}
