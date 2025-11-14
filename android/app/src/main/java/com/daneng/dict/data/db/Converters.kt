package com.daneng.dict.data.db

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
data class ExamplePair(
    val base: String,         // Example in source/base language
    val translation: String   // Translated example in destination language
)

object Converters {
    @TypeConverter
    @JvmStatic
    fun listToJson(list: List<String>?): String? = list?.let { Json.encodeToString(it) }

    @TypeConverter
    @JvmStatic
    fun jsonToList(json: String?): List<String>? = json?.let { Json.decodeFromString(it) }

    @TypeConverter
    @JvmStatic
    fun examplesToJson(list: List<ExamplePair>?): String? = list?.let { Json.encodeToString(it) }

    @TypeConverter
    @JvmStatic
    fun jsonToExamples(json: String?): List<ExamplePair>? = json?.let { Json.decodeFromString(it) }
}
