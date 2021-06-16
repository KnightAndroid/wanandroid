package com.wanandroid.knight.library_database.repository;

import android.os.Handler;
import android.os.Looper;

import com.wanandroid.knight.library_database.dao.EveryDayPushArticleDao;
import com.wanandroid.knight.library_database.db.AppDataBase;
import com.wanandroid.knight.library_database.entity.EveryDayPushEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/16 14:44
 * @descript:
 */
public class EveryDayPushArticleRepository {

    private AppDataBase mAppDataBase;
    private static EveryDayPushArticleRepository instance;
    private EveryDayPushArticleDao mEveryDayPushArticleDao;

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());


    public static EveryDayPushArticleRepository getInstance() {
        if (instance == null) {
            instance = new EveryDayPushArticleRepository();
        }
        return instance;
    }

    public void init() {
        mAppDataBase = AppDataBase.getInstance();
        mEveryDayPushArticleDao = mAppDataBase.mEveryDayPushArticleDao();

    }

    public interface OnQueryEveryDayArticleCallBack{
        void onFindEveryDayArticle(List<EveryDayPushEntity> everyDayPushEntitys);
    }

    //插入
    public void insertEveryDayPushArticle(EveryDayPushEntity everyDayPushEntity) {
        mEveryDayPushArticleDao.insertEveryDayPushArticle(everyDayPushEntity);
    }

    //查询某一条
    public void findHistoryReadRecords(OnQueryEveryDayArticleCallBack onQueryEveryDayArticleCallBack){
        mAppDataBase.databaseWriteExecutor.execute(()->{
            HANDLER.post(()->{
                onQueryEveryDayArticleCallBack.onFindEveryDayArticle(mEveryDayPushArticleDao.queryEveryDayPush());
            });
        });
    }

    //更新
    public int updateEveryDayPushArticle(EveryDayPushEntity...everyDayPushEntities){
        return mEveryDayPushArticleDao.updateEveryDayPushArticle(everyDayPushEntities);
    }
    
}
