package com.wanandroid.knight.library_database.dao;

import com.wanandroid.knight.library_database.entity.HistoryReadRecordsEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    //更新
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateHistoryReadRecord(HistoryReadRecordsEntity... historyReadRecordsEntities);

    //查询所有历史记录
    @Query("select * from historyreadrecords_table")
    List<HistoryReadRecordsEntity> queryAllHistoryRecords();

    //删除某个
    @Query("delete from historyreadrecords_table where id=:id")
    void deleteHistoryRecordsById(long id);

    //删除全部
    @Query("delete from historyreadrecords_table")
    void deleteAllHistoryRecords();

    //根据用户Id查询部分
    @Query("select id,webUrl,articleId,title,envelopePic,insertTime,author,chapterName,articledesc,userId,isCollect FROM historyreadrecords_table  Where userId =:userId order by insertTime desc limit :start,:end ")
    List<HistoryReadRecordsEntity> queryPartHistoryRecords(int start,int end,int userId);

    //根据用户指定查询
    @Query("select * from historyreadrecords_table WHERE userId=:userId AND webUrl=:webUrl AND articleId=:articleId")
    HistoryReadRecordsEntity findHistoryReadRecords(String webUrl,int articleId,int userId);



}
