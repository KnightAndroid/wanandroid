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


    public static String[] moduleApps = {
            HomeApp,
            SquareApp,
            ProjectApp,
            NavigateApp,
            MineApp
    };

}
