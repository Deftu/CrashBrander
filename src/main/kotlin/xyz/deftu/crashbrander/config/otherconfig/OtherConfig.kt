package xyz.deftu.crashbrander.config.otherconfig

import java.io.File

interface OtherConfig {
    val file: File

    val name: String
    val version: String?

    fun parse(input: String)
}
