package com.knight.wanandroid.library_util;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/20 16:58
 * @descript:Eventbus 工具
 */
public class EventBusUtils {

    //登录成功
    public static class LoginInSuccess{

    }

    //退出登录成功
    public static class LogoutSuccess{

    }

    //分享文章成功
    public static class ShareArticleSuccess{

    }

    //webActivity收藏成功
    public static class CollectSuccess{

    }

    //消息阅读
    public static class ReadAllMessage{

    }

    //标签更改
    public static class ChangeLabel{
        private List<String> lists;
        public ChangeLabel (List<String> lists){
            this.lists = lists;
        }

        public List<String> getResults(){
            return  lists;
        }

    }

}
