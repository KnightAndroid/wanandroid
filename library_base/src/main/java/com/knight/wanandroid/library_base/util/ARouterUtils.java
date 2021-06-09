package com.knight.wanandroid.library_base.util;

import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.route.RoutePathActivity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/10 19:13
 * @descript:
 */
public final class ARouterUtils {


    /**
     *
     * 跳转到webActivity
     * @param webUrl
     */
    public static void startWeb(String webUrl,String title,int articleId,boolean isCollect,String envelopePic,String articledesc,String chapterName,String author){
        ARouter.getInstance().build(RoutePathActivity.Web.Web_Pager)
                .withString("webUrl",webUrl)
                .withString("title",title)
                .withInt("articleId",articleId)
                .withBoolean("isCollect",isCollect)
                .withString("envelopePic",envelopePic)
                .withString("articledesc",articledesc)
                .withString("chapterName",chapterName)
                .withString("author",author)
                .navigation();
    }


    /**
     *
     * 带参数跳转到目标Activity
     * @param activity
     */
    public static void startActivity(String activity,String parameterKey,String parameter){
        ARouter.getInstance().build(activity).
                withString(parameterKey,parameter).navigation();
    }



    /**
     *
     * 带参数跳转到目标Activity
     * @param activity
     */
    public static void startActivity(String activity){
        ARouter.getInstance().build(activity).navigation();
    }

}
