# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\USER\AppData\Local\Android\Sdk\tools\proguard\proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If you use reflection to access classes in your project, add the
# appropriate -keep options here. Be sure to review the ProGuard manual
# for a complete list of options.
-keep class com.google.android.gms.common.** { *; }
-keep class com.google.android.gms.ads.identifier.** { *; }
-keep class com.google.android.gms.appset.** { *; }
-keep class com.google.firebase.analytics.** { *; }
-keep public class * extends java.lang.Exception
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn com.google.android.material.**
-dontwarn org.conscrypt.**
-dontwarn com.google.common.**
-dontwarn okio.**
