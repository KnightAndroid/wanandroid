package com.knight.wanandroid.module_hierachy.module_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.module_hierachy.R;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyRightBeanEntity;
import com.knight.wanandroid.module_hierachy.module_holder.RvHolder;
import com.knight.wanandroid.module_hierachy.module_listener.RvListener;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 16:58
 * @descript:右边具体内容适配器
 */
public final class HierachyClassifyDetailAdapter extends RvAdapter<HierachyRightBeanEntity>{

    public HierachyClassifyDetailAdapter(Context context, List<HierachyRightBeanEntity> list, RvListener listener){
        super(context,list,listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return viewType == 0 ? R.layout.hierachy_item_right_title : R.layout.hierachy_classify_detail;
    }
    @Override
    public int getItemViewType(int position) {
        return list.get(position).isTitle() ? 0 : 1;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ClassifyHolder(view,viewType,listener);
    }

    public class ClassifyHolder extends RvHolder<HierachyRightBeanEntity> {
        //内容
        TextView hierachy_tv_content;
        //标题
        TextView hierachy_tv_title;
        //标题右边颜色条
        View hierachy_right_view;

        public ClassifyHolder(View itemView,int type,RvListener listener){
            super(itemView,type,listener);
            switch (type) {
                case 0:
                    hierachy_tv_title = itemView.findViewById(R.id.hierachy_tv_title);
                    hierachy_right_view = itemView.findViewById(R.id.hierachy_right_view);
                    break;
                case 1:
                    hierachy_tv_content = itemView.findViewById(R.id.hierachy_tv_content);
                    break;

            }
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void bindHolder(HierachyRightBeanEntity hierachyRightBeanEntity, int position) {
            int itemViewType = HierachyClassifyDetailAdapter.this.getItemViewType(position);
            switch (itemViewType) {
                case 0:
                    hierachy_tv_title.setText(hierachyRightBeanEntity.getName());
                    hierachy_right_view.setBackgroundColor(CacheUtils.getThemeColor());
                    break;
                case 1:
                    ViewGroup.LayoutParams lp = hierachy_tv_content.getLayoutParams();
                    if (lp instanceof FlexboxLayoutManager.LayoutParams) {
                        FlexboxLayoutManager.LayoutParams flexboxLp =
                                (FlexboxLayoutManager.LayoutParams) hierachy_tv_content.getLayoutParams();
                        flexboxLp.setFlexGrow(1.0f);
                        flexboxLp.setAlignSelf(AlignItems.FLEX_END);
                    }
                    hierachy_tv_content.setText(hierachyRightBeanEntity.getName());
                    hierachy_tv_content.setTextColor(CacheUtils.getThemeColor());
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                    gradientDrawable.setStroke(2,CacheUtils.getThemeColor());
                    gradientDrawable.setCornerRadius(ScreenUtils.dp2px(6f));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        hierachy_tv_content.setBackground(gradientDrawable);
                    } else {
                        hierachy_tv_content.setBackgroundDrawable(gradientDrawable);
                    }
                    break;
                default:
                    break;
            }

        }


    }



}
