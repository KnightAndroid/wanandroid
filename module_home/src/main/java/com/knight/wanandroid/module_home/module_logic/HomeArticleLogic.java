package com.knight.wanandroid.module_home.module_logic;

import androidx.recyclerview.widget.DiffUtil;

import com.knight.wanandroid.library_util.imageengine.GlideEngineUtils;
import com.knight.wanandroid.module_home.module_adapter.TopArticleAdapter;
import com.knight.wanandroid.module_home.module_entity.TopArticleModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 17:53
 * @descript:顶部逻辑处理
 */
public class HomeArticleLogic {


    private static HomeArticleLogic instance = null;
    private HomeArticleLogic () {

    }

    public static HomeArticleLogic getInstance() {
        if (null == instance) {
            synchronized (HomeArticleLogic.class) {
                if (null == instance) {
                    instance = new HomeArticleLogic();
                }
            }
        }
        return instance;
    }




}
