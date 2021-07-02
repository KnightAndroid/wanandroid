package com.knight.wanandroid.module_home.module_activity;

import android.os.Bundle;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_widget.flowlayout.TagInfo;
import com.knight.wanandroid.library_widget.flowlayout.listener.OnTagClickListener;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeLabelActivityBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/2 16:09
 * @descript:
 */


public class KnowledgeLabelActivity extends BaseDBActivity<HomeLabelActivityBinding> {



    private List<String> mDataList;

    //当前选择的id
    private String currentId;

    private boolean isEdit;

    private ArrayList<TagInfo> myTagInfos = new ArrayList<>();
    @Override
    public int layoutId() {
        return R.layout.home_label_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        mDatabind.homeIncludeTitle.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.homeIncludeTitle.baseTvTitle.setText(R.string.home_knowledge_label);
        mDataList = (List<String>)getIntent().getSerializableExtra("data");
        currentId = getIntent().getStringExtra("currentId");
        if (currentId == null) {
            currentId = "-1";
        }
        mDatabind.homeKnowledgetTag.setSelectTagId(currentId);
        mDatabind.homeKnowledgetTag.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(TagInfo tagInfo) {

            }

            @Override
            public void onTagDelete(TagInfo tagInfo) {
                //删除标签
            }
        });
        initData();
    }


    @Override
    public void initData(){
        myTagInfos.addAll(addTags("default",mDataList,TagInfo.TYPE_TAG_USER));
        mDatabind.homeKnowledgetTag.setTags(myTagInfos);
    }

    public List<TagInfo> addTags(String tagId, List<String> dataList, int type) {
        List<TagInfo> list = new ArrayList<>();
        TagInfo tagInfo;
        String name;
        int labelSize = dataList.size();
        if (dataList != null && labelSize > 0) {
            for (int i = 0; i < labelSize; i++) {
                name = dataList.get(i);
                tagInfo = new TagInfo();
                tagInfo.type = type;
                tagInfo.tagName = name;
                tagInfo.tagId = tagId + i;
                list.add(tagInfo);
            }
        }
        return list;
    }


    public class ProxyClick {
        public void editLabel() {
            if (mDatabind.homeLabelEdit.getText().toString().equals(getString(R.string.home_edit))) {
                isEdit = true;
                mDatabind.homeLabelEdit.setText(R.string.home_save);
                initTagDrag();
            } else {
                isEdit = false;
                mDatabind.homeLabelEdit.setText(R.string.home_edit);
                initTagDefault();
            }
        }
    }

    private void initTagDrag() {
        mDatabind.homeKnowledgetTag.enableDragAndDrop();
        mDatabind.homeKnowledgetTag.setIsEdit(true);
    }
    private void initTagDefault() {
        mDatabind.homeKnowledgetTag.setDefault();
        mDatabind.homeKnowledgetTag.setIsEdit(false);
    }

}
