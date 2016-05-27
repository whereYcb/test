package com.ycb.where.test.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁从斌-where on 2016/5/23.
 * 健康知识的bean
 */
public class HealthKnowledgeBean {
    public List<HealthKnowledgeDetail> list = new ArrayList<HealthKnowledgeDetail>();
    public class HealthKnowledgeDetail{

        public String description;
        public long id ;
        public String img;
        public long time;

        @Override
        public String toString() {
            return "HealthKnowledgeDetail{" +
                    "description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HealthKnowledgeBean{" +
                "list=" + list +
                '}';
    }
}
