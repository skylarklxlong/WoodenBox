# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/double/SoftWare/android-studio-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# 在prguard-rules.pro文件中写的，其实就是混淆规则，规定哪些东西不需要混淆。
#自己编写的代码中大致就是一些重要的类需要混淆，而混淆的本质就是精简类名，用简单的a,b,c等单词来代替之前写的如DataUtil等易懂的类名。
#所以，理解了这点，也就好理解这个混淆文件该怎么写了，大致思路就是：
#不混淆第三方库，不混淆系统组件，一般也可以不混淆Bean等模型类，因为这些对别人都是没用的，毕竟都是开源的。。。



#混淆配置设定
-optimizationpasses 5 #指定代码压缩级别
-dontusemixedcaseclassnames #混淆时不会产生形形色色的类名
-dontskipnonpubliclibraryclasses #指定不忽略非公共类库
-dontpreverify #不预校验，如果需要预校验，是-dontoptimize
-ignorewarnings #屏蔽警告
-verbose #混淆时记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* #优化



#-不需要混淆第三方类库
-keep class org.apache.**{*;} #过滤commons-httpclient-3.1.jar
# 去掉与 leakcanary jar包相关的
-dontnote com.squareup.leakcanary.**
-keep class com.squareup.leakcanary.** {*;}
# 去掉与 gson jar包相关的
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
-keepattributes EnclosingMethod
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.gson.** { *;}

# 去掉与 butterknife jar包相关的
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keep class **$$ViewInjector {*;} #就是这里没有添加，导致我的整个程序出错（因为我在程序中使用的是InjectView而不是BindView）
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# 去掉与 circleimageview jar包相关的
-dontnote de.hdodenhof.circleimageview.**
-keep class de.hdodenhof.circleimageview.** {*;}

# 去掉与 stetho jar包相关的
-dontnote com.facebook.stetho.**
-keep class com.facebook.stetho.** {*;}

# 去掉与 stetho-okhttp3 jar包相关的
-dontnote com.facebook.stetho.okhttp3.**
-keep class com.facebook.stetho.okhttp3.** {*;}

# 去掉与 okhttp3 jar包相关的
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

# Picasso
-dontwarn com.squareup.okhttp.**

# 去掉与 okio jar包相关的
-dontnote okio.**
-keep class okio.** {*;}

# 去掉与 retrofit2 jar包相关的
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# 去掉与 RxJava  jar包相关的
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}

# 去掉与 htmlcleaner jar包相关的
-dontnote org.htmlcleaner.**
-keep class org.htmlcleaner.** {*;}

# 去掉与 dragphotoview jar包相关的
-dontnote com.wingsofts.dragphotoview.**
-keep class com.wingsofts.dragphotoview.** {*;}

# umeng
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keep class com.umeng.** { *; }
-keep public class * extends com.umeng.**

# 去掉与 lite-orm jar包相关的
-dontnote com.litesuits.orm.**
-keep class com.litesuits.orm.** {*;}

# 去掉与 cssparser jar包相关的
-dontnote com.osbcp.cssparser.**
-keep class com.osbcp.cssparser.** {*;}

# 去掉与 jpush jar包相关的
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
# 去掉与 com.yanzhenjie:permission jar包相关的
-keepclassmembers class ** {
    @com.yanzhenjie.permission.PermissionYes <methods>;
}
-keepclassmembers class ** {
    @com.yanzhenjie.permission.PermissionNo <methods>;
}







#不需要混淆系统组件等
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-dontwarn android.support.v4.** #去掉警告
-keep class android.support.v4.** { *; } #过滤android.support.v4
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keep class android.support.** { *; }
-keep interface android.support.** { *; }

#保护指定的类和类的成员，但条件是所有指定的类和类成员是要存在
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.support.v7.app.AppCompatActivity {
public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepattributes Signature
-keep class **.R$* {*;}
-ignorewarnings

-verbose
-keepclasseswithmembernames class * {
    native <methods>;
}


# Remove logging calls
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# cardview
# http://stackoverflow.com/questions/29679177/cardview-shadow-not-appearing-in-lollipop-after-obfuscate-with-proguard/29698051
-keep class android.support.v7.widget.RoundRectDrawable { *; }

#-自己编写的类的操作
#这句非常重要，主要是滤掉 com.demo.demo.bean包下的所有.class文件不进行混淆编译,com.demo.demo是你的包名
-keep class online.himakeit.skylark.model.** {*;}
-keep class online.himakeit.skylark.view.** {*;}
-keep class online.himakeit.skylark.widget.** {*;}
-keep class online.himakeit.skylark.api.** {*;}
-keep class net.nightwhistler.htmlspanner.** {*;} #过滤掉自己编写的实体类

# 过滤R文件的混淆：
-keep class **.R$* {*;}
-keep public class online.himakeit.skylark.R$*{
		public static final int *;
}
-keep class online.himakeit.skylark.BuildConfig { *; }
-keep public class * extends android.os.Binder