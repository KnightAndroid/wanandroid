# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5


-dontusemixedcaseclassnames

-dontskipnonpubliclibraryclasses

-dontskipnonpubliclibraryclassmembers

-dontpreverify

-verbose
-printmapping priguardMapping.txt

-optimizations !code/simplification/artithmetic,!field/*,!class/merging/*

################common###############
 #实体类不参与混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepnames class * implements java.io.Serializable
-keepattributes Signature
-keep class **.R$* {*;}
-ignorewarnings
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembernames class * { # 保持native方法不被混淆
    native <methods>;
}

-keepclassmembers enum * {  # 使用enum类型时需要注意避免以下两个方法混淆，因为enum类的特殊性，以下两个方法会被反射调用，
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

################support###############
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn android.support.**

-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

# 保留自定义控件(继承自View)不能被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(***);
    *** get* ();
}

# Aroute
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

-dontwarn com.kingja.loadsir.**
-keep class com.kingja.loadsir.** {*;}

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

-keep class com.wanandroid.knight.library_database.entity.**{*;}

################retrofit###############
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keep class com.knight.wanandroid.library_scan.entity.**{*;}


-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# And if you use AsyncExecutor:
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**

# 网络
-keep class com.knight.wanandroid.library_network.model.HttpData{*;}

# 解决mvp转换报错 Caused by: java.lang.ClassCastException
# base
-keep class com.knight.wanandroid.library_base.baseactivity.**{*;}
-keep class com.knight.wanandroid.library_base.basefragment.**{*;}
-keep class com.knight.wanandroid.library_base.entity.**{*;}

# home
-keep class com.knight.wanandroid.module_home.module_model.**{*;}
-keep class com.knight.wanandroid.module_home.module_request.**{*;}
-keep class com.knight.wanandroid.module_home.module_presenter.**{*;}

# message
-keep class com.knight.wanandroid.module_message.module_model.**{*;}
-keep class com.knight.wanandroid.module_message.module_request.**{*;}
-keep class com.knight.wanandroid.module_message.module_presenter.**{*;}

# hierachy
-keep class com.knight.wanandroid.module_hierachy.module_model.**{*;}
-keep class com.knight.wanandroid.module_hierachy.module_request.**{*;}
-keep class com.knight.wanandroid.module_hierachy.module_presenter.**{*;}

# mine
-keep class com.knight.wanandroid.module_mine.module_model.**{*;}
-keep class com.knight.wanandroid.module_mine.module_request.**{*;}
-keep class com.knight.wanandroid.module_mine.module_presenter.**{*;}

#project
-keep class com.knight.wanandroid.module_project.module_model.**{*;}
-keep class com.knight.wanandroid.module_project.module_request.**{*;}
-keep class com.knight.wanandroid.module_project.module_presenter.**{*;}

#square
-keep class com.knight.wanandroid.module_square.module_model.**{*;}
-keep class com.knight.wanandroid.module_square.module_request.**{*;}
-keep class com.knight.wanandroid.module_square.module_presenter.**{*;}

#web
-keep class com.knight.wanandroid.module_web.module_model.**{*;}
-keep class com.knight.wanandroid.module_web.module_presenter.**{*;}

#wechat
-keep class com.knight.wanandroid.module_wechat.module_model.**{*;}
-keep class com.knight.wanandroid.module_wechat.module_request.**{*;}
-keep class com.knight.wanandroid.module_wechat.module_presenter.**{*;}



# aop注解 解决混淆会把注解去掉
-keep @com.knight.wanandroid.library_aop.clickintercept.* class * {*;}
-keep @com.knight.wanandroid.library_aop.loginintercept.* class * {*;}
-keep class * {
    @com.knight.wanandroid.library_aop.clickintercept.* <fields>;
    @com.knight.wanandroid.library_aop.loginintercept.* <fields>;
}
-keepclassmembers class * {
    @com.knight.wanandroid.library_aop.clickintercept.* <methods>;
    @com.knight.wanandroid.library_aop.loginintercept.* <methods>;
}
