package com.knight.wanandroid.module_mine.dialog;

import android.os.Build;
import android.text.Html;
import android.view.Gravity;

import com.knight.wanandroid.library_base.basefragment.BaseDBDialogFragment;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MinePointRuleDialogBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/10/19 16:33
 * @descript:
 */
public final class PointRuleDialog extends BaseDBDialogFragment<MinePointRuleDialogBinding> {


    public PointRuleDialog(){

    }





    public static PointRuleDialog newInstance(){
        PointRuleDialog pointRuleDialog = new PointRuleDialog();
        return pointRuleDialog;
    }
    @Override
    protected int layoutId() {
        return R.layout.mine_point_rule_dialog;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDatabind.tvRuleDetailPoint.setText(Html.fromHtml(getString(R.string.mine_point_detail_rule),Html.FROM_HTML_MODE_LEGACY));
        } else {
            mDatabind.tvRuleDetailPoint.setText(Html.fromHtml(getString(R.string.mine_point_detail_rule)));
        }
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }
}
