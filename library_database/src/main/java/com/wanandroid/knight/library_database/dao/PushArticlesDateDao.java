package com.wanandroid.knight.library_database.dao;

import com.wanandroid.knight.library_database.entity.PushDateEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/24 10:15
 * @descript:
 */

@Dao
public interface PushArticlesDateDao {

    //插入
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertPushArticlesDate(PushDateEntity pushDateEntity);

    //更新
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updatePushArticleDate(PushDateEntity... pushDateEntitys);

    //查询所有历史记录
    @Query("select * from pushdate_table")
    List<PushDateEntity> queryPushDateEntity();

    //根据时间去找有没有这条记录
    @Query("select * from pushdate_table WHERE time=:time")
    PushDateEntity findPushDate(String time);


}
