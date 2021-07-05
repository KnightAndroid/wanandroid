package com.knight.wanandroid.module_home.module_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_widget.flowlayout.TagInfo;
import com.knight.wanandroid.library_widget.flowlayout.listener.OnTagClickListener;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeLabelActivityBinding;
import com.knight.wanandroid.module_home.module_adapter.MoreKnowLedgeAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/2 16:09
 * @descript:
 */


public class KnowledgeLabelActivity extends BaseDBActivity<HomeLabelActivityBinding> {


    //随意增删标签列表
    private List<String> mDataList;
    //固定标签列表
    private List<String> fixDataList = new ArrayList<>();

    //当前选择的id
    private String currentId;

    private boolean isEdit;

    public static final int QUESTCODE = 0x002;
    public static final String DATA_KEY = "data";

    private ArrayList<TagInfo> myTagInfos = new ArrayList<>();
    private FlexboxLayoutManager mManager;
    private MoreKnowLedgeAdapter mMoreKnowLedgeAdapter;
    private List<String> moreKnowLedgeList;
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
        fixDataList.add(mDataList.get(0));
        mDataList.remove(0);
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
        myTagInfos.addAll(addTags("fix",fixDataList,TagInfo.TYPE_TAG_SERVICE));
        myTagInfos.addAll(addTags("default",mDataList,TagInfo.TYPE_TAG_USER));
        mDatabind.homeKnowledgetTag.setTags(myTagInfos);

        mManager = new FlexboxLayoutManager(this);
        mManager.setFlexDirection(FlexDirection.ROW);
        //左对齐
        mManager.setJustifyContent(JustifyContent.FLEX_START);
        mManager.setAlignItems(AlignItems.CENTER);
        mDatabind.homeMoreknowledgeRv.setLayoutManager(mManager);

        String[] tagsMoreKnowledge = getResources().getStringArray(R.array.home_more_knowledge_name);
        moreKnowLedgeList = Arrays.asList(tagsMoreKnowledge);
        mMoreKnowLedgeAdapter = new MoreKnowLedgeAdapter(moreKnowLedgeList);
        mDatabind.homeMoreknowledgeRv.setAdapter(mMoreKnowLedgeAdapter);
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
                //保存到mmkv
                List <String> mKnowledgeLabelList = new ArrayList<>();
                for (int i = 0; i< mDatabind.homeKnowledgetTag.getTagInfos().size();i++) {
                    mKnowledgeLabelList.add(mDatabind.homeKnowledgetTag.getTagInfos().get(i).tagName);
                }
                CacheUtils.getInstance().saveDataInfo("knowledgeLabel",mKnowledgeLabelList);
                initTagDefault();
                Intent intent = new Intent();
                intent.putExtra(DATA_KEY,(Serializable)mKnowledgeLabelList);
                setResult(Activity.RESULT_OK,intent);
                finish();
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
