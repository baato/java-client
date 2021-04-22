-keep class com.baato.baatolibrary.models.** { *; }
-keepclassmembers class com.baato.baatolibrary.services.** {
   public static *** parse(...);
}
-keep class com.baato.baatolibrary.navigation.** { *; }
-keepclassmembers class com.baato.baatolibrary.navigation.** {
   public static *** parseparse(...);
   private static *** parseparse(...);
}