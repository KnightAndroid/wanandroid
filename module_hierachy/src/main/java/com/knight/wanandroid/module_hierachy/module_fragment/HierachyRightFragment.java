package com.knight.wanandroid.module_hierachy.module_fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.module_hierachy.R;
import com.knight.wanandroid.module_hierachy.databinding.HierachyRightFragmentBinding;
import com.knight.wanandroid.module_hierachy.module_adapter.HierachyClassifyDetailAdapter;
import com.knight.wanandroid.module_hierachy.module_contract.HierachyContract;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyChildrenEntity;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyListEntity;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyRightBeanEntity;
import com.knight.wanandroid.module_hierachy.module_entity.NavigateChildrenEntity;
import com.knight.wanandroid.module_hierachy.module_entity.NavigateListEntity;
import com.knight.wanandroid.module_hierachy.module_listener.CheckListener;
import com.knight.wanandroid.module_hierachy.module_listener.RvListener;
import com.knight.wanandroid.module_hierachy.module_model.HierachyModel;
import com.knight.wanandroid.module_hierachy.module_presenter.HierachyPresenter;
import com.knight.wanandroid.module_hierachy.module_widget.ItemHeaderDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 16:47
 * @descript:右边选择
 */
@Route(path = RoutePathFragment.Hierachy.Hierachy_Right)
public class HierachyRightFragment extends BaseFragment<HierachyRightFragmentBinding, HierachyPresenter, HierachyModel> implements HierachyContract.HierachyView, CheckListener {
    private HierachyClassifyDetailAdapter mHierachyClassifyDetailAdapter;
    private List<HierachyRightBeanEntity> mDatas = new ArrayList<>();
    private ItemHeaderDecoration mDecoration;
    private boolean move = false;
    private int mIndex = 0;
  //  private GridLayoutManager mManager;
    private CheckListener checkListener;
    private boolean isNavigate;
    private FlexboxLayoutManager mManager;


    public static HierachyRightFragment newInstance(boolean isNavigate){
        HierachyRightFragment hierachyRightFragment = new HierachyRightFragment();
        Bundle args = new Bundle();
        args.putBoolean("isNavigate",isNavigate);
        hierachyRightFragment.setArguments(args);
        return hierachyRightFragment;
    }

    @Override
    protected int layoutId() {
        return R.layout.hierachy_right_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        isNavigate = getArguments().getBoolean("isNavigate");
        mDatabind.hierachyRightRv.addOnScrollListener(new RecyclerViewListener());




//
//        mManager = new GridLayoutManager(getActivity(), 3);
//        //通过isTitle的标志来判断是否是title
//        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return mDatas.get(position).isTitle() ? 3 : 1;
//            }
//        });
        mManager = new FlexboxLayoutManager(getActivity());
                mManager.setFlexDirection(FlexDirection.ROW);
                //左对齐
                mManager.setJustifyContent(JustifyContent.FLEX_START);
                mManager.setAlignItems(AlignItems.CENTER);
        mDatabind.hierachyRightRv.setLayoutManager(mManager);



//        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getActivity());
//        //方向 主轴为水平方向,起点在左端
//        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
//        //左对齐
//        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
//        flexboxLayoutManager.setAlignItems(AlignItems.CENTER);
//        flexboxLayoutManager.setS
//        mDatabind.hierachyRightRv.setLayoutManager(flexboxLayoutManager);
        mHierachyClassifyDetailAdapter = new HierachyClassifyDetailAdapter(getActivity(), mDatas, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                String content = "";
                if (id == R.id.hierachy_root) {
                    content = "title";
                } else if (id == R.id.content) {
                    content = "content";
                }
                ToastUtils.getInstance().showToast("当前点击的是"+content+":"+mDatas.get(position).getName());
            }
        });



    }


    @Override
    protected void lazyLoadData() {
        loadLoading(mDatabind.hierachyLlRight);
        //导航请求
        if (isNavigate) {
            mPresenter.requestNavigateData((BaseDBActivity) getActivity());
        } else {
            //体系请求
            mPresenter.requestHierachyData((BaseDBActivity) getActivity());
        }

    }
    @Override
    protected void reLoadData() {
        //导航请求
        if (isNavigate) {
            mPresenter.requestNavigateData((BaseDBActivity) getActivity());
        } else {
            //体系请求
            mPresenter.requestHierachyData((BaseDBActivity) getActivity());
        }
    }

    public void setListener(CheckListener listener) {
        this.checkListener = listener;
    }

    @Override
    public void setHierachyData(List<HierachyListEntity> data) {
        showSuccess();
        for (int i = 0; i < data.size();i++) {
            HierachyRightBeanEntity hierachyRightBeanEntity = new HierachyRightBeanEntity();
            hierachyRightBeanEntity.setName(data.get(i).getName());
            hierachyRightBeanEntity.setTitle(true);
            hierachyRightBeanEntity.setTitleName(data.get(i).getName());
            hierachyRightBeanEntity.setTag(String.valueOf(i));
            mDatas.add(hierachyRightBeanEntity);
            List<HierachyChildrenEntity> hierachyChildrenEntities = data.get(i).getChildren();
            for(int j = 0;j< hierachyChildrenEntities.size();j++){
                HierachyRightBeanEntity hierachyRightBodyBeanEntity = new HierachyRightBeanEntity();
                hierachyRightBodyBeanEntity.setName(hierachyChildrenEntities.get(j).getName());
                hierachyRightBodyBeanEntity.setTag(String.valueOf(i));
                hierachyRightBodyBeanEntity.setTitleName(data.get(i).getName());
                mDatas.add(hierachyRightBodyBeanEntity);
            }

        }
        mDatabind.hierachyRightRv.setAdapter(mHierachyClassifyDetailAdapter);
        mDecoration = new ItemHeaderDecoration(getActivity(),mDatas);
        mDatabind.hierachyRightRv.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(checkListener);

        mHierachyClassifyDetailAdapter.notifyDataSetChanged();
        mDecoration.setData(mDatas);
    }

    @Override
    public void setNavigateData(List<NavigateListEntity> navigateListEntity) {
        showSuccess();
        for (int i = 0; i < navigateListEntity.size();i++) {
            HierachyRightBeanEntity hierachyRightBeanEntity = new HierachyRightBeanEntity();
            hierachyRightBeanEntity.setName(navigateListEntity.get(i).getName());
            hierachyRightBeanEntity.setTitle(true);
            hierachyRightBeanEntity.setTitleName(navigateListEntity.get(i).getName());
            hierachyRightBeanEntity.setTag(String.valueOf(i));
            mDatas.add(hierachyRightBeanEntity);

            List<NavigateChildrenEntity> navigateChildrenEntities = navigateListEntity.get(i).getArticles();
            for(int j = 0;j< navigateChildrenEntities.size();j++){
                HierachyRightBeanEntity hierachyRightBodyBeanEntity = new HierachyRightBeanEntity();
                hierachyRightBodyBeanEntity.setName(navigateChildrenEntities.get(j).getTitle());
                hierachyRightBodyBeanEntity.setTag(String.valueOf(i));
                hierachyRightBodyBeanEntity.setTitleName(navigateChildrenEntities.get(j).getChapterName());
                mDatas.add(hierachyRightBodyBeanEntity);
            }

        }
        mDatabind.hierachyRightRv.setAdapter(mHierachyClassifyDetailAdapter);
        mDecoration = new ItemHeaderDecoration(getActivity(),mDatas);
        mDatabind.hierachyRightRv.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(checkListener);

        mHierachyClassifyDetailAdapter.notifyDataSetChanged();
        mDecoration.setData(mDatas);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.getInstance().showToast(errorMsg);
    }

    @Override
    public void check(int position, boolean isScroll) {
        checkListener.check(position, isScroll);
    }

    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mDatabind.hierachyRightRv.getChildCount()) {
                    int top = mDatabind.hierachyRightRv.getChildAt(n).getTop();
                    mDatabind.hierachyRightRv.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mDatabind.hierachyRightRv.getChildCount()) {
                    int top = mDatabind.hierachyRightRv.getChildAt(n).getTop();
                    mDatabind.hierachyRightRv.scrollBy(0, top);
                }
            }
        }
    }

    public void setData(int n) {
        mIndex = n;
        mDatabind.hierachyRightRv.stopScroll();
        smoothMoveToPosition(n);
    }

    private void smoothMoveToPosition(int n) {
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mDatabind.hierachyRightRv.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mDatabind.hierachyRightRv.getChildAt(n - firstItem).getTop();
            mDatabind.hierachyRightRv.scrollBy(0, top);
        } else {
            mDatabind.hierachyRightRv.scrollToPosition(n);
            move = true;
        }
    }
}
