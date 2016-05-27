package com.ycb.where.test.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 袁从斌-where on 2016/5/20.
 * 社会热点对象
 */
public class SocialHotspotBean {
    public List<tngouDetail> list = new ArrayList<>();
    public int total;
    public class  tngouDetail{
        //访问数
        public int count;
        //信息的描述或者简介
        public String description;
        //fromname 信息来源
        public String fromname;
        //fromurl详情
        public String fromurl;
        //热点的id
        public long id;
        //图片地址
        public String img;
        //新闻标题
        public String title;
        //发布时间
        public long time;

        @Override
        public String toString() {
            return "tngouDetail{" +
                    "fromname='" + fromname + '\'' +
                    ", description='" + description + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SocialHotspotBean{" +
                "list=" + list +
                '}';
    }
}
