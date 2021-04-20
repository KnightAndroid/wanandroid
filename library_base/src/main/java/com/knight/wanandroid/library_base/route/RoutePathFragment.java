package com.knight.wanandroid.library_base.route;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 17:33
 * @descript:
 */
public class RoutePathFragment {

    /**
     *
     * 首页模块Fragment
     *
     */
    public static class Home {
        private static final String Home = "/module_home_fragment";

        public static final String Home_Pager = Home + "/home_main";
    }

    /**
     *
     * 广场模块
     */
    public static class Square {
        private static final String Square = "/module_square_fragment";

        public static final String Square_Pager = Square + "/square_main";

    }

    /**
     *
     * 我的页面
     * 
     */
    public static class Mine {
        private static final String Mine = "/module_mine_fragment";

        public static final String Mine_Pager = Mine + "/mine_main";

    }


}
