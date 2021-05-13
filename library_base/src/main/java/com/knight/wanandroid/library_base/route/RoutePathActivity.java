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
        public static final String ShareArticle = Square + "/square_share";
    }


    /**
     *
     * 体系模块
     */
    public static class Hierachy{
        private static final String Hierachy = "/module_hierachy_activity";
        public static final String HierachyTab = Hierachy + "/hierachy_tab";
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
        public static final String MyShareArticle_Pager = Mine + "/mine_sharearticle";
        public static final String MyCollectArticle_Pager = Mine + "/mine_collectarticle";

    }


    /**
     * 我的网页模块
     *
     *
     */
    public static class Web{
        private static final String Web = "/module_web_activity";

        public static final String Web_Pager = Web + "/web_pager";
    }









}
