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

-keep class com.kakaotech.team25.di.** { *; }
-keep class com.kakaotech.team25.data.** { *; }

# Keep Dagger Hilt classes and annotations
-keep class dagger.hilt.** { *; }

-keepclassmembers class * {
    @dagger.hilt.android.qualifiers.ApplicationContext *;
    @dagger.hilt.android.lifecycle.HiltViewModel *;
}

# Keep Retrofit classes and annotations
-keep class retrofit2.** { *; }

# Keep attributes for Retrofit
-keepattributes Signature, Exceptions, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

-keep class com.squareup.okhttp3.** { *; }
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}
# Keep OkHttp Callback interface
-keep class okhttp3.Callback { *; }

# Keep AndroidX Lifecycle classes
-keep class androidx.lifecycle.ViewModel { *; }
-keep class androidx.lifecycle.LiveData { *; }

# Keep all classes extending Fragment
-keep class * extends androidx.fragment.app.Fragment { *; }

# Keep Gson classes and annotations
-keep class com.google.gson.** { *; }

-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class com.kakaotech.team25.domain.model.** { *; }

-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# kakao sdk
-keep class com.kakao.sdk.** { *; }
-keep class com.kakao.vectormap.** { *; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
-keep class com.kakao.vectormap.** { *; }

-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep,allowobfuscation,allowshrinking class kotlinx.coroutines.flow.Flow
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite* {
   <fields>;
}

-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
