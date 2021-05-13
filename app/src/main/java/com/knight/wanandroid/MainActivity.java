package com.knight.wanandroid;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.knight.wanandroid.databinding.ActivityMainBinding;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.module_hierachy.module_fragment.HierachyNavigateMainFragment;
import com.knight.wanandroid.module_home.module_fragment.HomeFragment;
import com.knight.wanandroid.module_mine.module_fragment.MineFragment;
import com.knight.wanandroid.module_project.module_fragment.ProjectFragment;
import com.knight.wanandroid.module_square.module_fragment.SquareFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MainActivity extends BaseDBActivity<ActivityMainBinding> {

    ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private int selectIndex;
    private HomeFragment mHomeFragment;
    private SquareFragment mSquareFragment;
    private ProjectFragment mProjectFragment;
    private HierachyNavigateMainFragment mHierachyNavigateMainFragment;
    private MineFragment mMineFragment;
    private long mExitAppTime;

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }
    
    @Override
    public void initView(Bundle savedInstanceState) {
       mDatabind.setClick(new ProcyClick());
       initFragment();

    }



    private void initFragment(){
        fragments.add(mHomeFragment = new HomeFragment());
        fragments.add(mSquareFragment = new SquareFragment());
        fragments.add(mProjectFragment = new ProjectFragment());
        fragments.add(mHierachyNavigateMainFragment = new HierachyNavigateMainFragment());
        fragments.add(mMineFragment = new MineFragment());
        ViewSetUtils.setIsUserInputEnable(this,mDatabind.mainViewpager,fragments,false);

        mDatabind.btnNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.homeFragment){
                    selectIndex = 0;
                    mDatabind.mainFloatBtn.setVisibility(View.VISIBLE);
                    mDatabind.mainViewpager.setCurrentItem(0,false);
                } else if(item.getItemId() == R.id.squareFragment){
                    selectIndex = 1;
                    mDatabind.mainFloatBtn.setVisibility(View.VISIBLE);
                    mDatabind.mainViewpager.setCurrentItem(1,false);
                } else if(item.getItemId() == R.id.projectFragment){
                    selectIndex = 2;
                    mDatabind.mainFloatBtn.setVisibility(View.GONE);
                    mDatabind.mainViewpager.setCurrentItem(2,false);
                } else if(item.getItemId() == R.id.navigateFragment){
                    selectIndex = 3;
                    mDatabind.mainFloatBtn.setVisibility(View.GONE);
                    mDatabind.mainViewpager.setCurrentItem(3,false);
                } else if(item.getItemId() == R.id.mineFragment){
                    selectIndex = 4;
                    mDatabind.mainFloatBtn.setVisibility(View.GONE);
                    mDatabind.mainViewpager.setCurrentItem(4,false);
                }
                return true;
            }
        });

    }



    public class ProcyClick{
        public void scrollTop(){
            switch (selectIndex){
                case 0:
                    mHomeFragment.scrollTop();
                    break;
                case 1:
                    mSquareFragment.scrollTop();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitAppTime <= 2000) {
                finish();
                System.exit(0);
            } else {
                mExitAppTime = System.currentTimeMillis();
                ToastUtils.getInstance().showToast(getString(R.string.app_exit_tip));
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}