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
        public static final String HomeTopArticle = Home + "/module_article_tabs";
        public static final String KnowLedgeLabel = Home + "/module_knowledge_label";
        public static final String AddKnowledgeLabel = Home + "/module_add_knowledge";
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
        public static final String HierachyDetail = Hierachy + "/hierachy_detail";
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
        public static final String OtherShareArticle_Pager = Mine + "/mine_otherarticle";
        public static final String About_Pager = Mine + "/mine_about";
        public static final String AppUpdate_Pager = Mine + "/mine_app_updaterecord";
        public static final String HistoryRecord_Pager = Mine + "/mine_historyrecord";
    }


    /**
     *
     * 公众号模块
     */
    public static class Wechat{
        private static final String Wechat = "/module_wechat_activity";
        public static final String Wechat_Pager = Wechat + "/magicIndicator_pager";
    }


    /**
     * 我的网页模块
     *
     *
     */
    public static class Web{
        private static final String Web = "/module_web_activity";

        public static final String Web_Pager = Web + "/web_pager";
        public static final String Web_Normal = Web + "/web_normal_pager";
        public static final String Web_Transition = Web + "/web_transition_pager";

    }

    /**
     *
     * 我的消息模块
     *
     */
    public static class Message{
        private static final String Message = "/module_message_activity";
        public static final String Message_pager = Message + "/message_pager";
    }









}
