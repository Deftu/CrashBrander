package xyz.deftu.crashbrander

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import java.io.File

data class Config(
    val enabled: Boolean? = true,
    val name: String?,
    val version: String?,
    val author: String?,
    val website: String?
) {
    companion object {
        private val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setLenient()
            .create()
        fun read(file: File): Config =
            gson.fromJson(file.apply {
                if (!parentFile.exists()) parentFile.mkdirs()
                if (!exists()) {
                    createNewFile()
                    writeText(gson.toJson(Config(
                        false,
                        "",
                        "",
                        "",
                        ""
                    )))
                }
            }.readText(), Config::class.java)
    }
}
