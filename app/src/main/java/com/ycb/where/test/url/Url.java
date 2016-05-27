package com.ycb.where.test.url;

/**
 * Created by 袁从斌-where on 2016/5/22.
 */
public class Url {
    public static int  SOCIAL_HOTSPOT_TYPE = 6;
    public static int  HOT_LIVELIHOOD_TYPE = 1;
    public static int  HOT_ENTERTAINMENT_TYPE = 2;
    public static int  FINANCE_HOTSPOT_TYPE = 3;
    public static int  HOT_SPORTS_TYPE = 4;
    public static int  HOT_EDUCATIONAL_TYPE = 5;
    public static int  BABYPAGER_TYPE = 6;
    public static int  PARENTING_TYPE = 8;
    public static int  CHILD_HEALTH_TYPE = 2;
    public static int  HEALTHY_DIET_TYPE = 3;
    public static int  MARITAL_AFFECTION_TYPE = 13;
    public static int  MEN_HEALTH_TYPE = 4;
    public static int  LOSE_WEIGHT_TYPE = 11;
    public static int  WOMEN_CARE_TYPE = 5;
    public static int  MEDICAL_CARE_TYPE = 12;
    public static int  ELDERLY_HEALTH_TYPE = 1;
    public static int  MENTAL_HEALTH_TYPE = 9;
    public static int  FOUR_SEASONS_HEALTH_TYPE = 10;
    public static int  PRIVATE_LIFE_TYPE = 7;
    public static int  FLIRTY_TYPE = 1;

    //社会热点
    public static String SOCIAL_HOTSPOT = "http://www.tngou.net/api/top/news?id=9974&classify=6";
    //民生热点
    public static String HOT_LIVELIHOOD="http://www.tngou.net/api/top/news?id=9912&classify=1";
    //娱乐热点
    public static String HOT_ENTERTAINMENT="http://www.tngou.net/api/top/news?id=9881&classify=2";
    //财经热点
    public static String FINANCE_HOTSPOT="http://www.tngou.net/api/top/news?id=7550&classify=3";
    //体育热点
    public static String HOT_SPORTS="http://www.tngou.net/api/top/news?id=7600&classify=4";
    //教育热点
    public static String HOT_EDUCATIONAL="http://www.tngou.net/api/top/news?id=8400&classify=5";


    //育婴手册
    public static final String BABYPAGER = "http://www.tngou.net/api/lore/news?id=18238&classify=6";
    //私密生活
    public static final String PRIVATE_LIFE = "http://www.tngou.net/api/lore/news?id=18727&classify=7";


    //育儿
    public static final String PARENTING = "http://www.tngou.net/api/lore/news?id=19110&classify=8";

    //孩子健康
    public static final String CHILD_HEALTH = "http://www.tngou.net/api/lore/news?id=19110&classify=2";

    //健康饮食
    public static final String HEALTHY_DIET = "http://www.tngou.net/api/lore/news?id=19330&classify=3";

    //夫妻情感
    public static final String MARITAL_AFFECTION = "http://www.tngou.net/api/lore/news?id=19010&classify=13";

    //男性健康
    public static final String MEN_HEALTH = "http://www.tngou.net/api/lore/news?id=19070&classify=4";

    //减肥
    public static final String LOSE_WEIGHT = "http://www.tngou.net/api/lore/news?id=19070&classify=11";
    //女性保养
    public static final String WOMEN_CARE = "http://www.tngou.net/api/lore/news?id=19400&classify=5";
    //医疗护理
    public static final String MEDICAL_CARE = "http://www.tngou.net/api/lore/news?id=19530&classify=12";
    //老人健康
    public static final String ELDERLY_HEALTH = "http://www.tngou.net/api/lore/news?id=19300&classify=1";
    //心理健康
    public static final String MENTAL_HEALTH = "http://www.tngou.net/api/lore/news?id=18500&classify=9";
    //四季养生
    public static final String FOUR_SEASONS_HEALTH = "http://www.tngou.net/api/lore/news?id=19000&classify=10";

    //健康详情页的地址
    public static final String HEALTH_KNOWLEDGE_DETAIL = "http://www.tngou.net/api/lore/show?id=";

    //性感美女
    public static final String FLIRTY="http://www.tngou.net/tnfs/api/news?id=649&classify=1";
    //单个图片的详情
    public static final String IMAGE_DETAIL="http://www.tngou.net/tnfs/api/show?id=";

    //图片链接
    public static final String IMG="http://tnfs.tngou.net/image";
    public static String getHealthKnowledgeDetail(long id) {
        String url = HEALTH_KNOWLEDGE_DETAIL + id;
        return url;
    }

    /**
     * 获取热点下拉刷新的地址
     * @return
     */
    public static String getRefreshUrl(long id,int type){
        String RefreshUrl="http://www.tngou.net/api/top/news?id=" + id +"&classify=" + type;
        return RefreshUrl;
    }

    /**
     * 获取热点加载更多url地址
     * @param id
     * @return
     */
    public static String getTopEventsLoadingMoreUrl(long id,int type){
        if (id==-1){
            return "";
        }
           String LoadingMoreUrl = "http://www.tngou.net/api/top/news?id=" + id + "&classify="+ type +"&rows=100";
            return LoadingMoreUrl;
    }

    /**
     * 获取健康知识下拉刷新的地址
     * @return
     */
    public static String getHealthKnowleRefreshUrl(long id,int type){
        String RefreshUrl="http://www.tngou.net/api/lore/news?id=" + id +"&classify=" + type;
        return RefreshUrl;
    }

    /**
     * 获取健康知识加载更多url地址
     * @param id
     * @param type
     * @return
     */
    public static String getHealthKnowleLoadingMoreUrl(long id,int type){
        if (id==-1){
            return "";
        }
        String LoadingMoreUrl = "http://www.tngou.net/api/lore/news?id=" + id + "&classify="+ type +"&rows=100";
        return LoadingMoreUrl;
    }

    /**
     * 获取图片下拉刷新的地址
     * @param id
     * @return
     */
    public static String getFlirtyRefreshUrl(long id,int type){
        if (id==-1){
            return "";
        }
        String LoadingMoreUrl = "http://www.tngou.net/tnfs/api/news?id=" + id + "&classify="+ type +"&rows=100";
        return LoadingMoreUrl;
    }

    /**
     * 获取图片加载更多url地址
     * @param id
     * @param type
     * @return
     */
    public static String getFlirtyLoadingMoreUrl(long id,int type){
        if (id==-1){
            return "";
        }
        String LoadingMoreUrl = "http://www.tngou.net/tnfs/api/news?id=" + id + "&classify="+ type +"&rows=100";
        return LoadingMoreUrl;
    }

}
