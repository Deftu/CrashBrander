package xyz.deftu.crashbrander.config

import com.google.gson.Gson
import com.google.gson.JsonObject
import xyz.deftu.crashbrander.CrashBranderConstants
import xyz.deftu.crashbrander.config.otherconfig.OtherConfigType
import java.io.File

data class Config(
    val enabled: Boolean,
    val updateChecking: Boolean,
    val otherConfig: OtherConfigType?,
    val printTypes: List<PrintType>,
    val name: String?,
    val version: String?,
    val author: String?,
    val website: String?,
    val support: String?
) {
    companion object {
        val INSTANCE by lazy {
            val file = File(CrashBranderConstants.getConfigDirectory(), "crashbrander.json")
            if (!file.exists()) {
                if (file.createNewFile()) {
                    file.writeText(Gson().toJson(JsonObject()))
                } else throw IllegalStateException("Could not create config file!")
            }

            ConfigParser.parse(file.readText())
        }
    }

    fun getModpackName(): String? {
        if (otherConfig != null)
            return otherConfig.instance.name
        return name
    }

    fun getModpackVersion(): String? {
        if (otherConfig != null)
            return otherConfig.instance.version
        return version
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
