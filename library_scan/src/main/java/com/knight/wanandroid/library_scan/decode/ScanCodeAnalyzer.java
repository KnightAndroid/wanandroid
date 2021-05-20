package com.knight.wanandroid.library_scan.decode;

import android.app.Activity;
import android.graphics.ImageFormat;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.knight.wanandroid.library_scan.listener.OnScanCodeListener;
import com.knight.wanandroid.library_scan.entity.ScanCodeEntity;
import com.knight.wanandroid.library_scan.utils.AudioUtil;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/19 10:40
 * @descript:
 */
public class ScanCodeAnalyzer implements ImageAnalysis.Analyzer {


    private final AudioUtil audioUtil;
    private final MultiFormatReader reader;
    @NotNull
    private final ScanCodeEntity scanCodeEntity;
    @NotNull
    private final OnScanCodeListener onScancodeListenner;

    public ScanCodeAnalyzer(@NotNull Activity mActivity, @NotNull ScanCodeEntity scanCodeEntity, @NotNull OnScanCodeListener onScancodeListenner) {
        super();
        this.scanCodeEntity = scanCodeEntity;
        this.onScancodeListenner = onScancodeListenner;
        this.audioUtil = new AudioUtil(mActivity, this.scanCodeEntity.getAudioId());
        this.reader = this.initReader();
    }

    private byte[] toByteArray(@NotNull ByteBuffer byteArray){
        byteArray.rewind();
        byte[] data = new byte[byteArray.remaining()];
        byteArray.get(data);
        return data;
    }
    @Override
    public void analyze(@NonNull ImageProxy image) {
        if (ImageFormat.YUV_420_888 != image.getFormat()) {
            image.close();
            return;
        }

        //将buffer 数据写入数组
        ImageProxy.PlaneProxy planeProxy = image.getPlanes()[0];
        ByteBuffer byteBuffer = planeProxy.getBuffer();
        byte[] data = this.toByteArray(byteBuffer);

        //图片宽高
        int height = image.getHeight();
        int width = image.getWidth();
        //图片旋转
        byte[] rotationData = new byte[data.length];
        int j = 0;
        int k = 0;
        for (int y = 0;y < height;y++) {
            for (int x = 0;x < width;x++) {
                j = x * height + height - y - 1;
                k = x + y * width;
                rotationData[j] = data[k];
            }

        }
        PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(rotationData, height, width, 0, 0, height, width, false);
        BinaryBitmap bitmap = new BinaryBitmap((new HybridBinarizer(source)));

        try {
            Result result = this.reader.decode(bitmap);
            ScanCodeEntity scanCodeEntity = this.scanCodeEntity;
            Boolean isPlay = scanCodeEntity != null ? scanCodeEntity.isPlayAudio() : null;

            if (isPlay) {
                this.audioUtil.playBeepSoundAndVibrate();
            }
            OnScanCodeListener onScanCodeListener = this.onScancodeListenner;
            onScanCodeListener.onCodeResult(result.getText());
        } catch (Exception e){
            image.close();
        } finally {
            image.close();
        }

    }

    private final MultiFormatReader initReader() {
        MultiFormatReader formatReader = new MultiFormatReader();
        Hashtable hints = new Hashtable();
        Vector decodeFormats = new Vector();
        decodeFormats.addAll((Collection)DecodeFormatManager.ONE_D_FORMATS);
        decodeFormats.addAll((Collection)DecodeFormatManager.DATA_MATRIX_FORMATS);
        decodeFormats.addAll((Collection)DecodeFormatManager.QR_CODE_FORMATS);
        ((Map)hints).put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        ((Map)hints).put(DecodeHintType.CHARACTER_SET, "UTF-8");
        formatReader.setHints((Map)hints);
        return formatReader;
    }


    @NotNull
    public final ScanCodeEntity getScanCodeModel() {
        return this.scanCodeEntity;
    }

    @NotNull
    public final OnScanCodeListener getOnScancodeListenner() {
        return this.onScancodeListenner;
    }
}
