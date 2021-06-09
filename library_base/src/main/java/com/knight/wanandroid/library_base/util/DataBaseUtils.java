package com.knight.wanandroid.library_base.util;

import android.text.TextUtils;

import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.wanandroid.knight.library_database.entity.HistoryReadRecordsEntity;
import com.wanandroid.knight.library_database.entity.SearchHistroyKeywordEntity;
import com.wanandroid.knight.library_database.repository.HistoryReadRecordsRepository;
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


    /**
     *
     * 保存阅读历史记录
     * @param historyReadRecordEntity
     */
    public static void saveHistoryRecord(HistoryReadRecordsEntity historyReadRecordEntity) {
        HistoryReadRecordsRepository.getInstance().findHistoryReadRecords(historyReadRecordEntity.getWebUrl(), historyReadRecordEntity.getArticleId(), ModuleConfig.getInstance().user == null ? 0:ModuleConfig.getInstance().user.getId(),new HistoryReadRecordsRepository.OnQueryRecordsSuccessCallBack() {
            @Override
            public void onQueryRecordsSuccessCallBack(List<HistoryReadRecordsEntity> historyReadRecordsEntities) {

            }

            @Override
            public void onFindReadRecordsEntity(HistoryReadRecordsEntity historyReadRecordsEntity){
                if (historyReadRecordsEntity != null) {
                    //更新
                    HistoryReadRecordsRepository.getInstance().updateHistroyRecord(historyReadRecordsEntity);
                } else {
                    //插入
                    HistoryReadRecordsRepository.getInstance().insertHistroyRecordsKeyword(historyReadRecordEntity);
                }
            }
        });
    }



}
