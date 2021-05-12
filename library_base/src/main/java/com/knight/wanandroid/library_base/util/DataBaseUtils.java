package com.knight.wanandroid.library_base.util;

import android.text.TextUtils;

import com.wanandroid.knight.library_database.entity.SearchHistroyKeywordEntity;
import com.wanandroid.knight.library_database.repository.HistroyKeywordsRepository;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/12 9:27
 * @descript:
 */
public class DataBaseUtils {


    /**
     *
     * 保存搜索关键词
     * @param keyword
     */
    public static void saveSearchKeyword(String keyword) {
        HistroyKeywordsRepository.getInstance().queryHistroyKeywords(new HistroyKeywordsRepository.OnQuerySuccessCallBack() {
            @Override
            public void onQuerySuccessCallBack(List<SearchHistroyKeywordEntity> mSearchHistroyKeywordEntities) {
                long id = 0;
                for (SearchHistroyKeywordEntity searchHistroyKeywordEntity : mSearchHistroyKeywordEntities) {
                    if (TextUtils.equals(searchHistroyKeywordEntity.getName(), keyword)) {
                        id = searchHistroyKeywordEntity.getId();
                        break;
                    }
                }
                if (id != 0) {
                    HistroyKeywordsRepository.getInstance().deleteHistroyKeyword(id);
                }
                HistroyKeywordsRepository.getInstance().insertHistroyKeyword(new SearchHistroyKeywordEntity(keyword));
            }
        });
    }
}
