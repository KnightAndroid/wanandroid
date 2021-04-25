package com.knight.wanandroid.module_square.module_fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_square.R;
import com.knight.wanandroid.module_square.databinding.SquareFragmentSquareBinding;
import com.knight.wanandroid.module_square.module_adapter.HotKeyAdapter;
import com.knight.wanandroid.module_square.module_contact.SquareContact;
import com.knight.wanandroid.module_square.module_entity.SearchHotKeyEntity;
import com.knight.wanandroid.module_square.module_model.SquareModel;
import com.knight.wanandroid.module_square.module_presenter.SquarePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 16:46
 * @descript:广场页面
 */
@Route(path = RoutePathFragment.Square.Square_Pager)
public class SquareFragment extends BaseFragment<SquareFragmentSquareBinding, SquarePresenter, SquareModel> implements SquareContact.SquareView {

    private HotKeyAdapter mHotKeyAdapter;


    @Override
    protected int layoutId() {
        return R.layout.square_fragment_square;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        loadLoading(mDatabind.squareSharearticleFreshlayout);
        mHotKeyAdapter = new HotKeyAdapter(new ArrayList<SearchHotKeyEntity>());
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getActivity());
        //方向 主轴为水平方向,起点在左端
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //左对齐
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        SetInitCustomView.initSwipeRecycleview(mDatabind.squareSearchhotRv,flexboxLayoutManager,mHotKeyAdapter,false);



    }

    @Override
    protected void reLoadData() {
        mPresenter.requestHotKey((BaseDBActivity) getActivity());
    }

    /**
     *
     * 懒加载
     *
     */
    @Override
    protected void lazyLoadData() {
        mPresenter.requestHotKey((BaseDBActivity) getActivity());
    }

    @Override
    public void setHotKey(List<SearchHotKeyEntity> searchHotKeyEntities) {
        showSuccess();
        mHotKeyAdapter.setNewInstance(searchHotKeyEntities);
    }

    @Override
    public void setNewShareData() {

    }

    @Override
    public void setShareArticle() {

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
        showloadFailure();

    }

    public class ProcyClick{

        public void squareClick(){

            //点击事件
            Toast.makeText(getActivity(),"广场界面",Toast.LENGTH_SHORT).show();
        }

    }
}
