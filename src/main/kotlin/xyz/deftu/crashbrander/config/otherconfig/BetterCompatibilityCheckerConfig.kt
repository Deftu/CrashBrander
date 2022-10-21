package xyz.deftu.crashbrander.config.otherconfig

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import xyz.deftu.crashbrander.CrashBranderConstants
import xyz.deftu.crashbrander.config.InvalidConfigException
import java.io.File
import java.io.StringReader

object BetterCompatibilityCheckerConfig : OtherConfig {
    override val file: File
        get() = File(CrashBranderConstants.getConfigDirectory(), "bcc.json")

    override lateinit var name: String
    override var version: String? = null

    override fun parse(input: String) {
        JsonReader(StringReader(input)).use { reader ->
            val token = reader.peek()
            if (token != JsonToken.BEGIN_OBJECT)
                throw InvalidConfigException("BCC config is supposed to be a JSON object..?")

            reader.beginObject()

            var currentName = ""
            while (reader.hasNext()) {
                val token = reader.peek()
                if (token == JsonToken.NAME) {
                    currentName = reader.nextName()
                    continue
                }

                when (currentName) {
                    "modpackName" -> {
                        if (token != JsonToken.STRING)
                            throw InvalidConfigException("BCC Config modpackName should be a string!")
                        name = reader.nextString()
                    }
                    "modpackVersion" -> {
                        if (token != JsonToken.STRING)
                            throw InvalidConfigException("BCC config modpackVersion should be a string!")
                        version = reader.nextString()
                    }
                    else -> reader.skipValue()
                }
            }

            reader.endObject()
        }
    }
}
