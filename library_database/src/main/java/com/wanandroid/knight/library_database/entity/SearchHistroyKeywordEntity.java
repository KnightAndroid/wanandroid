package com.wanandroid.knight.library_database.entity;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 14:18
 * @descript:实体
 */
@Entity(tableName = "searchhistroy_table",indices = {@Index(value = "name", unique = true)})
public class SearchHistroyKeywordEntity {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String name;

    @NonNull
    private Date insertTime;

    public  SearchHistroyKeywordEntity(String name){
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(@NonNull Date insertTime) {
        this.insertTime = insertTime;
    }
}
