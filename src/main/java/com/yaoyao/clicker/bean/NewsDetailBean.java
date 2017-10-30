package com.yaoyao.clicker.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class NewsDetailBean {


    /**
     * resultcode : 200
     * picinfo : {"hxid":"18349349523","uid":"476DBD6CAFB54A499BD96D0A84EFECC6","lovedCount":0,"friship":"1","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg","pid":322,"ctime":"2017-08-31 16:15:38","size":"580.0-966.0","pdes":"123","loveCount":2,"purl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/7F1498CC-99A2-470E-84FA-D881EC7B4634.jpeg","name":"你好大哥","ppubtime":"2017-08-09 16:37:14","repship":"0","focuship":"1"}
     * comments : {"pn":20,"sn":0,"total":2,"childs":[{"pComment":"好","sn":0,"pn":20,"total":2,"childs":[{"id":"DF70453855BD4A30A367D8A017C27092","pid":"322","uid":"F9C7F5D3F6B944658682D324789CDDD6","comment":"哈哈哈哈","time":"2017-09-01 17:55:13","commentid":"476DBD6CAFB54A499BD96D0A84EFECC6","groupid":"B4225E10D95849DC994894ADF4A1374E","name":"成都车展1","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/6053E7AB-DF8F-44D3-84A1-B00A19C2C04F.jpeg","cname":"你好大哥","cpicurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg"},{"id":"FE3A19FA9F784635B140E8E24C4C9DB9","pid":"322","uid":"F9C7F5D3F6B944658682D324789CDDD6","comment":"哈哈哈","time":"2017-09-01 17:55:32","commentid":"476DBD6CAFB54A499BD96D0A84EFECC6","groupid":"B4225E10D95849DC994894ADF4A1374E","name":"成都车展1","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/6053E7AB-DF8F-44D3-84A1-B00A19C2C04F.jpeg","cname":"你好大哥","cpicurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg"}],"time":"2017-08-17 17:26:11","uid":"476DBD6CAFB54A499BD96D0A84EFECC6","name":"你好大哥","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg","groupid":"B4225E10D95849DC994894ADF4A1374E"},{"pComment":"好喜欢哟\u2026\u2026","sn":0,"pn":20,"total":0,"time":"2017-08-15 17:04:37","uid":"476DBD6CAFB54A499BD96D0A84EFECC6","name":"你好大哥","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg","groupid":"57E0E6E1C07F4BDAB79C53AAF0F25D26"}]}
     */

    private String resultcode;
    private PicinfoBean picinfo;
    private CommentsBean comments;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public PicinfoBean getPicinfo() {
        return picinfo;
    }

    public void setPicinfo(PicinfoBean picinfo) {
        this.picinfo = picinfo;
    }

    public CommentsBean getComments() {
        return comments;
    }

    public void setComments(CommentsBean comments) {
        this.comments = comments;
    }

    public static class PicinfoBean {
        /**
         * hxid : 18349349523
         * uid : 476DBD6CAFB54A499BD96D0A84EFECC6
         * lovedCount : 0
         * friship : 1
         * picurl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg
         * pid : 322
         * ctime : 2017-08-31 16:15:38
         * size : 580.0-966.0
         * pdes : 123
         * loveCount : 2
         * purl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/7F1498CC-99A2-470E-84FA-D881EC7B4634.jpeg
         * name : 你好大哥
         * ppubtime : 2017-08-09 16:37:14
         * repship : 0
         * focuship : 1
         */

        private String hxid;
        private String uid;
        private int lovedCount;
        private String friship;
        private String picurl;
        private int pid;
        private String ctime;
        private String size;
        private String pdes;
        private int loveCount;
        private String purl;
        private String name;
        private String ppubtime;
        private String repship;
        private String focuship;

        public String getHxid() {
            return hxid;
        }

        public void setHxid(String hxid) {
            this.hxid = hxid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public int getLovedCount() {
            return lovedCount;
        }

        public void setLovedCount(int lovedCount) {
            this.lovedCount = lovedCount;
        }

        public String getFriship() {
            return friship;
        }

        public void setFriship(String friship) {
            this.friship = friship;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getPdes() {
            return pdes;
        }

        public void setPdes(String pdes) {
            this.pdes = pdes;
        }

        public int getLoveCount() {
            return loveCount;
        }

        public void setLoveCount(int loveCount) {
            this.loveCount = loveCount;
        }

        public String getPurl() {
            return purl;
        }

        public void setPurl(String purl) {
            this.purl = purl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPpubtime() {
            return ppubtime;
        }

        public void setPpubtime(String ppubtime) {
            this.ppubtime = ppubtime;
        }

        public String getRepship() {
            return repship;
        }

        public void setRepship(String repship) {
            this.repship = repship;
        }

        public String getFocuship() {
            return focuship;
        }

        public void setFocuship(String focuship) {
            this.focuship = focuship;
        }
    }

    public static class CommentsBean {
        /**
         * pn : 20
         * sn : 0
         * total : 2
         * childs : [{"pComment":"好","sn":0,"pn":20,"total":2,"childs":[{"id":"DF70453855BD4A30A367D8A017C27092","pid":"322","uid":"F9C7F5D3F6B944658682D324789CDDD6","comment":"哈哈哈哈","time":"2017-09-01 17:55:13","commentid":"476DBD6CAFB54A499BD96D0A84EFECC6","groupid":"B4225E10D95849DC994894ADF4A1374E","name":"成都车展1","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/6053E7AB-DF8F-44D3-84A1-B00A19C2C04F.jpeg","cname":"你好大哥","cpicurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg"},{"id":"FE3A19FA9F784635B140E8E24C4C9DB9","pid":"322","uid":"F9C7F5D3F6B944658682D324789CDDD6","comment":"哈哈哈","time":"2017-09-01 17:55:32","commentid":"476DBD6CAFB54A499BD96D0A84EFECC6","groupid":"B4225E10D95849DC994894ADF4A1374E","name":"成都车展1","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/6053E7AB-DF8F-44D3-84A1-B00A19C2C04F.jpeg","cname":"你好大哥","cpicurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg"}],"time":"2017-08-17 17:26:11","uid":"476DBD6CAFB54A499BD96D0A84EFECC6","name":"你好大哥","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg","groupid":"B4225E10D95849DC994894ADF4A1374E"},{"pComment":"好喜欢哟\u2026\u2026","sn":0,"pn":20,"total":0,"time":"2017-08-15 17:04:37","uid":"476DBD6CAFB54A499BD96D0A84EFECC6","name":"你好大哥","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg","groupid":"57E0E6E1C07F4BDAB79C53AAF0F25D26"}]
         */

        private int pn;
        private int sn;
        private int total;
        private List<ChildsBeanX> childs;

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ChildsBeanX> getChilds() {
            return childs;
        }

        public void setChilds(List<ChildsBeanX> childs) {
            this.childs = childs;
        }

        public static class ChildsBeanX {
            /**
             * pComment : 好
             * sn : 0
             * pn : 20
             * total : 2
             * childs : [{"id":"DF70453855BD4A30A367D8A017C27092","pid":"322","uid":"F9C7F5D3F6B944658682D324789CDDD6","comment":"哈哈哈哈","time":"2017-09-01 17:55:13","commentid":"476DBD6CAFB54A499BD96D0A84EFECC6","groupid":"B4225E10D95849DC994894ADF4A1374E","name":"成都车展1","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/6053E7AB-DF8F-44D3-84A1-B00A19C2C04F.jpeg","cname":"你好大哥","cpicurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg"},{"id":"FE3A19FA9F784635B140E8E24C4C9DB9","pid":"322","uid":"F9C7F5D3F6B944658682D324789CDDD6","comment":"哈哈哈","time":"2017-09-01 17:55:32","commentid":"476DBD6CAFB54A499BD96D0A84EFECC6","groupid":"B4225E10D95849DC994894ADF4A1374E","name":"成都车展1","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/6053E7AB-DF8F-44D3-84A1-B00A19C2C04F.jpeg","cname":"你好大哥","cpicurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg"}]
             * time : 2017-08-17 17:26:11
             * uid : 476DBD6CAFB54A499BD96D0A84EFECC6
             * name : 你好大哥
             * picurl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg
             * groupid : B4225E10D95849DC994894ADF4A1374E
             */

            private String pComment;
            private int sn;
            private int pn;
            private int total;
            private String time;
            private String uid;
            private String name;
            private String picurl;
            private String groupid;
            private List<ChildsBean> childs;

            public String getPComment() {
                return pComment;
            }

            public void setPComment(String pComment) {
                this.pComment = pComment;
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

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
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

            public String getPicurl() {
                return picurl;
            }

            public void setPicurl(String picurl) {
                this.picurl = picurl;
            }

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
            }

            public List<ChildsBean> getChilds() {
                return childs;
            }

            public void setChilds(List<ChildsBean> childs) {
                this.childs = childs;
            }

            public static class ChildsBean {
                /**
                 * id : DF70453855BD4A30A367D8A017C27092
                 * pid : 322
                 * uid : F9C7F5D3F6B944658682D324789CDDD6
                 * comment : 哈哈哈哈
                 * time : 2017-09-01 17:55:13
                 * commentid : 476DBD6CAFB54A499BD96D0A84EFECC6
                 * groupid : B4225E10D95849DC994894ADF4A1374E
                 * name : 成都车展1
                 * picurl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/6053E7AB-DF8F-44D3-84A1-B00A19C2C04F.jpeg
                 * cname : 你好大哥
                 * cpicurl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/BABB83F3-1E35-4423-A56E-82EFE267128C.jpeg
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
    }
}
