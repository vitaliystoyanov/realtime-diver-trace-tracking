plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.kotlin.serialize)
}

kotlin {
    // Apply the default hierarchy again
    applyDefaultHierarchyTemplate()
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//    }
    js(IR) {
        browser()
    }
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)
            implementation(libs.gson)

            implementation(libs.kotlinx.coroutine.core)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.protobuf)
        }
    }
}