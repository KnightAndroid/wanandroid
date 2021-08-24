package com.knight.wanandroid.module_hierachy.module_entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 16:45
 * @descript:体系 导航 右边数据
 */
public final class HierachyRightBeanEntity implements Parcelable {
    private String name;
    private String titleName;
    private String tag;
    private boolean isTitle;
    private String link;
    //跳转到webview要用
    private int id;

    //父标签名字
    private String parentName;

    private int total;
    private int position;
    private ArrayList<String> childrenName;
    private ArrayList<Integer> cid;


    //文章是否被收藏
    private boolean collect;
    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitleName() {
        return titleName == null ? "" : titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTag() {
        return tag == null ? "" : tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getLink() {
        return link == null ? "" : link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParentName() {
        return parentName == null ? "" : parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<String> getChildrenName() {
        if (childrenName == null) {
            return new ArrayList<>();
        }
        return childrenName;
    }

    public void setChildrenName(ArrayList<String> childrenName) {
        this.childrenName = childrenName;
    }

    public ArrayList<Integer> getCid() {
        if (cid == null) {
            return new ArrayList<>();
        }
        return cid;
    }

    public void setCid(ArrayList<Integer> cid) {
        this.cid = cid;
    }

    public static Creator<HierachyRightBeanEntity> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.titleName);
        dest.writeString(this.tag);
        dest.writeByte(this.isTitle ? (byte) 1 : (byte) 0);
        dest.writeString(this.link);
        dest.writeInt(this.id);
        dest.writeString(this.parentName);
        dest.writeInt(this.total);
        dest.writeInt(this.position);
        dest.writeStringList(this.childrenName);
        dest.writeList(this.cid);
    }

    public HierachyRightBeanEntity() {
    }

    protected HierachyRightBeanEntity(Parcel in) {
        this.name = in.readString();
        this.titleName = in.readString();
        this.tag = in.readString();
        this.isTitle = in.readByte() != 0;
        this.link = in.readString();
        this.id = in.readInt();
        this.parentName = in.readString();
        this.total = in.readInt();
        this.position = in.readInt();
        this.childrenName = in.createStringArrayList();
        this.cid = new ArrayList<Integer>();
        in.readList(this.cid, Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<HierachyRightBeanEntity> CREATOR = new Parcelable.Creator<HierachyRightBeanEntity>() {
        @Override
        public HierachyRightBeanEntity createFromParcel(Parcel source) {
            return new HierachyRightBeanEntity(source);
        }

        @Override
        public HierachyRightBeanEntity[] newArray(int size) {
            return new HierachyRightBeanEntity[size];
        }
    };
}
