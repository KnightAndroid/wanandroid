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
     * 项目模块
     */
    public static class Project {
        private static final String Project = "/module_project_fragment";
        public static final String Project_Pager = Project + "/project_main";
        public static final String Project_Child_Pager = Project + "/project_child_pager";
    }

    /**
     *
     * 体系模块
     */
    public static class Hierachy {
        private static final String Hierachy = "/module_hierachy_fragment";
        public static final String HierachyNavigate = Hierachy + "/hierachy_navigate";
        public static final String Hierachy_Pager = Hierachy + "/hierachy_main";
        public static final String Hierachy_Right = Hierachy + "/hierachy_right";
        public static final String Navigate_pager = Hierachy + "/navigate";
        public static final String Hierachy_TabArticle_Pager = Hierachy + "/hierachy_tab_article";

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


    /**
     *
     * 消息模块
     */
    public static class Message {
        private static final String Message = "/module_message_fragment";

        public static final String Readed_Message = Message + "/readed_message";
    }


}
