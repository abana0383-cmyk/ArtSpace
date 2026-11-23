# ArtSpace – معرض فني رقمي (Android / Java / XML)

تطبيق أندرويد أصلي (Native) يعرض ست لوحات فنية مع التنقل بينها، وشاشة تفاصيل، و Intent صريح وضمني.

##  المتطلبات
- Android Studio (إصدار حديث: Koala / Ladybug أو أحدث)
- JDK 17 أو أحدث (مدمج مع Android Studio)
- اتصال بالإنترنت عند أول مزامنة Gradle (لتحميل Gradle 8.7 والمكتبات)
- `minSdk 24` (Android 7.0) – يغطي الغالبية العظمى من الأجهزة النشطة ويدعم Vector Drawables و Lambda بدون مكتبات إضافية. `compileSdk/targetSdk 34`.


##  بنية المشروع
```
ArtSpace/
├── settings.gradle, build.gradle, gradle.properties     # إعدادات البناء العامة
├── gradle/wrapper/gradle-wrapper.properties             # إصدار Gradle (8.7)
└── app/
    ├── build.gradle                                     # minSdk, namespace, المكتبات
    └── src/main/
        ├── AndroidManifest.xml                          # تسجيل Activities + <queries> للمتصفح
        ├── java/com/example/artspace/
        │   ├── MainActivity.java                        # عرض اللوحات، التنقل، حفظ الحالة، Intent صريح
        │   └── ArtworkDetailsActivity.java              # استلام البيانات، زر Back، Implicit Intent
        └── res/
            ├── layout/activity_main.xml                 # الشاشة الرئيسية (مطابقة للصور المرجعية)
            ├── layout-land/activity_main.xml            # نفس الواجهة بوضع أفقي (نفس الـ IDs)
            ├── layout/activity_artwork_details.xml      # واجهة شاشة التفاصيل
            ├── drawable-nodpi/artwork_01..06_*.jpg      # الصور الست (فضائية)
            ├── drawable/                                # أيقونة التطبيق
            ├── color/                                   # ألوان حالات الأزرار (مفعّل/معطّل)
            └── values/ (strings, colors, themes)        # النصوص والألوان والأنماط
```
