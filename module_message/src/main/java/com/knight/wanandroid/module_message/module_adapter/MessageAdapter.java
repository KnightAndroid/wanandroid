package com.knight.wanandroid.module_message.module_adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.module_message.R;
import com.knight.wanandroid.module_message.module_entity.MessageEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wananadroid
 * @Date 2021/6/30 16:59
 * @descript:
 */
public class MessageAdapter extends BaseQuickAdapter<MessageEntity, BaseViewHolder> {

    public MessageAdapter(@Nullable List<MessageEntity> data){
        super(R.layout.message_readed_item,data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MessageEntity messageEntity) {
        //作者
        if (!TextUtils.isEmpty(messageEntity.getFromUser())) {
            baseViewHolder.setText(R.id.message_item_author, messageEntity.getFromUser());
        } else {
            baseViewHolder.setText(R.id.message_item_author,"");
        }

        //Tag
        if (!TextUtils.isEmpty(messageEntity.getTag())) {
            baseViewHolder.setText(R.id.message_item_tag, messageEntity.getTag());
        } else {
            baseViewHolder.setGone(R.id.message_item_tag,true);
        }

        //时间
        if (!TextUtils.isEmpty(messageEntity.getNiceDate())) {
            baseViewHolder.setText(R.id.home_item_nicedata, messageEntity.getNiceDate());
        } else {
            baseViewHolder.setText(R.id.home_item_nicedata,"");
        }

        //标题
        if (!TextUtils.isEmpty(messageEntity.getTitle())) {
            baseViewHolder.setText(R.id.message_tv_title, messageEntity.getTitle());
        } else {
            baseViewHolder.setText(R.id.message_tv_title,"");
        }

        //描述
        if (!TextUtils.isEmpty(messageEntity.getMessage())) {
            baseViewHolder.setText(R.id.message_tv_desc, messageEntity.getMessage());
        } else {
            baseViewHolder.setText(R.id.message_tv_desc,"");
        }


    }
}
