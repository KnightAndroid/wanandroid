package com.wanandroid.knight.library_database.db;

import android.content.Context;

import com.wanandroid.knight.library_database.converter.DateConverter;
import com.wanandroid.knight.library_database.dao.EveryDayPushArticleDao;
import com.wanandroid.knight.library_database.dao.HistoryReadRecordsDao;
import com.wanandroid.knight.library_database.dao.SearchHistroyKeywordDao;
import com.wanandroid.knight.library_database.entity.EveryDayPushEntity;
import com.wanandroid.knight.library_database.entity.HistoryReadRecordsEntity;
import com.wanandroid.knight.library_database.entity.SearchHistroyKeywordEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 14:35
 * @descript:database
 */
@TypeConverters(value = {DateConverter.class})
@Database(entities = {SearchHistroyKeywordEntity.class, HistoryReadRecordsEntity.class, EveryDayPushEntity.class},version = 2,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract SearchHistroyKeywordDao mHistroyKeywordDao();

    public abstract HistoryReadRecordsDao mHistoryReadRecordsDao();

    public abstract EveryDayPushArticleDao mEveryDayPushArticleDao();

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
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }

        }
        return INSTANCE;

    }


    public static AppDataBase getInstance(){
        return INSTANCE;
    }


    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `historyreadrecords_table` (`id` INTEGER NOT NULL PRIMARY KEY autoincrement , `userId` INTEGER NOT NULL, " +
                            "`isCollect` INTEGER NOT NULL, `webUrl` TEXT NOT NULL ,`articleId` NOT NULL,`title` TEXT NOT NULL,`envelopePic` TEXT," +
                            "`author` TEXT,`chapterName` TEXT,`articledesc` TEXT,'insertTime' INTEGER NOT NULL)");
            database.execSQL("CREATE UNIQUE INDEX `index_historyreadrecords_table_articleId` On `historyreadrecords_table`(`articleId`)");
            database.execSQL("CREATE UNIQUE INDEX `index_historyreadrecords_table_webUrl` On `historyreadrecords_table`(`webUrl`)");


            database.execSQL("CREATE TABLE IF NOT EXISTS `everydaypush_table` (`id` INTEGER NOT NULL PRIMARY KEY autoincrement,`articlePicture` TEXT," +
                    "`articleLink` TEXT NOT NULL,`time` TEXT NOT NULL,`author` TEXT NOT NULL,`articledesc` TEXT,`articleTitle` TEXT,`popupTitle` TEXT,`pushStatus` Boolean)");
        }
    };


}
