package com.daneng.dict.data.db

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words",
    indices = [Index(value = ["language", "headword"]), Index(value = ["searchKey"])],
)
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val language: Language,
    val headword: String,          // lemma/citation form
    val phonetic: String? = null,  // IPA or simplified phonetic transcription
    val searchKey: String          // normalized key for diacritics-insensitive search
)

// Full-text search virtual table for fast lookups by headword and phonetic/aliases
@Fts4(contentEntity = Word::class)
@Entity(tableName = "words_fts")
data class WordFts(
    val headword: String,
    val phonetic: String?
)
