package com.knight.wanandroid.module_mine.module_contract;

import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 19:09
 * @descript:我的界面合约分包
 */
public interface MineContract {



    interface MineView extends BaseView{


    }


    interface MineModel extends BaseModel{


    }

    abstract class MineDataPresenter extends BasePresenter<MineView,MineModel>{

    }
}
