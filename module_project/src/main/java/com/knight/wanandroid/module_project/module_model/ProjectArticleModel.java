package com.knight.wanandroid.module_project.module_model;

import com.knight.wanandroid.library_base.api.CancelCollectArticleApi;
import com.knight.wanandroid.library_base.api.CollectArticleApi;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.library_network.request.GetRequest;
import com.knight.wanandroid.module_project.module_contract.ProjectArticleContract;
import com.knight.wanandroid.module_project.module_entity.ProjectArticleListEntity;
import com.knight.wanandroid.module_project.module_request.ProjectArticleApi;
import com.knight.wanandroid.module_project.module_request.ProjectNewArticleApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/27 17:49
 * @descript:
 */
public final class ProjectArticleModel implements ProjectArticleContract.ProjectArticleModel {

    /**
     *
     * 项目列表文章请求
     * @param fragment
     * @param page
     * @param cid
     * @param mvpListener
     */
    @Override
    public void requestProjectArticle(BaseFragment fragment, int page, int cid, boolean isNewProject, MvpListener mvpListener) {
        GetRequest projectArticleRequest = GoHttp.get(fragment);
        if (isNewProject) {
            projectArticleRequest.api(new ProjectNewArticleApi().setPage(page));
        } else {
            projectArticleRequest.api(new ProjectArticleApi().setPage(page).setCid(cid));
        }
        projectArticleRequest.request(new HttpCallback<HttpData<ProjectArticleListEntity>>(fragment) {

                                          @Override
                                          public void onSucceed(HttpData<ProjectArticleListEntity> result) {
                                              mvpListener.onSuccess(result.getData());
                                          }

                                          @Override
                                          public void onFail(Exception e) {
                                              mvpListener.onError(e.getMessage());
                                          }
                                      }

        );

    }

    @Override
    public void requestCollectArticle(BaseFragment fragment, int collectArticleId, MvpListener mvpListener) {
        GoHttp.post(fragment)
                .api(new CollectArticleApi().setCollectArticleId(collectArticleId))
                .request(new HttpCallback<HttpData>(fragment){
                    @Override
                    public void onSucceed(HttpData result) {
                        mvpListener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }


                });
    }

    @Override
    public void requestCancelCollectArticle(BaseFragment fragment, int collectArticleId, MvpListener mvpListener) {
        GoHttp.post(fragment)
                .api(new CancelCollectArticleApi().setCancelArticleId(collectArticleId))
                .request(new HttpCallback<HttpData>(fragment){
                    @Override
                    public void onSucceed(HttpData result) {
                        mvpListener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }


                });
    }


}
