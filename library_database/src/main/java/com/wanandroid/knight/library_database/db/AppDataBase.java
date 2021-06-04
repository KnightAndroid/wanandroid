package com.wanandroid.knight.library_database.db;

import android.content.Context;

import com.wanandroid.knight.library_database.converter.DateConverter;
import com.wanandroid.knight.library_database.dao.HistoryReadRecordsDao;
import com.wanandroid.knight.library_database.dao.SearchHistroyKeywordDao;
import com.wanandroid.knight.library_database.entity.HistoryReadRecordsEntity;
import com.wanandroid.knight.library_database.entity.SearchHistroyKeywordEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 14:35
 * @descript:database
 */
@TypeConverters(value = {DateConverter.class})
@Database(entities = {SearchHistroyKeywordEntity.class, HistoryReadRecordsEntity.class},version = 2,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract SearchHistroyKeywordDao mHistroyKeywordDao();

    public abstract HistoryReadRecordsDao mHistoryReadRecordsDao();

    private static volatile AppDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDataBase getDatabase(final Context context,String dbName){
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class, dbName)
                            .allowMainThreadQueries()
                            .enableMultiInstanceInvalidation()
                            .build();
                }
            }

        }
        return INSTANCE;

    }


    public static AppDataBase getInstance(){
        return INSTANCE;
    }




}
