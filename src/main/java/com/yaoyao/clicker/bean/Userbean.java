package com.yaoyao.clicker.bean;

/**
 * Created by Administrator on 2017/8/23.
 */

public class Userbean {

    /**
     * resultcode : 200
     */

    private String resultcode;
    private ResultBean result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * aid : 3
         * uid : F9C7F5D3F6B944658682D324789CDDD6
         * name : 成都车展
         * phone : 18382414601
         * picurl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/49CA60C3-4B4B-4412-958A-1E6919951470.jpeg
         * focusCount : 2
         * focusedCount : 3
         * loveCount : 5
         * lovedCount : 0
         * sex : 0
         * hxid : 18382414601
         * udes :
         * friship : 0
         * focuship : 0
         * signSendLove : 0
         */

        private int aid;
        private String uid;
        private String name;
        private String phone;
        private String picurl;
        private int focusCount;
        private int focusedCount;
        private int loveCount;
        private int lovedCount;
        private int sex;
        private String hxid;
        private String udes;
        private String friship;
        private String focuship;
        private int signSendLove;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public int getFocusCount() {
            return focusCount;
        }

        public void setFocusCount(int focusCount) {
            this.focusCount = focusCount;
        }

        public int getFocusedCount() {
            return focusedCount;
        }

        public void setFocusedCount(int focusedCount) {
            this.focusedCount = focusedCount;
        }

        public int getLoveCount() {
            return loveCount;
        }

        public void setLoveCount(int loveCount) {
            this.loveCount = loveCount;
        }

        public int getLovedCount() {
            return lovedCount;
        }

        public void setLovedCount(int lovedCount) {
            this.lovedCount = lovedCount;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getHxid() {
            return hxid;
        }

        public void setHxid(String hxid) {
            this.hxid = hxid;
        }

        public String getUdes() {
            return udes;
        }

        public void setUdes(String udes) {
            this.udes = udes;
        }

        public String getFriship() {
            return friship;
        }

        public void setFriship(String friship) {
            this.friship = friship;
        }

        public String getFocuship() {
            return focuship;
        }

        public void setFocuship(String focuship) {
            this.focuship = focuship;
        }

        public int getSignSendLove() {
            return signSendLove;
        }

        public void setSignSendLove(int signSendLove) {
            this.signSendLove = signSendLove;
        }
    }
}
