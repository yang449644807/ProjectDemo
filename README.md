Oss阿里云上传已实现的一个简单Demo - 
相关AndroidSDK https://help.aliyun.com/document_detail/32047.html?spm=a2c4g.11174283.6.881.1b027da2NrKaxD

Android Studio 推荐依赖 

dependencies {
    compile 'com.aliyun.dpa:oss-android-sdk:+'
    
    compile 'com.squareup.okhttp3:okhttp:3.4.1'
    
    compile 'com.squareup.okio:okio:1.9.0'
}

权限 

<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />

混淆

-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**

两种模式 STS鉴权模式或自签名模式
自签名模式需要这四种变量

private String AccessId = "xxxxx";
private String AccessKey = "xxxx";
private String EndPoint = "xxxx";
private String Bucket = "dtbnews";
