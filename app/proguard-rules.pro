# We only need this configuration should we process the library as such,
# with build-processed.gradle, before it is integrated in an application.
-verbose
-dontoptimize
# With the default configuration dexguard-library-release-aggressive.pro,
# we need to explicitly keep all public API. This means that we can
# obfuscate the classes in all internal packages.
-keep public class !**.internal.**, com.google.** {
    public protected *;
}
# We can repackage all obfuscated classes in a new internal package.
-repackageclasses com.google.internal
-keep class android.support.v7.widget.SearchView { *; }
-dontwarn okio.**
-dontwarn retrofit2.**
-dontwarn org.junit.**
-dontwarn okhttp3.**
-dontwarn dagger.**
-dontwarn android.test.**
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}
-keep class dagger.* { *; }
-keep class javax.inject.* { *; }
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep class retrofit2.** { *; }
-keep class com.dynatrace.android.** { *; }
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep class okio.BufferedSource
-dontwarn com.squareup.okhttp.**
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }
-keep class com.shockwave.**
-dontwarn android.support.v8.renderscript.*
-keepclassmembers class android.support.v8.renderscript.RenderScript {
  native *** rsn*(...);
  native *** n*(...);
}
-keep class android.support.design.widget.TextInputLayout { *; }