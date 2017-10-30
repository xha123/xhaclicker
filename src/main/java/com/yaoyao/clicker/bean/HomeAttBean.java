package com.yaoyao.clicker.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class HomeAttBean {

    /**
     * resultcode : 200
    */

    private String resultcode;
    private List<ResultBean> result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         */

        private PicinfoBean picinfo;
        private CommentsBean comments;

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
             * hxid : osxit1iiwq8q5psm4cfaf4aj-dwu
             * uid : 4DD0051EC02E40D5866BFD940919AB20
             * lovedCount : 0
             * friship : 0
             * pid : 308
             * ctime :
             * size : 2811.0-2811.0
             * pdes : 🍾🥂🍷
             * loveCount : 4
             * purl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/FE7E5E11-3C9A-4E31-8A7D-8CFB43346124.jpeg
             * name : 61xuan
             * ppubtime : 2017-08-04 18:56:57
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

        public static class CommentsBean implements Serializable{
            /**
             * pn : 20
             * sn : 0
             * total : 1
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

            public static class ChildsBeanX implements Serializable{
                /**
                 * pComment : 哈哈哈哈哈每张都不像😂
                 * sn : 0
                 * pn : 20
                 * total : 4
                 * time : 2017-08-04 19:35:44
                 * uid : 964B4750F3714335AA6FB76884AA4881
                 * name : 3NX™
                 * picurl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/79644B79-C887-40E9-B223-BD61B69A07EC.jpeg
                 * groupid : 714A02882E404E78B0C1281B380CBDB3
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

                public static class ChildsBean implements Serializable{
                    /**
                     * id : 9B5E312FED6F4247BFBC417F97438BDF
                     * pid : 308
                     * uid : C2F25BFF0D944F84BEAB1B2E3CAE6546    回复人
                     * comment : 我觉得 很像啊 周冬雨
                     * time : 2017-08-04 19:37:06
                     * commentid : 964B4750F3714335AA6FB76884AA4881  被回复人
                     * groupid : 714A02882E404E78B0C1281B380CBDB3
                     * name : Brilliance
                     * picurl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/819A95AE-88B5-448C-B90D-AF36B6EC9269.jpeg
                     * cname : 3NX™
                     * cpicurl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/79644B79-C887-40E9-B223-BD61B69A07EC.jpeg
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
}
