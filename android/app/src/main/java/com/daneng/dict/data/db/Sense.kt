package com.daneng.dict.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "senses",
    indices = [Index(value = ["sourceWordId", "targetLanguage"])]
)
data class Sense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sourceWordId: Long,          // FK to Word.id in base language
    val targetLanguage: Language,    // Destination language for synonyms/examples
    val partOfSpeech: String,        // noun, verb, adjective, etc.
    val synonyms: List<String> = emptyList(), // synonyms in destination language
    val examples: List<ExamplePair> = emptyList() // example sentences + translations
)
