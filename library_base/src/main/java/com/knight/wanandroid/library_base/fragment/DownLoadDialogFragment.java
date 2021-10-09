package com.knight.wanandroid.library_base.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;

import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_base.basefragment.BaseDBDialogFragment;
import com.knight.wanandroid.library_base.databinding.BaseDownloadDialogBinding;
import com.knight.wanandroid.library_util.DownLoadManagerUtils;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.ThreadPoolUtils;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/11 14:59
 * @descript:
 */
public class DownLoadDialogFragment extends BaseDBDialogFragment<BaseDownloadDialogBinding> {



    private String downLoadLink;
    private File apkFile;
    private final int successDownLoadApk = 1;


    public DownLoadDialogFragment(){

    }





    public static DownLoadDialogFragment newInstance(String downLoadLink){
        DownLoadDialogFragment downLoadDialogFragment = new DownLoadDialogFragment();
        Bundle args = new Bundle();
        args.putString("downLoadLink",downLoadLink);
        downLoadDialogFragment.setArguments(args);
        return downLoadDialogFragment;
    }


    @Override
    protected int layoutId() {
        return R.layout.base_download_dialog;
    }

    @Override
    protected void initView() {
        downLoadLink = getArguments().getString("downLoadLink");
        ThreadPoolUtils.execute(new DownloadApkTask());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    private Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == successDownLoadApk) {
                //下载成功
                dismiss();
                SystemUtils.installApk(apkFile,getActivity());
            } else if (msg.what == 2) {
                mDatabind.baseTvDownlaodRate.setText(((int)(  ((float)msg.arg2 / (float)msg.arg1)  * 100) ) +"%");
                mDatabind.baseTvSpeed.setText(SystemUtils.formetFileSize(msg.arg2) + "/" + SystemUtils.formetFileSize(msg.arg1));

            }
        }
    };


    private class DownloadApkTask implements Runnable{

        @Override
        public void run() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                apkFile = DownLoadManagerUtils.downloadApk(downLoadLink,mDatabind.baseDownloadProgressbar,mHandler,getActivity());
                Message msg = new Message();
                msg.what = successDownLoadApk;
                mHandler.sendMessage(msg);
            }
        }
    }
}
