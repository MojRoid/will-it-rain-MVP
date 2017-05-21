# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/moj/Library/Android/sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# Retrofit 2 #######################################################################################

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# okio #############################################################################################

-dontwarn okio.**

# okhttp 3 #########################################################################################

-keep class com.squareup.okhttp3.** {
*;
}

# RxJava 2 #########################################################################################

-dontwarn javax.annotation.**
-keep class javax.annotation.** { *; }

# Joda Time ########################################################################################

-dontwarn org.joda.time.**
-dontwarn org.joda.convert.**
-keep class org.joda.time.** { *; }
-keep class org.joda.convert.** { *; }
-keep interface org.joda.time.** { *; }

####################################################################################################
