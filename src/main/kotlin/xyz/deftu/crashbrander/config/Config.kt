package xyz.deftu.crashbrander.config

import java.io.File

data class Config(
    val enabled: Boolean,
    val printTypes: List<PrintType>,
    val name: String?,
    val version: String?,
    val author: String?,
    val website: String?,
    val support: String?
) {
    companion object {
        val INSTANCE by lazy {
            val file = File("config/crashbrander.json")
            if (!file.exists())
                file.createNewFile()
            ConfigParser.parse(file.readText())
        }
    }

    enum class PrintType {
        LOGS,
        CRASH;

        companion object {
            fun from(input: String) = values().firstOrNull {
                it.name.equals(input, true)
            } ?: throw InvalidConfigException("Invalid print type provided! ($input)")
        }
    }
}
