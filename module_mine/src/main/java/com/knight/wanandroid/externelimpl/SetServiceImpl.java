package com.knight.wanandroid.externelimpl;

import com.google.auto.service.AutoService;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.module_set.module_external.MineExternalContact;


/**
 * Author:Knight
 * Time:2023/2/9 16:35
 * Description:SetServiceImpl
 */
@AutoService(MineExternalContact.class)
public class SetServiceImpl implements MineExternalContact {
    @Override
    public String getUserRank() {
        return CacheUtils.getUserRank();
    }
}
