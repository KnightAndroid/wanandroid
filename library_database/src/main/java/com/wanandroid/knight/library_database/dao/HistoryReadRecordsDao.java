package com.wanandroid.knight.library_database.dao;

import com.wanandroid.knight.library_database.entity.HistoryReadRecordsEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/4 15:56
 * @descript:
 */


@Dao
public interface HistoryReadRecordsDao {

    //插入
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertHistoryReadRecords(HistoryReadRecordsEntity historyReadRecordsEntity);


    //查询所有历史记录
    @Query("select * from historyreadrecords_table")
    List<HistoryReadRecordsEntity> queryAllHistoryRecords();

    //删除某个
    @Query("delete from historyreadrecords_table where id=:id")
    void deleteHistoryRecordsById(long id);

    //删除全部
    @Query("delete from historyreadrecords_table")
    void deleteAllHistoryRecords();
}
