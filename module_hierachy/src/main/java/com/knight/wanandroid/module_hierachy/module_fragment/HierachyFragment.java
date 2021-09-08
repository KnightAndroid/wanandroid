package com.knight.wanandroid.module_hierachy.module_fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.module_hierachy.R;
import com.knight.wanandroid.module_hierachy.databinding.HierachyFragmentMainBinding;
import com.knight.wanandroid.module_hierachy.module_adapter.HierachyLeftBarAdapter;
import com.knight.wanandroid.module_hierachy.module_contract.HierachyContract;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyListEntity;
import com.knight.wanandroid.module_hierachy.module_entity.NavigateListEntity;
import com.knight.wanandroid.module_hierachy.module_listener.CheckListener;
import com.knight.wanandroid.module_hierachy.module_listener.RvListener;
import com.knight.wanandroid.module_hierachy.module_model.HierachyModel;
import com.knight.wanandroid.module_hierachy.module_presenter.HierachyPresenter;
import com.knight.wanandroid.module_hierachy.module_widget.ItemHeaderDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 15:17
 * @descript:体系fragment
 */
@Route(path = RoutePathFragment.Hierachy.Hierachy_Pager)
public final class HierachyFragment extends BaseFragment<HierachyFragmentMainBinding, HierachyPresenter, HierachyModel> implements HierachyContract.HierachyView, CheckListener {

    private HierachyLeftBarAdapter mHierachyLeftBarAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<HierachyListEntity> mHierachyListEntities;
    private HierachyRightFragment mHierachyRightFragment;
    /**
     * 点击左边某一个具体的item的位置
     */
    private int targetPosition;
    private boolean isMoved;

    @Override
    protected int layoutId() {
        return R.layout.hierachy_fragment_main;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        loadLoading(mDatabind.hierachyLlMain);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mDatabind.hierachyLeftSidebar.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mDatabind.hierachyLeftSidebar.addItemDecoration(decoration);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.requestHierachyData();
    }

    @Override
    protected void reLoadData() {
        mPresenter.requestHierachyData();
    }

    @Override
    public void setHierachyData(List<HierachyListEntity> data) {
        showSuccess();
        mHierachyListEntities = data;
        List<String> list = new ArrayList<>();
        for (int i = 0;i < data.size();i++) {
            list.add(data.get(i).getName());
        }
        mHierachyLeftBarAdapter = new HierachyLeftBarAdapter(getActivity(), list, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if (mHierachyRightFragment != null) {
                    isMoved = true;
                    targetPosition = position;

                    setChecked(position,true);

                }
            }
        });
        mDatabind.hierachyLeftSidebar.setAdapter(mHierachyLeftBarAdapter);
        createFragment();


    }

    @Override
    public void setNavigateData(List<NavigateListEntity> navigateListEntity) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.show(errorMsg);

    }

    private void setChecked(int position, boolean isLeft) {
        if (isLeft) {
            mHierachyLeftBarAdapter.setCheckedPosition(position);
            //此处的位置需要根据每个分类的集合来进行计算
            int count = 0;
            for (int i = 0; i < position; i++) {
                count += mHierachyListEntities.get(i).getChildren().size();
            }
            count += position;
            mHierachyRightFragment.setData(count);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition));//凡是点击左边，将左边点击的位置作为当前的tag
        } else {
            if (isMoved) {
                isMoved = false;
            } else {
                mHierachyLeftBarAdapter.setCheckedPosition(position);
            }

            ItemHeaderDecoration.setCurrentTag(String.valueOf(position));//如果是滑动右边联动左边，则按照右边传过来的位置作为tag

        }
        moveToCenter(position);

    }

    //将当前选中的item居中
    private void moveToCenter(int position) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        View childAt = mDatabind.hierachyLeftSidebar.getChildAt(position - mLinearLayoutManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - mDatabind.hierachyLeftSidebar.getHeight() / 2);
            mDatabind.hierachyLeftSidebar.smoothScrollBy(0, y);
        }

    }

    @Override
    public void check(int position, boolean isScroll) {
        setChecked(position, isScroll);
    }

    public void createFragment() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        mHierachyRightFragment = (HierachyRightFragment) getChildFragmentManager().findFragmentByTag("hierachyfragment");
        if (mHierachyRightFragment == null) {
            mHierachyRightFragment = HierachyRightFragment.newInstance(false);
            mHierachyRightFragment.setListener(this);
            fragmentTransaction.replace(R.id.hierachy_right_sidebar,mHierachyRightFragment,"hierachyfragment");
            fragmentTransaction.commitNowAllowingStateLoss();

        }




    }




}
