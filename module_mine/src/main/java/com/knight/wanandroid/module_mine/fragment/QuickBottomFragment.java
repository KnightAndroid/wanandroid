package com.knight.wanandroid.module_mine.fragment;

import android.view.Gravity;
import android.view.View;

import com.knight.wanandroid.library_base.basefragment.BaseDBDialogFragment;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineQuickloginBottomDialogBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/23 9:21
 * @descript:
 */
public final class QuickBottomFragment extends BaseDBDialogFragment<MineQuickloginBottomDialogBinding> {



    public QuickBottomFragment() {

    }

    public interface FingureLoginListener {
        void fingureQuick();
    }


    private FingureLoginListener mFingureLoginListener;

    public void setFingureLoginListener(FingureLoginListener mFingureLoginListener) {
        this.mFingureLoginListener = mFingureLoginListener;
    }



    @Override
    protected int layoutId() {
        return R.layout.mine_quicklogin_bottom_dialog;
    }

    @Override
    protected void initView() {
        mDatabind.setClick(new ProxyClick());
        if (CacheUtils.getFingerLogin()) {
            mDatabind.tvFingureLogin.setVisibility(View.VISIBLE);
        } else {
            mDatabind.tvFingureLogin.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }


    public class ProxyClick {
        public void fingureLogin() {
            dismiss();
            mFingureLoginListener.fingureQuick();
        }


        public void close() {
            dismiss();
        }
    }
}
