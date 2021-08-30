package com.knight.wanandroid.library_widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.knight.wanandroid.library_common.utils.ColorUtils;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/26 14:43
 * @descript:底部加载
 */
public class FooterMoreView extends LinearLayout implements SwipeRecyclerView.LoadMoreView, View.OnClickListener {


    private Context context;
    private ProgressBar mProgressBar;
    private TextView mTvMessage;




    private SwipeRecyclerView.LoadMoreListener mLoadMoreListener;

    public void setLoadMoreListener(SwipeRecyclerView.LoadMoreListener mLoadMoreListener){
        this.mLoadMoreListener = mLoadMoreListener;
    }


    public FooterMoreView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FooterMoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public FooterMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FooterMoreView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(View v) {

        if (mLoadMoreListener != null) {
            if (mTvMessage.getText() != "没有更多数据了" && mProgressBar.getVisibility() != View.VISIBLE) {
                mLoadMoreListener.onLoadMore();
            }

        }
    }

    @Override
    public void onLoading() {
        setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTvMessage.setVisibility(View.VISIBLE);
        mTvMessage.setText("正在努力加载,请稍后...");
    }

    /**
     * 加载更多完成了
     * @param dataEmpty
     * @param hasMore
     */
    @Override
    public void onLoadFinish(boolean dataEmpty, boolean hasMore) {
        if (!hasMore) {
            if (dataEmpty) {
                mProgressBar.setVisibility(View.GONE);
                mTvMessage.setVisibility(View.VISIBLE);
                mTvMessage.setText("暂时没有数据");
            } else {
                mProgressBar.setVisibility(View.GONE);
                mTvMessage.setVisibility(View.VISIBLE);
                mTvMessage.setText("没有更多数据了");
            }
        } else {
            setVisibility(View.INVISIBLE);
        }
    }

    /**
     *
     * 调用了setAutoLoadMore(false)后，
     * 在需要加载更多的时候，这个方法会被调用，并传入加载更多的listener。
     * @param loadMoreListener
     */
    @Override
    public void onWaitToLoadMore(SwipeRecyclerView.LoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
        setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTvMessage.setVisibility(View.VISIBLE);
        mTvMessage.setText("点我加载更多");
    }

    @Override
    public void onLoadError(int errorCode, String errorMessage) {
        setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTvMessage.setVisibility(View.VISIBLE);
        mTvMessage.setText(errorMessage);
    }

    private void init(){
        setLayoutParams(new ViewGroup.LayoutParams(-1,-2));
        setGravity(Gravity.CENTER);
        setVisibility(View.GONE);
        int minHeight = ScreenUtils.dp2px(56f);
        setMinimumHeight(minHeight);
        View.inflate(context,R.layout.base_footer_loadmore,this);
        mProgressBar = findViewById(R.id.base_loading_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgressBar.setIndeterminateTintMode(PorterDuff.Mode.SRC_ATOP);
            mProgressBar.setIndeterminateTintList(ColorUtils.getOneColorStateList(context));
        }
        mTvMessage = findViewById(R.id.base_tv_message);
        setOnClickListener(this);
    }

    public void setLoadViewColor(ColorStateList colorStateList){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgressBar.setIndeterminateTintMode(PorterDuff.Mode.SRC_ATOP);
            mProgressBar.setIndeterminateTintList(colorStateList);
        }

    }



}
