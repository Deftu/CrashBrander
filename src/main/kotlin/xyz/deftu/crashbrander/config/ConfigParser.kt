package xyz.deftu.crashbrander.config

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import java.io.StringReader

object ConfigParser {
    fun parse(input: String): Config {
        JsonReader(StringReader(input)).use { reader ->
            val token = reader.peek()
            if (token != JsonToken.BEGIN_OBJECT)
                throw InvalidConfigException("Config should be a JSON object!")

            var enabled = true
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
                    "print_type", "type" -> printTypes = parsePrintType(reader, token)
                    "name" -> {
                        if (token != JsonToken.STRING)
                            throw InvalidConfigException("Config#name should be a string!")
                        name = reader.nextString()
                    }
                    "version" -> {
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
                }
            }

            reader.endObject()
            return Config(enabled, printTypes, name, version, author, website, support)
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
