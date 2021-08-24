package com.knight.wanandroid.module_home.module_logic;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.widget.ImageView;

import com.knight.wanandroid.module_home.module_adapter.TopArticleAdapter;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 17:53
 * @descript:顶部逻辑处理
 */
public final class HomeArticleLogic {


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


    public void setArrowAnimate(TopArticleAdapter mTopArticleAdapter,ImageView view,boolean isShowOnlythree){
        ObjectAnimator animator;
        if (isShowOnlythree) {
            animator = ObjectAnimator.ofFloat(view,"rotation",180f,0f);
        } else {
            animator = ObjectAnimator.ofFloat(view,"rotation",0f,180f);
        }
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //结束之后
                mTopArticleAdapter.setShowOnlyThree(isShowOnlythree);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(500);
        animator.start();

    }




}
