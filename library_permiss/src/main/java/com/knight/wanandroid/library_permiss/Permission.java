package com.knight.wanandroid.library_permiss;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/5 18:15
 * @descript:
 */
public final class Permission {

    private Permission() {}

    /**
     * 文件管理权限（特殊权限，需要 Android 11 及以上）
     *
     * 为了兼容 Android 11 以下版本，需要在清单文件中注册
     * {@link Permission#READ_EXTERNAL_STORAGE} 和 {@link Permission#WRITE_EXTERNAL_STORAGE} 权限
     *
     * 如果你的应用需要上架 GooglePlay，那么需要详细查看：https://support.google.com/googleplay/android-developer/answer/9956427
     */
    public static final String MANAGE_EXTERNAL_STORAGE = "android.permission.MANAGE_EXTERNAL_STORAGE";

    /**
     * 安装应用权限（特殊权限，需要 Android 8.0 及以上）
     *
     * Android 11 特性调整，安装外部来源应用需要重启 App：https://cloud.tencent.com/developer/news/637591
     * 经过实践，Android 12 已经修复了此问题，授权或者取消授权后应用并不会重启
     */
    public static final String REQUEST_INSTALL_PACKAGES = "android.permission.REQUEST_INSTALL_PACKAGES";

    /**
     * 悬浮窗权限（特殊权限）
     *
     * 在 Android 10 及之前的版本能跳转到应用悬浮窗设置页面，而在 Android 11 及之后的版本只能跳转到系统设置悬浮窗管理列表了
     * 官方解释：https://developer.android.google.cn/reference/android/provider/Settings#ACTION_MANAGE_OVERLAY_PERMISSION
     */
    public static final String SYSTEM_ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW";

    /** 系统设置权限（特殊权限） */
    public static final String WRITE_SETTINGS = "android.permission.WRITE_SETTINGS";

    /** 通知栏权限（特殊权限，注意此权限不需要在清单文件中注册也能申请） */
    public static final String NOTIFICATION_SERVICE = "android.permission.NOTIFICATION_SERVICE";

    /** 查看应用使用情况权限，简称读取包权限（特殊权限，Android 5.0 之后才有的权限） */
    public static final String PACKAGE_USAGE_STATS = "android.permission.PACKAGE_USAGE_STATS";

    /** 读取外部存储 */
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    /** 写入外部存储 */
    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";

    /** 相机权限 */
    public static final String CAMERA = "android.permission.CAMERA";

    /** 麦克风权限 */
    public static final String RECORD_AUDIO = "android.permission.RECORD_AUDIO";

    /** 获取精确位置 */
    public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    /** 获取粗略位置 */
    public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    /**
     * 在后台获取位置（需要 Android 10.0 及以上）
     *
     * 需要注意的是：如果你的 App 只在前台状态下使用定位功能，请不要申请该权限
     */
    public static final String ACCESS_BACKGROUND_LOCATION = "android.permission.ACCESS_BACKGROUND_LOCATION";

    /**
     * 蓝牙扫描权限（需要 Android 12.0 及以上）
     *
     * 为了兼容 Android 12 以下版本，需要清单文件中注册 {@link Manifest.permission#BLUETOOTH_ADMIN} 权限
     * 还有 Android 12 以下设备，获取蓝牙扫描结果需要模糊定位权限，框架会自动在旧的安卓设备上自动添加此权限进行动态申请
     */
    public static final String BLUETOOTH_SCAN = "android.permission.BLUETOOTH_SCAN";
    /**
     * 蓝牙连接权限（需要 Android 12.0 及以上）
     *
     * 为了兼容 Android 12 以下版本，需要在清单文件中注册 {@link Manifest.permission#BLUETOOTH} 权限
     */
    public static final String BLUETOOTH_CONNECT = "android.permission.BLUETOOTH_CONNECT";
    /**
     * 蓝牙广播权限（需要 Android 12.0 及以上）
     *
     * 将当前设备的蓝牙进行广播，供其他设备扫描时需要用到该权限
     * 为了兼容 Android 12 以下版本，需要在清单文件中注册 {@link Manifest.permission#BLUETOOTH_ADMIN} 权限
     */
    public static final String BLUETOOTH_ADVERTISE = "android.permission.BLUETOOTH_ADVERTISE";

    /** 读取联系人 */
    public static final String READ_CONTACTS = "android.permission.READ_CONTACTS";
    /** 修改联系人 */
    public static final String WRITE_CONTACTS = "android.permission.WRITE_CONTACTS";
    /** 访问账户列表 */
    public static final String GET_ACCOUNTS = "android.permission.GET_ACCOUNTS";

    /** 读取日历 */
    public static final String READ_CALENDAR = "android.permission.READ_CALENDAR";
    /** 修改日历 */
    public static final String WRITE_CALENDAR = "android.permission.WRITE_CALENDAR";

    /**
     * 读取照片中的地理位置（需要 Android 10.0 及以上）
     *
     * 需要注意的是：如果这个权限申请成功了但是不能正常读取照片的地理信息，那么需要先申请存储权限：
     *
     * 如果项目 targetSdkVersion <= 29 需要申请 {@link Permission.Group#STORAGE}
     * 如果项目 targetSdkVersion >= 30 需要申请 {@link Permission#MANAGE_EXTERNAL_STORAGE}
     */
    public static final String ACCESS_MEDIA_LOCATION = "android.permission.ACCESS_MEDIA_LOCATION";

    /**
     * 读取电话状态
     *
     * 需要注意的是：这个权限在某些手机上面是没办法获取到的，因为某些系统禁止应用获得该权限
     *             所以你要是申请了这个权限之后没有弹授权框，而是直接回调授权失败方法
     *             请不要惊慌，这个不是 Bug、不是 Bug、不是 Bug，而是正常现象
     *
     * 后续情况汇报：有人反馈在 iQOO 手机上面获取不到该权限，在清单文件加入下面这个权限就可以了（这里只是做记录，并不代表这种方式就一定有效果）
     *             <uses-permission android:name="android.permission.READ_PRIVILEGED_PHONE_STATE" />
     */
    public static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
    /** 拨打电话 */
    public static final String CALL_PHONE = "android.permission.CALL_PHONE";
    /** 读取通话记录 */
    public static final String READ_CALL_LOG = "android.permission.READ_CALL_LOG";
    /** 修改通话记录 */
    public static final String WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG";
    /** 添加语音邮件 */
    public static final String ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL";
    /** 使用SIP视频 */
    public static final String USE_SIP = "android.permission.USE_SIP";
    /**
     * 处理拨出电话
     *
     * @deprecated         在 Android 10 已经过时，请见：https://developer.android.google.cn/reference/android/Manifest.permission?hl=zh_cn#PROCESS_OUTGOING_CALLS
     */
    public static final String PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS";
    /**
     * 接听电话（需要 Android 8.0 及以上，Android 8.0 以下可以采用模拟耳机按键事件来实现接听电话，这种方式不需要权限）
     */
    public static final String ANSWER_PHONE_CALLS = "android.permission.ANSWER_PHONE_CALLS";
    /**
     * 读取手机号码（需要 Android 8.0 及以上）
     *
     * 为了兼容 Android 8.0 以下版本，需要在清单文件中注册 {@link Manifest.permission#READ_PHONE_STATE} 权限
     */
    public static final String READ_PHONE_NUMBERS = "android.permission.READ_PHONE_NUMBERS";

    /** 使用传感器 */
    public static final String BODY_SENSORS = "android.permission.BODY_SENSORS";
    /** 获取活动步数（需要 Android 10.0 及以上） */
    public static final String ACTIVITY_RECOGNITION = "android.permission.ACTIVITY_RECOGNITION";

    /** 发送短信 */
    public static final String SEND_SMS = "android.permission.SEND_SMS";
    /** 接收短信 */
    public static final String RECEIVE_SMS = "android.permission.RECEIVE_SMS";
    /** 读取短信 */
    public static final String READ_SMS = "android.permission.READ_SMS";
    /** 接收 WAP 推送消息 */
    public static final String RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH";
    /** 接收彩信 */
    public static final String RECEIVE_MMS = "android.permission.RECEIVE_MMS";

    /** 允许呼叫应用继续在另一个应用中启动的呼叫（需要 Android 9.0 及以上） */
    public static final String ACCEPT_HANDOVER = "android.permission.ACCEPT_HANDOVER";

    /**
     * 权限组
     */
    public static final class Group {

        /** 存储权限 */
        public static final String[] STORAGE = new String[] {
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE};

        /** 日历权限 */
        public static final String[] CALENDAR = new String[] {
                Permission.READ_CALENDAR,
                Permission.WRITE_CALENDAR};

        /** 联系人权限 */
        public static final String[] CONTACTS = new String[] {
                Permission.READ_CONTACTS,
                Permission.WRITE_CONTACTS,
                Permission.GET_ACCOUNTS};

        /** 传感器权限 */
        public static final String[] SENSORS = new String[] {
                Permission.BODY_SENSORS,
                Permission.ACTIVITY_RECOGNITION};

        /** 蓝牙权限 */
        public static final String[] BLUETOOTH = new String[] {
                Permission.BLUETOOTH_SCAN,
                Permission.BLUETOOTH_CONNECT,
                Permission.BLUETOOTH_ADVERTISE};
    }
}
