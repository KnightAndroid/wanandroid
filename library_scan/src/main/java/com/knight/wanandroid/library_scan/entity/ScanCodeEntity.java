package com.knight.wanandroid.library_scan.entity;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

/**
 * @author apple
 */
public class ScanCodeEntity implements Parcelable {

    public Activity mActivity;
    public Fragment mFragment;
    private int style;
    private int scanMode;
    private boolean isPlayAudio;
    private int audioId;
    private boolean showFrame;
    private ScanRectEntity scanRect;
    private int scanSize;
    private int offsetX;
    private int offsetY;
    private boolean usePx;

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getScanMode() {
        return scanMode;
    }

    public void setScanMode(int scanMode) {
        this.scanMode = scanMode;
    }

    public boolean isPlayAudio() {
        return isPlayAudio;
    }

    public void setPlayAudio(boolean playAudio) {
        isPlayAudio = playAudio;
    }

    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    public boolean isShowFrame() {
        return showFrame;
    }

    public void setShowFrame(boolean showFrame) {
        this.showFrame = showFrame;
    }

    public ScanRectEntity getScanRect() {
        return scanRect;
    }

    public void setScanRect(ScanRectEntity scanRect) {
        this.scanRect = scanRect;
    }

    public int getScanSize() {
        return scanSize;
    }

    public void setScanSize(int scanSize) {
        this.scanSize = scanSize;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public boolean isUsePx() {
        return usePx;
    }

    public void setUsePx(boolean usePx) {
        this.usePx = usePx;
    }

    public long getScanDuration() {
        return scanDuration;
    }

    public void setScanDuration(long scanDuration) {
        this.scanDuration = scanDuration;
    }

    public int getFrameColor() {
        return frameColor;
    }

    public void setFrameColor(int frameColor) {
        this.frameColor = frameColor;
    }

    public boolean isShowShadow() {
        return showShadow;
    }

    public void setShowShadow(boolean showShadow) {
        this.showShadow = showShadow;
    }

    public int getShaowColor() {
        return shaowColor;
    }

    public void setShaowColor(int shaowColor) {
        this.shaowColor = shaowColor;
    }

    public int getScanBitmapId() {
        return scanBitmapId;
    }

    public void setScanBitmapId(int scanBitmapId) {
        this.scanBitmapId = scanBitmapId;
    }

    public int getFrameWith() {
        return frameWith;
    }

    public void setFrameWith(int frameWith) {
        this.frameWith = frameWith;
    }

    public int getFrameLenth() {
        return frameLenth;
    }

    public void setFrameLenth(int frameLenth) {
        this.frameLenth = frameLenth;
    }

    public int getFrameRaduis() {
        return frameRaduis;
    }

    public void setFrameRaduis(int frameRaduis) {
        this.frameRaduis = frameRaduis;
    }

    private long scanDuration;
    private int frameColor;
    private boolean showShadow;
    private int shaowColor;
    private int scanBitmapId;
    private int frameWith;
    private int frameLenth;
    private int frameRaduis;

    public ScanCodeEntity(Activity mActivity){
        this.mActivity = mActivity;
    }


    public ScanCodeEntity(Fragment mFragment){
        this.mFragment = mFragment;
    }


    public ScanCodeEntity(Activity mActivity, Fragment mFragment){
        this.mActivity = mActivity;
        this.mFragment = mFragment;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.style);
        dest.writeInt(this.scanMode);
        dest.writeByte(this.isPlayAudio ? (byte) 1 : (byte) 0);
        dest.writeInt(this.audioId);
        dest.writeByte(this.showFrame ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.scanRect);
        dest.writeInt(this.scanSize);
        dest.writeInt(this.offsetX);
        dest.writeInt(this.offsetY);
        dest.writeByte(this.usePx ? (byte) 1 : (byte) 0);
        dest.writeLong(this.scanDuration);
        dest.writeInt(this.frameColor);
        dest.writeByte(this.showShadow ? (byte) 1 : (byte) 0);
        dest.writeInt(this.shaowColor);
        dest.writeInt(this.scanBitmapId);
        dest.writeInt(this.frameWith);
        dest.writeInt(this.frameLenth);
        dest.writeInt(this.frameRaduis);
    }

    protected ScanCodeEntity(Parcel in) {
        this.style = in.readInt();
        this.scanMode = in.readInt();
        this.isPlayAudio = in.readByte() != 0;
        this.audioId = in.readInt();
        this.showFrame = in.readByte() != 0;
        this.scanRect = (ScanRectEntity) in.readSerializable();
        this.scanSize = in.readInt();
        this.offsetX = in.readInt();
        this.offsetY = in.readInt();
        this.usePx = in.readByte() != 0;
        this.scanDuration = in.readLong();
        this.frameColor = in.readInt();
        this.showShadow = in.readByte() != 0;
        this.shaowColor = in.readInt();
        this.scanBitmapId = in.readInt();
        this.frameWith = in.readInt();
        this.frameLenth = in.readInt();
        this.frameRaduis = in.readInt();
    }

    public static final Parcelable.Creator<ScanCodeEntity> CREATOR = new Parcelable.Creator<ScanCodeEntity>() {
        @Override
        public ScanCodeEntity createFromParcel(Parcel source) {
            return new ScanCodeEntity(source);
        }

        @Override
        public ScanCodeEntity[] newArray(int size) {
            return new ScanCodeEntity[size];
        }
    };
}
