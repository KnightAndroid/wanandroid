package com.knight.wanandroid.library_base.interceptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_permiss.IPermissionInterceptor;
import com.knight.wanandroid.library_permiss.OnPermissionCallback;
import com.knight.wanandroid.library_permiss.Permission;
import com.knight.wanandroid.library_permiss.XXPermissions;
import com.knight.wanandroid.library_util.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/6 17:38
 * @descript:申请权限拦截器
 */
public final class PermissionInterceptor implements IPermissionInterceptor {


    @Override
    public void grantedPermissions(FragmentActivity activity,  List<String> allPermissions, List<String> grantedPermissions,
                                   boolean all, OnPermissionCallback callback) {
        if (callback != null) {
            callback.onGranted(grantedPermissions, all);
        }
    }

    @Override
    public void deniedPermissions(FragmentActivity activity,  List<String> allPermissions, List<String> deniedPermissions,
                                  boolean never, OnPermissionCallback callback) {
        if (callback != null) {
            callback.onDenied(deniedPermissions, never);
        }

        if (never) {
            showPermissionDialog(activity, deniedPermissions);
            return;
        }

        if (deniedPermissions.size() == 1 && Permission.ACCESS_BACKGROUND_LOCATION.equals(deniedPermissions.get(0))) {
            ToastUtils.show(R.string.base_permission_fail_four);
            return;
        }
        if (callback == null) {
            return;
        }

        ToastUtils.show(R.string.base_permission_fail_one);
    }


    /**
     *
     * 显示授权对话框
     * @param activity
     * @param permissions
     */
    protected void showPermissionDialog(FragmentActivity activity,List<String> permissions) {
        new AlertDialog.Builder(activity)
                .setTitle(R.string.base_permission_alert)
                .setCancelable(false)
                .setMessage(getPermissionHint(activity,permissions))
                .setPositiveButton(R.string.base_permission_goto, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        XXPermissions.startPermissionActivity(activity,permissions);
                    }
                })
                .show();
    }


    /**
     *
     * 根据权限获取提示
     * @param context
     * @param permissions
     * @return
     */
    protected String getPermissionHint(Context context,List<String> permissions) {
        if(permissions == null || permissions.isEmpty()) {
            return context.getString(R.string.base_permission_fail_two);
        }

        List<String> hints = new ArrayList<>();
        for (String permission : permissions) {
            switch (permission) {
                case Permission.READ_EXTERNAL_STORAGE:
                case Permission.WRITE_EXTERNAL_STORAGE:
                case Permission.MANAGE_EXTERNAL_STORAGE: {
                    String hint = context.getString(R.string.base_permission_storage);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }

                case Permission.CAMERA: {
                    String hint = context.getString(R.string.base_permission_camera);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }

                case Permission.RECORD_AUDIO: {
                    String hint = context.getString(R.string.base_permission_microphone);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }

                case Permission.ACCESS_FINE_LOCATION:
                case Permission.ACCESS_COARSE_LOCATION:
                case Permission.ACCESS_BACKGROUND_LOCATION: {
                    String hint;
                    if (!permissions.contains(Permission.ACCESS_FINE_LOCATION)
                       && !permissions.contains(Permission.ACCESS_COARSE_LOCATION)) {
                        hint = context.getString(R.string.base_permission_location_background);
                    } else {
                        hint = context.getString(R.string.base_permission_location);
                    }

                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }

                case Permission.BLUETOOTH_SCAN:
                case Permission.BLUETOOTH_CONNECT:
                case Permission.BLUETOOTH_ADVERTISE: {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        String hint = context.getString(R.string.base_permission_bluetooth);
                        if (!hints.contains(hint)) {
                            hints.add(hint);
                        }
                    }
                    break;
                }

                case Permission.READ_PHONE_STATE:
                case Permission.CALL_PHONE:
                case Permission.ADD_VOICEMAIL:
                case Permission.USE_SIP:
                case Permission.READ_PHONE_NUMBERS:
                case Permission.ANSWER_PHONE_CALLS: {
                    String hint = context.getString(R.string.base_permission_phone);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }

                case Permission.GET_ACCOUNTS:
                case Permission.READ_CONTACTS:
                case Permission.WRITE_CONTACTS: {
                    String hint = context.getString(R.string.base_permisssion_contacts);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }

                case Permission.READ_CALENDAR:
                case Permission.WRITE_CALENDAR: {
                     String hint = context.getString(R.string.base_permission_calendar);
                     if (!hints.contains(hint)) {
                         hints.add(hint);
                     }

                     break;
                }

                case Permission.READ_CALL_LOG:
                case Permission.WRITE_CALL_LOG:
                case Permission.PROCESS_OUTGOING_CALLS:{
                    String hint = context.getString(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ?
                            R.string.base_permission_call_log : R.string.base_permission_phone);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }

                case Permission.BODY_SENSORS: {
                    String hint = context.getString(R.string.base_permission_sensors);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }

                case Permission.ACTIVITY_RECOGNITION: {
                    String hint = context.getString(R.string.base_permission_activity_recognition);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permission.SEND_SMS:
                case Permission.RECEIVE_SMS:
                case Permission.READ_SMS:
                case Permission.RECEIVE_WAP_PUSH:
                case Permission.RECEIVE_MMS: {
                    String hint = context.getString(R.string.base_permission_sms);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }

                case Permission.REQUEST_INSTALL_PACKAGES: {
                    String hint = context.getString(R.string.base_permission_install);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permission.NOTIFICATION_SERVICE: {
                    String hint = context.getString(R.string.base_permission_notification);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permission.SYSTEM_ALERT_WINDOW: {
                    String hint = context.getString(R.string.base_permission_window);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permission.WRITE_SETTINGS: {
                    String hint = context.getString(R.string.base_permission_setting);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }

                case Permission.PACKAGE_USAGE_STATS: {
                    String hint = context.getString(R.string.base_permission_task);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                default:
                    break;


            }

        }

        if (!hints.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String text : hints) {
                if (builder.length() == 0) {
                    builder.append(text);
                } else {
                    builder.append("、").append(text);
                }
            }

            builder.append(" ");
            return context.getString(R.string.base_permission_fail_three,builder.toString());
        }
        return context.getString(R.string.base_permission_fail_two);

    }
}
