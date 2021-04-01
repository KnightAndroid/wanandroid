package com.knight.wanandroid.module_square.module_contact;

import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 16:11
 * @descript:
 */
public interface SquareContact {


   interface SquareView extends BaseView{

      //获取最新分享
      void setNewShareData();

      //分享最新文章
      void setShareArticle();

   }

   interface SquareModel extends BaseModel{
      //请求数据
      void requestShareData();
   }


   abstract class SquareDataPresenter extends BasePresenter<SquareModel,SquareView>{
      //具体实现
      public abstract void requestShareData();
   }
}
