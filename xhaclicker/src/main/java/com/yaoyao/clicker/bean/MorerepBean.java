package com.yaoyao.clicker.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class MorerepBean {


    /**
     * resultcode : 200
     * total : 4
     * sn : 0
     * pn : 21
     */

    private String resultcode;
    private int total;
    private int sn;
    private int pn;
    private List<ResultBean> result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 9FCA950126EE4C8A98F2E87FC7D71505
         * pid : 55
         * uid : 9D1053237B44491FA3D8F0F7D75B38B0
         * comment : 你这个人撒子��皮思想
         * time : 2017-06-20 11:19:11
         * commentid : C2F25BFF0D944F84BEAB1B2E3CAE6546
         * groupid : 14A4C080B5CD4DEFBC496EB03F14E4D7
         * name : Question
         * picurl : http://wx.qlogo.cn/mmopen/LZC8o5afNN05ztENsMicz7SC4K8tunicsyqzsp3HuNF53Ip6ibloqxa98bBe6hRqvmw4kbG7FzGMUndLWzEmECvgB6Xial7TzoLY/0
         * cname : Brilliance
         * cpicurl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/819A95AE-88B5-448C-B90D-AF36B6EC9269.jpeg
         */

        private String id;
        private String pid;
        private String uid;
        private String comment;
        private String time;
        private String commentid;
        private String groupid;
        private String name;
        private String picurl;
        private String cname;
        private String cpicurl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCommentid() {
            return commentid;
        }

        public void setCommentid(String commentid) {
            this.commentid = commentid;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCpicurl() {
            return cpicurl;
        }

        public void setCpicurl(String cpicurl) {
            this.cpicurl = cpicurl;
        }
    }
}
