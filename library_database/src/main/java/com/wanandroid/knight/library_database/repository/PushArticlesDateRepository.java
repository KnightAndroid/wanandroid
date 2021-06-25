package com.wanandroid.knight.library_database.repository;

import android.os.Handler;
import android.os.Looper;

import com.wanandroid.knight.library_database.dao.PushArticlesDateDao;
import com.wanandroid.knight.library_database.db.AppDataBase;
import com.wanandroid.knight.library_database.entity.PushDateEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/24 10:28
 * @descript:
 */
public class PushArticlesDateRepository {


    private AppDataBase mAppDataBase;
    private static PushArticlesDateRepository instance;
    private PushArticlesDateDao mPushArticlesDateDao;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());


    public static PushArticlesDateRepository getInstance() {
        if (instance == null) {
             instance = new PushArticlesDateRepository();
        }
        return instance;
    }

    public void init() {
        mAppDataBase = AppDataBase.getInstance();
        mPushArticlesDateDao = mAppDataBase.mPushArticlesDateDao();
    }

    public interface OnQueryPushArticlesDateCallBack{
        void onFindPushArticlesDate(List<PushDateEntity> pushDateEntities);
    }



    //查询某一条
    public void findPushArticlesDate(OnQueryPushArticlesDateCallBack onQueryEveryDayArticleCallBack){
        mAppDataBase.databaseWriteExecutor.execute(()->{
            HANDLER.post(()->{
                onQueryEveryDayArticleCallBack.onFindPushArticlesDate(mPushArticlesDateDao.queryPushDateEntity());
            });
        });
    }


    //插入
    public void insertPushArticlesDate(PushDateEntity pushDateEntity){
        mPushArticlesDateDao.insertPushArticlesDate(pushDateEntity);
    }


    //更新
    public int updatePushArticlesDate(PushDateEntity...pushDateEntity){
        return mPushArticlesDateDao.updatePushArticleDate(pushDateEntity);
    }


}
