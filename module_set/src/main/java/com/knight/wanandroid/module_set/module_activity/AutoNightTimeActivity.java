package com.knight.wanandroid.module_set.module_activity;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TimePicker;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.databinding.SetAutonighttimeActivityBinding;

import java.util.Calendar;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/12/8 16:04
 * @descript:设置自动夜间切换时间
 */

@Route(path = RoutePathActivity.Set.Set_AutoNightMode)
public final class AutoNightTimeActivity extends BaseDBActivity<SetAutonighttimeActivityBinding> {

    private Calendar mCalendar;

    private String temphourOfNight  = CacheUtils.getStartNightModeHour();
    private String tempminuterNight = CacheUtils.getStartNightModeMinuter();
    private String temphourOfDay = CacheUtils.getStartDayModeHour();
    private String tempminuterDay = CacheUtils.getStartDayModeMinuter();

    @Override
    public int layoutId() {
        return R.layout.set_autonighttime_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mCalendar = Calendar.getInstance();
        mDatabind.setClick(new ProxyClick());
        mDatabind.includeAutoNightToolbar.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.includeAutoNightToolbar.baseTvTitle.setText(getString(R.string.set_night_mode));
        mDatabind.includeAutoNightToolbar.baseTvRight.setVisibility(View.VISIBLE);
        mDatabind.includeAutoNightToolbar.baseTvRight.setText(getString(R.string.base_save));
        mDatabind.includeAutoNightToolbar.baseTvRight.setOnClickListener(v -> saveAutoNightTime());
        if (CacheUtils.getAutoNightMode()) {
            mDatabind.setCbNightMode.setChecked(true);
            mDatabind.setStartDayTime.setVisibility(View.VISIBLE);
            mDatabind.setStartNightTime.setVisibility(View.VISIBLE);
        } else {
            mDatabind.setCbNightMode.setChecked(false);
        }
        mDatabind.setCbNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDatabind.setStartDayTime.setVisibility(View.VISIBLE);
                    mDatabind.setStartNightTime.setVisibility(View.VISIBLE);
                } else {
                    mDatabind.setStartDayTime.setVisibility(View.GONE);
                    mDatabind.setStartNightTime.setVisibility(View.GONE);
                }
            }
        });
        mDatabind.setNightTimeValue.setText(temphourOfNight + ":" + tempminuterNight);
        mDatabind.setDayTimeValue.setText(temphourOfDay + ":" + tempminuterDay);
    }


    public class ProxyClick {
        /**
         * 设置晚间模式时间
         */
        public void setNightTime() {

            TimePickerDialog nightDialog = new TimePickerDialog(AutoNightTimeActivity.this, R.style.dialog_time_style, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    temphourOfNight = hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay);
                    tempminuterNight = minute < 10 ? "0" + minute : String.valueOf(minute);

                    mDatabind.setNightTimeValue.setText(temphourOfNight + ":" + tempminuterNight);
                }
            }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
            nightDialog.show();
        }

        /**
         * 设置白天模式时间
         */
        public void setDayTime() {
            TimePickerDialog dayDialog = new TimePickerDialog(AutoNightTimeActivity.this, R.style.dialog_time_style, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    temphourOfDay = hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay);
                    tempminuterDay = minute < 10 ? "0" + minute : String.valueOf(minute);

                    mDatabind.setDayTimeValue.setText(temphourOfDay + ":" + tempminuterDay);
                }
            }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
            dayDialog.show();
        }
    }


    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    /**
     * 保存自动
     * 设置夜间模式切换时间
     */
    private void saveAutoNightTime() {
        if (mDatabind.setCbNightMode.isChecked()) {
            CacheUtils.setOpenAutoNightMode(true);
            CacheUtils.setStartNightModeHour(temphourOfNight);
            CacheUtils.setStartNightModeMinuter(tempminuterNight);
            CacheUtils.setStartDayModeHour(temphourOfDay);
            CacheUtils.setStartDayModeMinuter(tempminuterDay);
        } else {
            CacheUtils.setOpenAutoNightMode(false);
        }
        ToastUtils.show(R.string.set_nightauto_mode_success);
        finish();
    }
}
