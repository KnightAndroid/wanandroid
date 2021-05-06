package com.wanandroid.knight.library_database.dao;

import com.wanandroid.knight.library_database.entity.SearchHistroyKeywordEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 14:17
 * @descript:搜索记录关键字Dao层
 */
@Dao
public interface SearchHistroyKeywordDao {


    //插入
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertHistroyKeyword(SearchHistroyKeywordEntity searchHistroyKeywordEntity);


    //查询全部
    @Query("select * from searchhistroy_table")
    List<SearchHistroyKeywordEntity> queryAllHistroyWordkeys();

    //删除某个
    @Query("delete from searchhistroy_table where id=:id")
    void deleteKetwordsById(long id);

    //删除全部
    @Query("delete from searchhistroy_table")
    void deleteAllKeywords();

}
