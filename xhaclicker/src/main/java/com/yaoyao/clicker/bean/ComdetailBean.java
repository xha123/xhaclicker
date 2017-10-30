package com.yaoyao.clicker.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */

public class ComdetailBean {


    /**
     * resultcode : 200
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
         * hxid : osxit1ky74i-qippl1pqjd9whh_0
         * uid : 9D1053237B44491FA3D8F0F7D75B38B0
         * lovedCount : 0
         * friship : 0
         * pid : 106
         * ctime :
         * size : 3264.0-2448.0
         * pdes : 很久没有出去社交了，还是可以
         * loveCount : 1
         * purl : http://clicker1.oss-cn-shenzhen.aliyuncs.com/FF8BFCDB-DE89-451D-8E19-5D1D84E5D120.jpeg
         * name : Question
         * ppubtime : 2017-06-29 02:13:32
         * repship : 0
         * focuship : 0
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
             * pComment : 为啥子不好生表现
             * sn : 0
             * pn : 20
             * time : 2017-07-04 17:06:02
             * uid : 9E36D899BDB6442E8AC7CF03D772D1E9
             * name : 王哥
             * groupid : 3C9DAD5CA1EB405DAD235DE4BE4E976C
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
                 * id : C072CB4322BE4AF7A4A0BDAEF196C938
                 * pid : 106
                 * uid : 9D1053237B44491FA3D8F0F7D75B38B0
                 * comment : ？？
                 * time : 2017-07-04 17:06:40
                 * commentid : 9E36D899BDB6442E8AC7CF03D772D1E9
                 * groupid : 3C9DAD5CA1EB405DAD235DE4BE4E976C
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
