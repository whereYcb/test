package com.ycb.where.test.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁从斌-where on 2016/5/24.
 */
public class FlirtyBean {
   public List<tngouDetail> tngou = new ArrayList<>();
    public class tngouDetail{
        public int count;
        public String img;
        public long id;
        public String title;

        @Override
        public String toString() {
            return "tngouDetail{" +
                    "title='" + title + '\'' +
                    ", count=" + count +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FlirtyBean{" +
                "tngou=" + tngou +
                '}';
    }
}
