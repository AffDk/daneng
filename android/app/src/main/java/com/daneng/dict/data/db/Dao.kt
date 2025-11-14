package com.daneng.dict.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWords(words: List<Word>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSenses(senses: List<Sense>): List<Long>

    @Query("SELECT * FROM words WHERE id = :id")
    suspend fun getWordById(id: Long): Word?

    @Query("SELECT rowid, headword, phonetic FROM words_fts WHERE words_fts MATCH :query LIMIT :limit")
    suspend fun searchFts(query: String, limit: Int = 50): List<WordFtsRow>

    @Query("SELECT * FROM words WHERE language = :language AND searchKey LIKE :prefix || '%' LIMIT :limit")
    suspend fun searchByPrefix(language: Language, prefix: String, limit: Int = 50): List<Word>

    @Query("SELECT * FROM senses WHERE sourceWordId = :wordId AND targetLanguage = :targetLang")
    suspend fun getSensesForWord(wordId: Long, targetLang: Language): List<Sense>
}

// Helper projection for FTS query results that can map to Word via rowid
data class WordFtsRow(
    val rowid: Long,
    val headword: String,
    val phonetic: String?
)
