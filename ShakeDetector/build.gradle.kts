plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.anasesh.shakedetector"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    
    publishing {
        singleVariant("release") {
            withJavadocJar()
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.kotlin.bom))
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}




afterEvaluate {
    publishing {
        repositories {
            mavenLocal()
        }
        publications {
            register<MavenPublication>("release") {
                groupId = "com.github.AnasEsh"
                artifactId = "shake_detector"

                from(components["release"])
                // Only javadoc JAR is included via withJavadocJar()

                pom.withXml {
                    val root = asNode()
                    val dependenciesNode = root.get("dependencies").let { deps ->
                        if (deps is groovy.util.NodeList && deps.isNotEmpty()) {
                            deps[0] as groovy.util.Node
                        } else {
                            root.appendNode("dependencies")
                        }
                    }

                    // Add API dependencies to POM
                    configurations.getByName("api").allDependencies.forEach { dep ->
                        if (dep.group != null && dep.name != "unspecified" && dep.version != null) {
                            val dependencyNode = dependenciesNode.appendNode("dependency")
                            dependencyNode.appendNode("groupId", dep.group)
                            dependencyNode.appendNode("artifactId", dep.name)
                            dependencyNode.appendNode("version", dep.version)
                            dependencyNode.appendNode("scope", "compile")
                        }
                    }

                    // Add implementation dependencies to POM
                    configurations.getByName("implementation").allDependencies.forEach { dep ->
                        if (dep.group != null && dep.name != "unspecified" && dep.version != null) {
                            val dependencyNode = dependenciesNode.appendNode("dependency")
                            dependencyNode.appendNode("groupId", dep.group)
                            dependencyNode.appendNode("artifactId", dep.name)
                            dependencyNode.appendNode("version", dep.version)
                            dependencyNode.appendNode("scope", "runtime")
                        }
                    }
                }
            }
        }
    }
}
