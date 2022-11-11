# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/sidhi/software/android-sdk-linux/tools/proguard/proguard-android.txt
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
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

#keep all classes that might be used in XML layouts
-keep public class * extends android.view.View
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.Fragment

#keep all classes
-keep public class *{
    public protected *;
}

#keep all public and protected methods that could be used by java reflection
-keepclassmembernames class * {
public protected <methods>;
}

-keepclasseswithmembernames class * {
native <methods>;
}

-keepclasseswithmembernames class * {
public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

-dontwarn **CompatHoneycomb
-dontwarn org.htmlcleaner.*

#Crashlytic
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

#Picasso
-dontwarn com.squareup.okhttp.**

# ignore androidannotations depend 3rd jars warnings
-dontwarn org.androidannotations.**

-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-keepclassmembers,includedescriptorclasses class ** { public void onEvent*(**); }

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

-keep class com.ekades.ruangmuslim.models.** { *; }
-keep class com.ekades.ruangmuslim.entities.** { *; }
-keep class com.ekades.ruangmuslim.network.responses.** { *; }
-keep class com.ekades.ruangmuslim.networks.responses.** { *; }

-keep class com.ekades.template.entities.** { *; }
-keep class com.ekades.template.network.entities.** { *; }
-keep class com.ekades.template.network.responses.** { *; }

-keep class com.ekades.ruangmuslim.lib.core.network.responses.entity.** { *; }

##---------------End: proguard configuration for Gson  ----------

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn org.apache.commons.**
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**

-keepclassmembers class com.sothree.slidinguppanel.** {
*;
}

#Start Adjust setting
-keep class com.adjust.sdk.plugin.MacAddressUtil { java.lang.String getMacAddress(android.content.Context); }
-keep class com.adjust.sdk.plugin.AndroidIdUtil { java.lang.String getAndroidId(android.content.Context); }

-keep class com.google.android.gms.common.ConnectionResult {
    int SUCCESS;
}

-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
    com.google.android.gms.ads.identifier.AdvertisingIdClient.Info getAdvertisingIdInfo (android.content.Context);
}

-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient.Info {
    java.lang.String getId ();
    boolean isLimitAdTrackingEnabled();
}
#End Adjust setting

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}
-dontwarn okio.**
## One Signal
-dontwarn com.onesignal.**
-dontwarn com.iarcuschin.**

-keep class com.google.android.gms.common.api.GoogleApiClient {
    void connect();
    void disconnect();
}

-keep public interface android.app.OnActivityPausedListener {*;}
-keep class com.iarcuschin.**{*;}

-keep class com.onesignal.shortcutbadger.impl.AdwHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.ApexHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.AsusHomeLauncher { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.DefaultBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.HuaweiHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.NewHtcHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.NovaHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.SolidHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.SonyHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.XiaomiHomeBadger { <init>(...); }

-keep class com.wang.avi.**{*;}
-keep class com.wang.avi.indicators.**{*;}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class com.sinch.** { *; }
-keep interface com.sinch.** { *; }
-keep class org.webrtc.** { *; }

-dontwarn com.google.**

# support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
# support-design
-dontwarn android.support.design.**
-dontwarn com.bumptech.glide.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

# Fuel
-keep class com.github.kittinunf.fuel.android.util.AndroidEnvironment

-ignorewarnings

-keep class org.apache.commons.** { *; }

# sdk MoEngange
-dontwarn com.google.android.gms.location.**
-keep class com.google.android.gms.location.** { *; }

-keep class com.moe.pushlibrary.activities.** { *; }
-keep class com.moe.pushlibrary.MoEHelper
-keep class com.moengage.locationlibrary.GeofenceIntentService
-keep class com.moe.pushlibrary.InstallReceiver
-keep class com.moe.pushlibrary.providers.MoEProvider
-keep class com.moe.pushlibrary.models.** { *;}
-keep class com.moengage.core.GeoTask
-keep class com.moengage.location.GeoManager
-keep class com.moengage.inapp.InAppManager
-keep class com.moengage.push.PushManager
-keep class com.moengage.inapp.InAppController
-keep class com.moe.pushlibrary.AppUpdateReceiver
-keep class com.moengage.core.MoEAlarmReceiver
-keep class com.moengage.core.MoEngage

# Push
-keep class com.moengage.pushbase.activities.PushTracker
-keep class com.moengage.pushbase.activities.SnoozeTracker
-keep class com.moengage.pushbase.push.MoEPushWorker
-keep class com.moe.pushlibrary.MoEWorker

# Real Time Triggers
-keep class com.moengage.addon.trigger.DTHandlerImpl
-keep class com.moengage.core.MoEDTManager
-keep class com.moengage.core.MoEDTManager.DTHandler

# Push Amplification
-keep class com.moengage.addon.messaging.MessagingHandlerImpl
-keep class com.moengage.push.MoEMessagingManager
-keep class com.moengage.addon.messaging.MoEMessageSyncJob
-keep class com.moengage.addon.messaging.MoEMessageSyncReceiver
-keep class com.moengage.addon.messaging.MoEMessageSyncIntentService

-keep class com.ekades.ruangmuslim.networksV2.prayerschedule.data.dataSource.response.** { *; }
-keep class com.ekades.ruangmuslim.networksV2.quran.data.dataSource.response.** { *; }

-keep class com.ekades.ruangmuslim.features.main.model.** { *; }
-keep class com.ekades.ruangmuslim.features.prayerdetail.model.** { *; }
-keep class com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.model.** { *; }
-keep class com.ekades.ruangmuslim.features.prayerschedule.searchcity.model.** { *; }
-keep class com.ekades.ruangmuslim.features.qurandetail.model.** { *; }
-keep class com.ekades.ruangmuslim.features.quranlist.model.** { *; }

-dontwarn com.moengage.location.GeoManager
-dontwarn com.moengage.core.GeoTask
-dontwarn com.moengage.receiver.*
-dontwarn com.moengage.worker.*
-dontwarn com.moengage.inapp.ViewEngine

#SendBird app
-dontwarn com.sendbird.android.shadow.**

-keep class com.delight.**  { *; }