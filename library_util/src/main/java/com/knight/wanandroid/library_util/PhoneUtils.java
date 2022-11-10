package com.knight.wanandroid.library_util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.RequiresPermission;

import com.knight.wanandroid.library_common.provider.ApplicationProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author:Knight
 * Time:2022/11/10 17:37
 * Description:PhoneUtils
 */
public class PhoneUtils {

    /**
     *
     * 获取DeviceId
     * @return
     */
    public static String getDeviceId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return "";
        }
        TelephonyManager tm = getTelephonyManager();
        String deviceId = tm.getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) return deviceId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String imei = tm.getImei();
            if (!TextUtils.isEmpty(imei)) return imei;
            String meid = tm.getMeid();
            return TextUtils.isEmpty(meid) ? "" : meid;
        }
        return "";
    }


    @SuppressLint("HardwareIds")
    public static String getSerial() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                return Build.getSerial();
            } catch (SecurityException e) {
                e.printStackTrace();
                return "";
            }
        }
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? Build.getSerial() : Build.SERIAL;
    }

    public static String getIMEI() {
        return getImeiOrMeid(true);
    }

    public static String getImeiOrMeid(boolean isImei) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return "";
        }
        TelephonyManager tm = getTelephonyManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (isImei) {
                return getMinOne(tm.getImei(0), tm.getImei(1));
            } else {
                return getMinOne(tm.getMeid(0), tm.getMeid(1));
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String ids = getSystemPropertyByReflect(isImei ? "ril.gsm.imei" : "ril.cdma.meid");
            if (!TextUtils.isEmpty(ids)) {
                String[] idArr = ids.split(",");
                if (idArr.length == 2) {
                    return getMinOne(idArr[0], idArr[1]);
                } else {
                    return idArr[0];
                }
            }

            String id0 = tm.getDeviceId();
            String id1 = "";
            try {
                Method method = tm.getClass().getMethod("getDeviceId", int.class);
                id1 = (String) method.invoke(tm,
                        isImei ? TelephonyManager.PHONE_TYPE_GSM
                                : TelephonyManager.PHONE_TYPE_CDMA);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (isImei) {
                if (id0 != null && id0.length() < 15) {
                    id0 = "";
                }
                if (id1 != null && id1.length() < 15) {
                    id1 = "";
                }
            } else {
                if (id0 != null && id0.length() == 14) {
                    id0 = "";
                }
                if (id1 != null && id1.length() == 14) {
                    id1 = "";
                }
            }
            return getMinOne(id0, id1);
        } else {
            String deviceId = tm.getDeviceId();
            if (isImei) {
                if (deviceId != null && deviceId.length() >= 15) {
                    return deviceId;
                }
            } else {
                if (deviceId != null && deviceId.length() == 14) {
                    return deviceId;
                }
            }
        }
        return "";
    }

    private static String getSystemPropertyByReflect(String key) {
        try {
            @SuppressLint("PrivateApi")
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method getMethod = clz.getMethod("get", String.class, String.class);
            return (String) getMethod.invoke(clz, key, "");
        } catch (Exception e) {/**/}
        return "";
    }
    private static String getMinOne(String s0, String s1) {
        boolean empty0 = TextUtils.isEmpty(s0);
        boolean empty1 = TextUtils.isEmpty(s1);
        if (empty0 && empty1) return "";
        if (!empty0 && !empty1) {
            if (s0.compareTo(s1) <= 0) {
                return s0;
            } else {
                return s1;
            }
        }
        if (!empty0) return s0;
        return s1;
    }

    public static String getIMSI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                getTelephonyManager().getSubscriberId();
            } catch (SecurityException e) {
                e.printStackTrace();
                return "";
            }
        }
        return getTelephonyManager().getSubscriberId();
    }

    public static int getPhoneType() {
        TelephonyManager tm = getTelephonyManager();
        return tm.getPhoneType();
    }

    public static String getSimOperatorByMnc() {
        TelephonyManager tm = getTelephonyManager();
        String operator = tm.getSimOperator();
        if (operator == null) return "";
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
            case "46020":
                return "中国移动";
            case "46001":
            case "46006":
            case "46009":
                return "中国联通";
            case "46003":
            case "46005":
            case "46011":
                return "中国电信";
            default:
                return operator;
        }
    }
    private static TelephonyManager getTelephonyManager() {
        return (TelephonyManager) ApplicationProvider.getInstance().getApplication().getSystemService(Context.TELEPHONY_SERVICE);
    }

}
