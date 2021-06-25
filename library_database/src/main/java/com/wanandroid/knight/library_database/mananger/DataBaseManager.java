package com.wanandroid.knight.library_database.mananger;

import android.content.Context;

import com.wanandroid.knight.library_database.db.AppDataBase;
import com.wanandroid.knight.library_database.repository.EveryDayPushArticleRepository;
import com.wanandroid.knight.library_database.repository.HistoryReadRecordsRepository;
import com.wanandroid.knight.library_database.repository.HistroyKeywordsRepository;
import com.wanandroid.knight.library_database.repository.PushArticlesDateRepository;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/4 17:27
 * @descript:
 */
public final class DataBaseManager {


    /**
     *
     * 初始化数据库
     * @param context
     * @param dbName
     */
    public static void getDataBase(Context context, String dbName){
        AppDataBase.getDatabase(context,dbName);
        HistroyKeywordsRepository.getInstance().init();
        HistoryReadRecordsRepository.getInstance().init();
        EveryDayPushArticleRepository.getInstance().init();
        PushArticlesDateRepository.getInstance().init();
    }
}
