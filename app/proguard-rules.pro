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

# ===== Reglas para drivers JDBC (usan reflexión) =====
# Evita que R8/ofuscación rompa jTDS y MS SQL JDBC en runtime.
-keep class net.sourceforge.jtds.** { *; }
-keep class com.microsoft.sqlserver.jdbc.** { *; }
-dontwarn net.sourceforge.jtds.**
-dontwarn com.microsoft.sqlserver.jdbc.**

# Clases usadas vía reflexión por los drivers.
-keep class javax.sql.** { *; }
-keep class javax.naming.** { *; }
-dontwarn javax.naming.**
