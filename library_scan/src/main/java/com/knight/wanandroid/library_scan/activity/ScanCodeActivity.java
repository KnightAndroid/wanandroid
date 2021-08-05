package com.knight.wanandroid.library_scan.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.common.util.concurrent.ListenableFuture;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_scan.R;
import com.knight.wanandroid.library_scan.databinding.ScancodeActivityBinding;
import com.knight.wanandroid.library_scan.decode.ScanCodeAnalyzer;
import com.knight.wanandroid.library_scan.decode.ScanCodeConfig;
import com.knight.wanandroid.library_scan.entity.ScanCodeEntity;
import com.knight.wanandroid.library_scan.listener.OnScanCodeListener;
import com.knight.wanandroid.library_scan.listener.PreviewTouchListener;
import com.knight.wanandroid.library_scan.widget.BaseScanView;
import com.knight.wanandroid.library_scan.widget.ScanView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCase;
import androidx.camera.core.ZoomState;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;


/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/19 11:07
 * @descript:
 */
public class ScanCodeActivity extends BaseDBActivity<ScancodeActivityBinding> {

    private int lensFacing = CameraSelector.LENS_FACING_BACK;
    private ImageCapture mImageCapture;
    private Camera camera;
    private Preview preview;
    private ImageAnalysis imageAnalyzer;
    private CameraControl cameraControl;
    private CameraInfo mCameraInfo;
    private ExecutorService cameraExecutor;
    private BaseScanView baseScanView;
    private ScanCodeEntity mScanCodeEntity;

    private double RATIO_4_3_VALUE = 4.0/3.0;
    private double RATIO_16_9_VALUE = 16.0/9.0;
    private boolean isOpenLight;






    @Override
    public int layoutId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        return R.layout.scancode_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        initData();
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    @SuppressLint("NewApi")
    public void initData(){
        mScanCodeEntity = getIntent().getExtras().getParcelable(ScanCodeConfig.MODEL_KEY);

        //添加扫描布局
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        baseScanView  = new ScanView(this);
        if (baseScanView != null) {
            baseScanView.setLayoutParams(lp);
            mDatabind.scanRoot.addView(baseScanView);
        }

        cameraExecutor =new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        //surface监听
        mDatabind.scanPreview.post(() -> {
            //设置实例
            bindCameraUseCases();
        });
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void bindCameraUseCases(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mDatabind.scanPreview.getDisplay().getRealMetrics(displayMetrics);
        int screenAspectRatio = this.aspectRatio(displayMetrics.widthPixels / 2, displayMetrics.heightPixels / 2);
        int width = mDatabind.scanPreview.getMeasuredWidth();
        int height;
        Size size;
        if (screenAspectRatio == AspectRatio.RATIO_16_9) {
            height = (int)(width * RATIO_16_9_VALUE);
        } else {
            height = (int)(width * RATIO_4_3_VALUE);
        }
        size = new Size(width,height);

        //获取旋转角度
        int rotation = mDatabind.scanPreview.getDisplay().getRotation();
        //绑定生命周期
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(this.lensFacing).build();
        ListenableFuture cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                ProcessCameraProvider cameraProvider = null;
                try {
                    cameraProvider = (ProcessCameraProvider)cameraProviderFuture.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mImageCapture = new ImageCapture.Builder()
                        .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                        .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                        .build();
                //预览用例
                preview = new Preview.Builder()
                        .setTargetResolution(size)
                        .setTargetRotation(rotation)
                        .build();
                //图像分析用例
                imageAnalyzer = new ImageAnalysis.Builder()
                        .setTargetResolution(size)
                        .setTargetRotation(rotation)
                        .build();

                imageAnalyzer.setAnalyzer(cameraExecutor,new ScanCodeAnalyzer(ScanCodeActivity.this, mScanCodeEntity, new OnScanCodeListener() {
                    @Override
                    public void onCodeResult(String code) {
                        Intent intent = new Intent();
                        intent.putExtra(ScanCodeConfig.CODE_KEY,code);
                        setResult(Activity.RESULT_OK,intent);
                        finish();
                        overridePendingTransition(R.anim.base_bottom_slient,R.anim.base_bottom_out);
                    }
                }));

                //必须在重新绑定用例之前取消之前绑定
                cameraProvider.unbindAll();
                try {
                    camera = cameraProvider.bindToLifecycle(ScanCodeActivity.this, cameraSelector, preview, (UseCase) mImageCapture, (UseCase)imageAnalyzer);
                    preview.setSurfaceProvider(mDatabind.scanPreview.getSurfaceProvider());
                    cameraControl = camera.getCameraControl();
                    mCameraInfo = camera.getCameraInfo();
                    bindTouchListener();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }


    private void bindTouchListener() {
        LiveData<ZoomState> zoomState = mCameraInfo.getZoomState();
        PreviewTouchListener cameraXPreviewViewTouchListener = new PreviewTouchListener(this);
        cameraXPreviewViewTouchListener.setCustomTouchListener(new PreviewTouchListener.CustomTouchListener() {
            @Override
            public void zoom(float delta) {
                if(zoomState.getValue() != null) {
                    float currentZoomRatio = zoomState.getValue().getZoomRatio();
                    cameraControl.setZoomRatio(currentZoomRatio * delta);
                }
            }
        });
        mDatabind.scanPreview.setOnTouchListener(cameraXPreviewViewTouchListener);
    }


    private int aspectRatio(int width,int height){
        double previewRatio = Math.max(width, height) / Math.min(width, height);
        if (Math.abs(previewRatio - RATIO_4_3_VALUE) <= Math.abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3;
        } else {
            return AspectRatio.RATIO_16_9;
        }
    }


    public class ProxyClick{

        public void finishActivity(){
            finish();
            overridePendingTransition(R.anim.base_bottom_slient,R.anim.base_bottom_out);
        }

        public void flashClick(){
            isOpenLight = !isOpenLight;
            cameraControl.enableTorch(isOpenLight);
            if (isOpenLight) {
                mDatabind.scanIvFlash.setBackgroundResource(R.drawable.scan_icon_openflash);
            } else {
                mDatabind.scanIvFlash.setBackgroundResource(R.drawable.scan_icon_closeflash);
            }
        }
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        cameraExecutor.shutdownNow();
        baseScanView.cancelAnim();
    }

}
