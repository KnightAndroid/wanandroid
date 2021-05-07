package com.knight.wanandroid.module_square.module_contract;

import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_square.module_entity.SquareArticleListEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 16:11
 * @descript:
 */
public interface SquareContact {


   interface SquareView extends BaseView{


      //搜索热词
      void setHotKey(List<SearchHotKeyEntity> searchHotKeyEntities);

      //获取最新分享
      void setNewShareData();

      //分享最新文章
      void setShareArticles(SquareArticleListEntity result);

   }

   interface SquareModel extends BaseModel{

      //请求热词数据
      void requestHotKey(BaseFragment fragment, MvpListener mvpListener);
      //请求数据
      void requestShareArticles(BaseFragment fragment, int page,MvpListener mvpListener);
   }


   abstract class SquareDataPresenter extends BasePresenter<SquareModel,SquareView>{

      //请求热词
      public abstract void requestHotKey();
      //具体实现
      public abstract void requestShareData(int page);
   }
}
