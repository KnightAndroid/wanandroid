package com.wanandroid.knight.library_database.repository;

import android.os.Handler;
import android.os.Looper;

import com.wanandroid.knight.library_database.dao.HistoryReadRecordsDao;
import com.wanandroid.knight.library_database.db.AppDataBase;
import com.wanandroid.knight.library_database.entity.HistoryReadRecordsEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/4 17:33
 * @descript:
 */
public class HistoryReadRecordsRepository {
    private AppDataBase mAppDataBase;
    private static HistoryReadRecordsRepository instance;
    private HistoryReadRecordsDao mHistoryReadRecordsDao;
    private List<HistoryReadRecordsEntity> mHistoryReadRecordsEntities;

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 主线程回调接口
     */
    public interface OnQueryRecordsSuccessCallBack {

        /**
         * 查询成功回调方法
         */
        void onQueryRecordsSuccessCallBack(List<HistoryReadRecordsEntity> historyReadRecordsEntities);
    }


    public static HistoryReadRecordsRepository getInstance() {
        if (instance == null) {
            instance = new HistoryReadRecordsRepository();
        }
        return instance;
    }

    public void init() {
        mAppDataBase = AppDataBase.getInstance();
        mHistoryReadRecordsDao = mAppDataBase.mHistoryReadRecordsDao();

    }


    //插入
    public void insertHistroyRecordsKeyword(HistoryReadRecordsEntity historyReadRecordsEntity) {
        mHistoryReadRecordsDao.insertHistoryReadRecords(historyReadRecordsEntity);
    }

    //查询
    public void queryHistroyRecordsKeywords(OnQueryRecordsSuccessCallBack onQueryRecordsSuccessCallBack) {
        mAppDataBase.databaseWriteExecutor.execute(() -> {
            mHistoryReadRecordsEntities = mHistoryReadRecordsDao.queryAllHistoryRecords();
            HANDLER.post(() -> {
                onQueryRecordsSuccessCallBack.onQueryRecordsSuccessCallBack(mHistoryReadRecordsEntities);

            });
        });

    }

    //删除单个
    public void deleteHistroyRecord(long id) {
        mHistoryReadRecordsDao.deleteHistoryRecordsById(id);
    }

    //删除全部
    public void deleteAllHistroyRecord(){
        mHistoryReadRecordsDao.deleteAllHistoryRecords();

    }
}
