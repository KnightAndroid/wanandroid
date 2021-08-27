package com.knight.wanandroid.library_base;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/28 18:52
 * @descript:配置组件
 */
public final class AppConfig {

    private static final String HomeApp = "com.knight.wanandroid.module_home.HomeApp";
    private static final String SquareApp = "com.knight.wanandroid.module_square.SquareApp";
    private static final String ProjectApp = "com.knight.wanandroid.module_project.ProjectApp";
    private static final String NavigateApp = "com.knight.wanandroid.module_hierachy.HierachyApp";
    private static final String MineApp = "com.knight.wanandroid.module_mine.MineApp";
    private static final String WebApp = "com.knight.wanandroid.module_web.WebApp";
    private static final String WechatApp = "com.knight.wanandroid.module_wechat.WechatApp";
    private static final String MessageApp = "com.knight.wanandroid.module_message.MessageApp";
    private static final String SetApp = "com.knight.wanandroid.module_set.SetApp";

    /**
     *
     * 类型：文字文章
     */
    public static final int ARTICLE_TEXT_TYPE = 0;
    /**
     *
     * 类型：图文文章
     */
    public static final int ARTICLE_PICTURE_TYPE = 1;
    /**
     *
     * 字段搜索名称
     */
    public static String SEARCH_KEYWORD = "";

    /**
     *
     * 共享元素图像
     */
    public static final String IMAGE_TRANSITION_NAME = "transitionImage";
    public static final String TEXT_AUTHOR_NAME = "authorName";
    public static final String TEXT_CHAPTERNAME_NAME = "chapterName";


    public static String[] moduleApps = {
            HomeApp,
            SquareApp,
            ProjectApp,
            NavigateApp,
            MineApp,
            WebApp,
            WechatApp,
            MessageApp,
            SetApp
    };

}
