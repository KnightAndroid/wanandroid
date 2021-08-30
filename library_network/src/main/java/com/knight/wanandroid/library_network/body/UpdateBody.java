package com.knight.wanandroid.library_network.body;

import com.knight.wanandroid.library_network.NetWorkUtils;
import com.knight.wanandroid.library_network.data.ContentType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:14
 * @descript: 上传文件流
 */
public final class UpdateBody extends RequestBody {

    /** 上传源 */
    private final Source mSource;

    /** 内容类型 */
    private final MediaType mMediaType;

    /** 内容名称 */
    private final String mName;

    /** 内容大小 */
    private final long mLength;

    public UpdateBody(File file) throws FileNotFoundException {
        this(Okio.source(file), NetWorkUtils.guessMimeType(file.getName()), file.getName(), file.length());
    }

    public UpdateBody(InputStream inputStream, String name) throws IOException {
        this(Okio.source(inputStream), ContentType.STREAM, name, inputStream.available());
    }

    public UpdateBody(Source source, MediaType type, String name, long length) {
        mSource = source;
        mMediaType = type;
        mName = name;
        mLength = length;
    }

    @Override
    public MediaType contentType() {
        return mMediaType;
    }

    @Override
    public long contentLength() {
        return mLength;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        try {
            sink.writeAll(mSource);
        } finally {
            NetWorkUtils.closeStream(mSource);
        }
    }

    public String getName() {
        return mName;
    }


}