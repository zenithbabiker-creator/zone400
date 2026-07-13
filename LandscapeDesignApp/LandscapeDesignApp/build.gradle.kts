dependencies {
    // العودة لإصدار Sceneview القديم المتوافق مع كودك
    implementation("io.github.sceneview:arsceneview:0.10.0")

    // العودة لإصدار Material 1.1.2 لتجنب أخطاء ExposedDropdownMenu
    implementation("androidx.compose.material3:material3:1.1.2")
    
    // باقي المكتبات كما هي...
    implementation("com.google.ar:core:1.44.0")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    // ... باقي الـ dependencies
}
