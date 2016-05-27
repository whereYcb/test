package com.ycb.where.test.bean;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁从斌-where on 2016/5/25.
 */
public class ImageDetailBean {
    public List<detail> list = new ArrayList<>();
    public class detail{
            public long id;
            public String src;

        @Override
        public String toString() {
            return "list{" +
                    "id=" + id +
                    ", src='" + src + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ImageDetailBean{" +
                "ImageDetail=" + list +
                '}';
    }
}
