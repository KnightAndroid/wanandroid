package com.knight.wanandroid.library_base.view;

import android.content.Context;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/27 18:40
 * @descript:
 */
public class ScaleTransitionPagerTitleView extends ColorTransitionPagerTitleView {

    private float minScale = 0.8f;

    public ScaleTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);
        this.setScaleX(this.minScale + (1.0F - this.minScale) * enterPercent);
        this.setScaleY(this.minScale + (1.0F - this.minScale) * enterPercent);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);
        this.setScaleX(1.0F + (this.minScale - 1.0F) * leavePercent);
        this.setScaleY(1.0F + (this.minScale - 1.0F) * leavePercent);
    }


}
