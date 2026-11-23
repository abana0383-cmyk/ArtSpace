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

## كيف يعمل الكود (ملخص للفهم والمناقشة)
| المتطلب | أين / كيف |
|---|---|
| بيانات اللوحات | مصفوفات متوازية `IMAGES / TITLES / ARTISTS / YEARS` في `MainActivity` (نفس الفهرس = نفس اللوحة) |
| التنقل | `currentIndex++` / `currentIndex--` داخل شرط الحدود ثم `showArtwork()` |
| تعطيل Previous/Next | `btnPrevious.setEnabled(currentIndex > 0)` و `btnNext.setEnabled(currentIndex < IMAGES.length - 1)` |
| منع الفهرس غير الصالح | شروط الحدود في الأزرار + تقييد (clamp) الفهرس داخل `showArtwork()` |
| حفظ الحالة عند التدوير | `onSaveInstanceState()` تحفظ `currentIndex` في `Bundle`، و `onCreate()` تقرؤه إن لم يكن `savedInstanceState == null` |
| Explicit Intent | `new Intent(MainActivity.this, ArtworkDetailsActivity.class)` + `putExtra` للعنوان والفنان والسنة |
| استلام البيانات | `getIntent().getStringExtra(...)` / `getIntExtra(...)` ثم عرضها في `TextView` واحد |
| Back | `finish()` يعيدك إلى `MainActivity` على نفس اللوحة |
| Implicit Intent | `new Intent(Intent.ACTION_VIEW, Uri.parse("https://..."))` داخل `try/catch (ActivityNotFoundException)` مع `Toast` عند عدم وجود متصفح |
