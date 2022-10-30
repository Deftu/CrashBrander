plugins {
    java
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("xyz.enhancedpixel.gradle.multiversion")
    id("xyz.enhancedpixel.gradle.tools")
    id("xyz.enhancedpixel.gradle.tools.loom")
    id("xyz.enhancedpixel.gradle.tools.shadow")
}

loomHelper {
    disableRunConfigs(xyz.enhancedpixel.gradle.utils.GameSide.SERVER)
}

repositories {
    maven("https://maven.terraformersmc.com/")
    maven("https://maven.deftu.xyz/releases")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    modImplementation("xyz.deftu:DeftuLib-${mcData.versionStr}:1.0.0")
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs += "-Xjvm-default=enable"
        }
    }

    remapJar {
        archiveBaseName.set("${modData.name}-${mcData.versionStr}")
    }
}