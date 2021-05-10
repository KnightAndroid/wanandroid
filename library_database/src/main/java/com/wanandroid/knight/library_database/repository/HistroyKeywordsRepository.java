package com.wanandroid.knight.library_database.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.wanandroid.knight.library_database.dao.SearchHistroyKeywordDao;
import com.wanandroid.knight.library_database.db.AppDataBase;
import com.wanandroid.knight.library_database.entity.SearchHistroyKeywordEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 14:47
 * @descript:
 */
public class HistroyKeywordsRepository {

    private AppDataBase mAppDataBase;
    private static HistroyKeywordsRepository instance;


    private SearchHistroyKeywordDao mHistroyKeywordDao;
    private List<SearchHistroyKeywordEntity> mSearchHistroyKeywordEntities;

    private Context context;
    private String dbName;

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 主线程回调接口
     */
    public interface OnQuerySuccessCallBack {

        /**
         * 查询成功回调方法
         */
        void onQuerySuccessCallBack(List<SearchHistroyKeywordEntity> mSearchHistroyKeywordEntities);


    }


    public static HistroyKeywordsRepository getInstance() {
        if (instance == null) {
            instance = new HistroyKeywordsRepository();
        }
        return instance;
    }

    public void init(Context context, String dbName) {
        this.context = context;
        this.dbName = dbName;
        mAppDataBase = AppDataBase.getDatabase(context, dbName);
        mHistroyKeywordDao = mAppDataBase.mHistroyKeywordDao();
    }

    //插入
    public void insertHistroyKeyword(SearchHistroyKeywordEntity searchHistroyKeywordEntity) {
        mHistroyKeywordDao.insertHistroyKeyword(searchHistroyKeywordEntity);
    }

    //查询
    public void queryHistroyKeywords(OnQuerySuccessCallBack onQuerySuccessCallBack) {
        mAppDataBase.databaseWriteExecutor.execute(() -> {
            mSearchHistroyKeywordEntities = mHistroyKeywordDao.queryAllHistroyWordkeys();
            HANDLER.post(() -> {
                onQuerySuccessCallBack.onQuerySuccessCallBack(mSearchHistroyKeywordEntities);
            });
        });

    }

    //删除单个
    public void deleteHistroyKeyword(long id) {
        mHistroyKeywordDao.deleteKetwordsById(id);
    }

    //删除全部
    public void deleteAllKeywords(){
        mHistroyKeywordDao.deleteAllKeywords();

    }


}
