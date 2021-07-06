package com.knight.wanandroid.module_home.module_activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.reflect.TypeToken;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.flowlayout.TagInfo;
import com.knight.wanandroid.library_widget.flowlayout.listener.OnTagClickListener;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeLabelActivityBinding;
import com.knight.wanandroid.module_home.module_adapter.MoreKnowLedgeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/2 16:09
 * @descript:
 */

@Route(path = RoutePathActivity.Home.KnowLedgeLabel)
public class KnowledgeLabelActivity extends BaseDBActivity<HomeLabelActivityBinding> {


    //随意增删标签列表
    private List<String> mDataList;
    //固定标签列表
    private List<String> fixDataList = new ArrayList<>();

    //当前选择的id
    private String currentId;

    private boolean isEdit;

    private ArrayList<TagInfo> myTagInfos = new ArrayList<>();
    private FlexboxLayoutManager mManager;
    private MoreKnowLedgeAdapter mMoreKnowLedgeAdapter;
    private List<TagInfo> moreKnowLedgeList;
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
        mDatabind.homeTvMylabel.setText(getString(R.string.home_knowledge_label) +"("+mDataList.size()+"/10)");
        //固定标签
        fixDataList.add(mDataList.get(0));
        //把首个标签移除，其他就是可编辑标签
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
                //删除标签 回调这里
                mMoreKnowLedgeAdapter.getData().add(tagInfo);
                mMoreKnowLedgeAdapter.notifyItemInserted(mMoreKnowLedgeAdapter.getData().size() - 1);
                mDatabind.homeTvMylabel.setText(getString(R.string.home_knowledge_label) +"("+mDatabind.homeKnowledgetTag.getTagInfos().size()+"/10)");
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

        //首先读取本地是否有保存
        moreKnowLedgeList = CacheUtils.getInstance().getDataInfo("moreknowledgeLabel",new TypeToken<List<TagInfo>>(){}.getType());
        if (moreKnowLedgeList == null || moreKnowLedgeList.size() == 0) {
            moreKnowLedgeList = new ArrayList<>();
            String[] tagsMoreKnowledge = getResources().getStringArray(R.array.home_more_knowledge_name);
            moreKnowLedgeList.addAll(addTags("moreknowledge",Arrays.asList(tagsMoreKnowledge),TagInfo.TYPE_TAG_USER));
        }
        mMoreKnowLedgeAdapter = new MoreKnowLedgeAdapter(moreKnowLedgeList);
        mDatabind.homeMoreknowledgeRv.setAdapter(mMoreKnowLedgeAdapter);
        initLinstener();
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
                mMoreKnowLedgeAdapter.setIsEdit(true);
            } else {
                if (mDatabind.homeKnowledgetTag.getTagInfos().size() > 10) {
                    ToastUtils.getInstance().showToast(getString(R.string.home_moreenough_tips,10));
                } else {
                    isEdit = false;
                    mDatabind.homeLabelEdit.setText(R.string.home_edit);
                    mMoreKnowLedgeAdapter.setIsEdit(false);
                    //保存到mmkv
                    List <String> mKnowledgeLabelList = new ArrayList<>();
                    for (int i = 0; i< mDatabind.homeKnowledgetTag.getTagInfos().size();i++) {
                        mKnowledgeLabelList.add(mDatabind.homeKnowledgetTag.getTagInfos().get(i).tagName);
                    }
                    //保存我的标签
                    CacheUtils.getInstance().saveDataInfo("knowledgeLabel",mKnowledgeLabelList);
                    //保存更多标签
                    CacheUtils.getInstance().saveDataInfo("moreknowledgeLabel",mMoreKnowLedgeAdapter.getData());
                    initTagDefault();
                    EventBus.getDefault().post(new EventBusUtils.ChangeLabel(mKnowledgeLabelList));
                }

            }
        }


        /**
         *
         * 去往添加标签
         *
         */
        public void goAddKnowLedgeLabel(){
            if (mMoreKnowLedgeAdapter.getData().size() <= 10) {
                Intent intent = new Intent(KnowledgeLabelActivity.this,AddKnowLedgeLabelActivity.class);
                startActivityForResult(intent,AddKnowLedgeLabelActivity.QUESTCODE);
            } else {
                ToastUtils.getInstance().showToast(getString(R.string.home_moreenough_tips,10));
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


    /**
     *
     * 点击事件
     */
    private void initLinstener() {
        mMoreKnowLedgeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                mDatabind.homeKnowledgetTag.addTag(mMoreKnowLedgeAdapter.getData().get(position),isEdit);
                mDatabind.homeTvMylabel.setText(getString(R.string.home_knowledge_label) +"("+mDatabind.homeKnowledgetTag.getTagInfos().size()+"/10)");
                mMoreKnowLedgeAdapter.getData().remove(position);
                mMoreKnowLedgeAdapter.notifyItemRemoved(position);

            }
        });

        //更多
        mMoreKnowLedgeAdapter.addChildClickViewIds(R.id.home_iv_moreknowledge_delete);
        mMoreKnowLedgeAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.home_iv_moreknowledge_delete) {
                    //删除
                    mMoreKnowLedgeAdapter.getData().remove(position);
                    mMoreKnowLedgeAdapter.notifyItemRemoved(position);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == AddKnowLedgeLabelActivity.QUESTCODE && data != null){
                Bundle extras = data.getExtras();
                if (extras != null) {
                    String result = extras.getString(AddKnowLedgeLabelActivity.LABEL_DATA);
                    if (!TextUtils.isEmpty(result)) {
                        for (int i = 0; i < mMoreKnowLedgeAdapter.getData().size();i++) {
                            if (result.equals(mMoreKnowLedgeAdapter.getData().get(i).tagName)) {
                                ToastUtils.getInstance().showToast(getString(R.string.home_same_label_tip));
                                return;
                            }
                        }
                        TagInfo tagInfo = new TagInfo();
                        tagInfo.type = TagInfo.TYPE_TAG_USER;
                        tagInfo.tagName = result;
                        tagInfo.tagId = "moreknowledgeLabel" + new Random(1).nextInt(100);
                        mMoreKnowLedgeAdapter.getData().add(tagInfo);
                        mMoreKnowLedgeAdapter.notifyItemInserted(mMoreKnowLedgeAdapter.getData().size() - 1);

                    }
                }
            }
        }
    }

}
