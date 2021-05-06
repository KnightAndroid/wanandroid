package com.knight.wanandroid.library_base.route;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 17:25
 * @descript:activity路由
 */
public class RoutePathActivity {


    /**
     *
     * Home模块Activity路由
     *
     * */
    public static class Home {
        private static final String Home = "/module_home_activity";

        public static final String search = Home + "/module_search_activity";
        public static final String searchResult = Home + "/module_search_result";
    }


    /**
     *
     * 广场模块Activity路由
     */
    public static class Square{
        private static final String Square = "/module_square_activity";
    }


    /**
     *
     * 我的模块Activity路由
     */
    public static class Mine{
        private static final String Mine = "/module_mine_activity";
        public static final String Login_Pager = Mine + "/mine_login";
        public static final String Register_Pager = Mine + "/mine_register";
        public static final String UserCoin_pager = Mine + "/mine_usercoin";
        public static final String UserCoinRank_Pager = Mine + "/mine_coinrank";

    }












}
