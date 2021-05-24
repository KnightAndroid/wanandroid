package com.knight.wanandroid.library_base;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/28 18:52
 * @descript:配置组件
 */
public class AppConfig {

    private static final String HomeApp = "com.knight.wanandroid.module_home.HomeApp";
    private static final String SquareApp = "com.knight.wanandroid.module_square.SquareApp";
    private static final String ProjectApp = "com.knight.wanandroid.module_project.ProjectApp";
    private static final String NavigateApp = "com.knight.wanandroid.module_hierachy.HierachyApp";
    private static final String MineApp = "com.knight.wanandroid.module_mine.MineApp";
    private static final String WebApp = "com.knight.wanandroid.module_web.WebApp";

    private static final String WechatApp = "com.knight.wanandroid.module_wechat.WechatApp";
    //文章只有文字
    public static final int ARTICLE_TEXT_TYPE = 0;
    //文章有文字和图片
    public static final int ARTICLE_PICTURE_TYPE = 1;
    //搜索字段
    public static String SEARCH_KEYWORD = "";
    //共享元素 图像
    public static final String IMAGE_TRANSITION_NAME = "transitionImage";


    public static String[] moduleApps = {
            HomeApp,
            SquareApp,
            ProjectApp,
            NavigateApp,
            MineApp,
            WebApp,
            WechatApp
    };

}
