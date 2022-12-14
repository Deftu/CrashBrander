import com.modrinth.minotaur.dependencies.DependencyType
import com.modrinth.minotaur.dependencies.ModDependency
import xyz.deftu.gradle.tools.CurseRelation
import xyz.deftu.gradle.tools.CurseRelationType

plugins {
    java
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("xyz.deftu.gradle.multiversion")
    id("xyz.deftu.gradle.tools")
    id("xyz.deftu.gradle.tools.loom")
    id("xyz.deftu.gradle.tools.shadow")
    id("xyz.deftu.gradle.tools.releases")
}

repositories {
    maven("https://maven.terraformersmc.com/")
    maven("https://maven.deftu.xyz/releases")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    modImplementation("xyz.deftu:DeftuLib-${mcData.versionStr}:1.2.1")
}

releases {
    modrinth {
        projectId.set("C9eIZ8sb")
        dependencies.set(listOf(
            ModDependency("Ha28R6CL", DependencyType.REQUIRED),
            ModDependency("mOgUt4GM", DependencyType.REQUIRED),
            ModDependency("WfhjX9sQ", DependencyType.REQUIRED)
        ))
    }

    curseforge {
        projectId.set("657052")
        relations.set(listOf(
            CurseRelation("fabric-language-kotlin", CurseRelationType.REQUIRED),
            CurseRelation("modmenu", CurseRelationType.REQUIRED),
            CurseRelation("deftulib", CurseRelationType.REQUIRED)
        ))
    }
}
