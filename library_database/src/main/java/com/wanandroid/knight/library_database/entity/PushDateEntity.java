package com.wanandroid.knight.library_database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/24 10:07
 * @descript:
 */

@Entity(tableName = "pushdate_table")
public class PushDateEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }
}
