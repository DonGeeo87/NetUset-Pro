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

-keepattributes *Annotation*

# Gson: keep fields annotated with @SerializedName to avoid removal/rename
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Retrofit/OkHttp: keep interfaces and avoid warnings for reflection
-keepattributes Signature
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# Coroutines: keep debug metadata to preserve readable stacktraces
-keepclassmembers class kotlinx.coroutines.** {
    @kotlinx.coroutines.DebugMetadata *;
}

# Compose usually needs no extra rules; avoid blanket -dontobfuscate