import com.modrinth.minotaur.dependencies.DependencyType
import com.modrinth.minotaur.dependencies.ModDependency
import xyz.enhancedpixel.gradle.tools.CurseRelation
import xyz.enhancedpixel.gradle.tools.CurseRelationType

plugins {
    java
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("xyz.enhancedpixel.gradle.multiversion")
    id("xyz.enhancedpixel.gradle.tools")
    id("xyz.enhancedpixel.gradle.tools.loom")
    id("xyz.enhancedpixel.gradle.tools.shadow")
    id("xyz.enhancedpixel.gradle.tools.releases")
}

repositories {
    maven("https://maven.terraformersmc.com/")
    maven("https://maven.deftu.xyz/releases")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    modImplementation(include("xyz.deftu:DeftuLib-${mcData.versionStr}:1.1.2")!!)
}


releases {
    modrinth {
        projectId.set("C9eIZ8sb")
        dependencies.set(listOf(
            ModDependency("P7dR8mSH", DependencyType.REQUIRED),
            ModDependency("Ha28R6CL", DependencyType.REQUIRED),
            ModDependency("mOgUt4GM", DependencyType.REQUIRED)
        ))
    }

    curseforge {
        projectId.set("657052")
        relations.set(listOf(
            CurseRelation("fabric-api", CurseRelationType.REQUIRED),
            CurseRelation("fabric-language-kotlin", CurseRelationType.REQUIRED),
            CurseRelation("modmenu", CurseRelationType.REQUIRED)
        ))
    }
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