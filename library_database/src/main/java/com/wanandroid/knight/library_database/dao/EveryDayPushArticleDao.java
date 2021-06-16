package com.wanandroid.knight.library_database.dao;

import com.wanandroid.knight.library_database.entity.EveryDayPushEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/16 14:34
 * @descript:
 */

@Dao
public interface EveryDayPushArticleDao {

    //插入
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertEveryDayPushArticle(EveryDayPushEntity everyDayPushEntity);

    //更新
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateEveryDayPushArticle(EveryDayPushEntity... everyDayPushEntities);

    //查询所有历史记录
    @Query("select * from everydaypush_table")
    List<EveryDayPushEntity> queryEveryDayPush();

    //根据文章查找有没有这条记录
    @Query("select * from everydaypush_table WHERE articleLink=:articleLink")
    EveryDayPushEntity findEveryDayPushArticle(String articleLink);

}
