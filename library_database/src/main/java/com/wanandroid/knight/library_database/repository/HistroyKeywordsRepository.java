package com.wanandroid.knight.library_database.repository;

import android.content.Context;

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




    public static HistroyKeywordsRepository getInstance(){
        if (instance == null) {
            instance = new HistroyKeywordsRepository();
        }
        return instance;
    }

    public void init(Context context, String dbName){
        this.context = context;
        this.dbName = dbName;
        mAppDataBase = AppDataBase.getDatabase(context,dbName);
        mHistroyKeywordDao = mAppDataBase.mHistroyKeywordDao();


    }

    //插入
    void insertHistroyKeyword(SearchHistroyKeywordEntity searchHistroyKeywordEntity){
        mAppDataBase.databaseWriteExecutor.execute(()->{
            mHistroyKeywordDao.insertHistroyKeyword(searchHistroyKeywordEntity);
        });

    }

    //查询
    void queryHistroyKeywords(){
        mAppDataBase.databaseWriteExecutor.execute(()->{
            mHistroyKeywordDao.queryAllHistroyWordkeys();
        });
    }

    //删除
    void deleteHistroyKeyword(long id){
       mAppDataBase.databaseWriteExecutor.execute(()->{
           mHistroyKeywordDao.deleteKetwordsById(id);
       });
    }




}
