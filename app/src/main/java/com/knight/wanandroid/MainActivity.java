package com.knight.wanandroid;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.knight.wanandroid.databinding.ActivityMainBinding;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
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


    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }
    
    @Override
    public void initView(Bundle savedInstanceState) {
       initFragment();

    }



    private void initFragment(){
        fragments.add(new HomeFragment());
        fragments.add(new SquareFragment());
        fragments.add(new ProjectFragment());
        fragments.add(new HierachyNavigateMainFragment());
        fragments.add(new MineFragment());
       // mDatabind.mainViewpager
        ViewSetUtils.setIsUserInputEnable(this,mDatabind.mainViewpager,fragments,false);

        mDatabind.btnNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.homeFragment){
                    mDatabind.mainViewpager.setCurrentItem(0,false);
                } else if(item.getItemId() == R.id.squareFragment){
                    mDatabind.mainViewpager.setCurrentItem(1,false);
                } else if(item.getItemId() == R.id.projectFragment){
                    mDatabind.mainViewpager.setCurrentItem(2,false);
                } else if(item.getItemId() == R.id.navigateFragment){
                    mDatabind.mainViewpager.setCurrentItem(3,false);
                } else if(item.getItemId() == R.id.mineFragment){
                    mDatabind.mainViewpager.setCurrentItem(4,false);
                }
                return true;
            }
        });

//        mDatabind.btnNav.setOnItemSelectedListener(new Function2<OneBottomNavigationBar.Item, Integer, Boolean>() {
//            @Override
//            public Boolean invoke(OneBottomNavigationBar.Item item, Integer integer) {
//                if(integer == 0){
//                    ARouter.getInstance().build(RoutePathFragment.Home.Home_Pager).navigation();
//                } else if(integer == 1){
//                    ARouter.getInstance().build(RoutePathFragment.Square.Square_pager).navigation();
//                }
//
//                return false;
//            }
//        });
    }

    @Override
    public void onSucceed(Object result) {

    }

    @Override
    public void onFail(Exception e) {

    }
}