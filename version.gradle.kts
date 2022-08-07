import com.modrinth.minotaur.dependencies.ModDependency
import com.modrinth.minotaur.dependencies.DependencyType
import xyz.unifycraft.gradle.tools.CurseDependency

plugins {
    java
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("xyz.unifycraft.gradle.multiversion")
    id("xyz.unifycraft.gradle.tools")
    id("xyz.unifycraft.gradle.tools.loom")
    id("xyz.unifycraft.gradle.tools.shadow")
    //id("xyz.unifycraft.gradle.tools.releases")
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

    modImplementation("net.fabricmc.fabric-api:fabric-api:${when (mcData.version) {
        11900 -> "0.57.0+1.19"
        11802 -> "0.57.0+1.18.2"
        else -> throw IllegalStateException("Invalid MC version: ${mcData.version}")
    }}")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.8.2+kotlin.1.7.10")

    modImplementation("com.terraformersmc:modmenu:${when (mcData.version) {
        11900 -> "4.0.4"
        11802 -> "3.2.3"
        else -> throw IllegalStateException("Invalid MC version: ${mcData.version}")
    }}")
}

/*releases {
    releaseName.set("[${mcData.versionStr}] ${modData.name} ${modData.version}")
    file.set(tasks.remapJar)
    changelogFile.set(rootProject.file("CHANGELOG.md"))
    version.set("${modData.version}-${mcData.versionStr}")

    modrinth {
        projectId.set(property("releases.modrinth.id")?.toString() ?: throw IllegalStateException("No Modrinth project ID set."))
        dependencies.set(listOf(
            ModDependency("Ha28R6CL", DependencyType.REQUIRED)
        ))
    }

    curseforge {
        projectId.set(property("releases.curseforge.id")?.toString() ?: throw IllegalStateException("No CurseForge project ID set."))
        dependencies.set(listOf(
            CurseDependency("fabric-language-kotlin", true)
        ))
    }
}*/

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