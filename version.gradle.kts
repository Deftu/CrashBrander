plugins {
    java
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("xyz.unifycraft.gradle.multiversion")
    id("xyz.unifycraft.gradle.tools")
    id("xyz.unifycraft.gradle.tools.loom")
    id("xyz.unifycraft.gradle.tools.shadow")
}

loomHelper {
    disableRunConfigs(xyz.unifycraft.gradle.utils.GameSide.SERVER)
}

repositories {
    maven("https://maven.terraformersmc.com/")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    modImplementation("net.fabricmc:fabric-language-kotlin:1.8.2+kotlin.1.7.10")

    modImplementation("com.terraformersmc:modmenu:${when (mcData.version) {
        11900 -> "4.0.4"
        11802 -> "3.2.3"
        else -> throw IllegalStateException("Invalid MC version: ${mcData.version}")
    }}")
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