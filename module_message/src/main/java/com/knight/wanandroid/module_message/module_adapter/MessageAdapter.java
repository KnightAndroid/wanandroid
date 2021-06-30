package com.knight.wanandroid.module_message.module_adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.module_message.R;
import com.knight.wanandroid.module_message.module_entity.MessageReadedEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wananadroid
 * @Date 2021/6/30 16:59
 * @descript:
 */
public class MessageAdapter extends BaseQuickAdapter<MessageReadedEntity, BaseViewHolder> {

    public MessageAdapter(@Nullable List<MessageReadedEntity> data){
        super(R.layout.message_readed_item,data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MessageReadedEntity messageReadedEntity) {
        //作者
        if (!TextUtils.isEmpty(messageReadedEntity.getFromUser())) {
            baseViewHolder.setText(R.id.message_item_author,messageReadedEntity.getFromUser());
        } else {
            baseViewHolder.setText(R.id.message_item_author,"");
        }

        //Tag
        if (!TextUtils.isEmpty(messageReadedEntity.getTag())) {
            baseViewHolder.setText(R.id.message_item_tag,messageReadedEntity.getTag());
        } else {
            baseViewHolder.setGone(R.id.message_item_tag,true);
        }

        //时间
        if (!TextUtils.isEmpty(messageReadedEntity.getNiceDate())) {
            baseViewHolder.setText(R.id.home_item_nicedata,messageReadedEntity.getNiceDate());
        } else {
            baseViewHolder.setText(R.id.home_item_nicedata,"");
        }

        //标题
        if (!TextUtils.isEmpty(messageReadedEntity.getTitle())) {
            baseViewHolder.setText(R.id.message_tv_title,messageReadedEntity.getTitle());
        } else {
            baseViewHolder.setText(R.id.message_tv_title,"");
        }

        //描述
        if (!TextUtils.isEmpty(messageReadedEntity.getMessage())) {
            baseViewHolder.setText(R.id.message_tv_desc,messageReadedEntity.getMessage());
        } else {
            baseViewHolder.setText(R.id.message_tv_desc,"");
        }


    }
}
