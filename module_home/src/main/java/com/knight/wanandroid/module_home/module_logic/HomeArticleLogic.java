package com.knight.wanandroid.module_home.module_logic;

import com.knight.wanandroid.module_home.module_adapter.TopArticleAdapter;
import com.knight.wanandroid.module_home.module_entity.TopArticleModel;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 17:53
 * @descript:顶部逻辑处理
 */
public class HomeArticleLogic {


    /**
     *
     * 设置三条顶部数据
     * @param topArticleModelList
     * @param mTopArticleAdapter
     */
    public static void setTopArticleStatus(List<TopArticleModel> topArticleModelList, TopArticleAdapter mTopArticleAdapter) {
        if (topArticleModelList.size() >= 3) {
            mTopArticleAdapter.setNewInstance(topArticleModelList.subList(0,3));
        } else {
            mTopArticleAdapter.setNewInstance(topArticleModelList);
        }

    }
}
