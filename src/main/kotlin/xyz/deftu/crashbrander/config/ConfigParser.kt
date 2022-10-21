package xyz.deftu.crashbrander.config

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import xyz.deftu.crashbrander.CrashBranderConstants
import xyz.deftu.crashbrander.config.otherconfig.OtherConfigType
import java.io.StringReader

object ConfigParser {
    fun parse(input: String): Config {
        CrashBranderConstants.logger.info("Parsing config${if (CrashBranderConstants.debug) " from JSON ($input)" else ""}.")
        JsonReader(StringReader(input)).use { reader ->
            val token = reader.peek()
            if (token != JsonToken.BEGIN_OBJECT)
                throw InvalidConfigException("Config should be a JSON object!")

            var enabled = true
            var otherConfig: OtherConfigType? = null
            var printTypes = listOf(Config.PrintType.CRASH)
            var name: String? = null
            var version: String? = null
            var author: String? = null
            var website: String? = null
            var support: String? = null

            reader.beginObject()

            var currentName = ""
            while (reader.hasNext()) {
                val token = reader.peek()
                if (token == JsonToken.NAME) {
                    currentName = reader.nextName()
                    continue
                }

                when (currentName) {
                    "enabled" -> {
                        if (token != JsonToken.BOOLEAN)
                            throw InvalidConfigException("Config#enabled should be a boolean!")
                        enabled = reader.nextBoolean()
                    }
                    "other_config", "other" -> {
                        if (token != JsonToken.STRING)
                            throw InvalidConfigException("Config#other_config should be a string!")
                        val value = reader.nextString()
                        otherConfig = OtherConfigType.values().firstOrNull {
                            it.name.equals(value, true)
                        } ?: throw InvalidConfigException("An invalid value was provided for Config#other_config!")
                        CrashBranderConstants.logger.info("Other (external) config found! Loading name and version from there instead.")
                        otherConfig.instance.parse(otherConfig.instance.file.readText())
                    }
                    "print_type", "type" -> printTypes = parsePrintType(reader, token)
                    "name" -> {
                        if (otherConfig != null) {
                            reader.skipValue()
                            continue // We'll be reading from the other config file.
                        }

                        if (token != JsonToken.STRING)
                            throw InvalidConfigException("Config#name should be a string!")
                        name = reader.nextString()
                    }
                    "version" -> {
                        if (otherConfig != null) {
                            reader.skipValue()
                            continue // We'll be reading from the other config file.
                        }

                        if (token != JsonToken.STRING)
                            throw InvalidConfigException("Config#version should be a string!")
                        version = reader.nextString()
                    }
                    "author" -> {
                        if (token != JsonToken.STRING)
                            throw InvalidConfigException("Config#author should be a string!")
                        author = reader.nextString()
                    }
                    "website" -> {
                        if (token != JsonToken.STRING)
                            throw InvalidConfigException("Config#website should be a string!")
                        website = reader.nextString()
                    }
                    "support" -> {
                        if (token != JsonToken.STRING)
                            throw InvalidConfigException("Config#support should be a string!")
                        support = reader.nextString()
                    }
                    else -> {
                        CrashBranderConstants.logger.warn("Unknown key-value found... Ignoring. ($currentName)")
                        reader.skipValue()
                    }
                }
            }

            reader.endObject()
            return Config(enabled, otherConfig, printTypes, name, version, author, website, support)
        }
    }

    private fun parsePrintType(reader: JsonReader, token: JsonToken): List<Config.PrintType> {
        val returnValue = mutableListOf<Config.PrintType>()
        when (token) {
            JsonToken.STRING -> returnValue.add(Config.PrintType.from(reader.nextString()))
            JsonToken.BEGIN_ARRAY -> {
                reader.beginArray()
                while (reader.hasNext()) {
                    val token = reader.peek()
                    if (token != JsonToken.STRING)
                        throw InvalidConfigException("Config#print_type may only contain strings!")

                    returnValue.add(Config.PrintType.from(reader.nextString()))
                }
                reader.endArray()
            }
            else -> throw InvalidConfigException("Config#print_type should either be a string or an array!")
        }
        return returnValue
    }
}
