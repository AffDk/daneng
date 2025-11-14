package com.daneng.dict.data.repo

import com.daneng.dict.data.db.Language
import com.daneng.dict.data.db.Sense
import com.daneng.dict.data.db.Word
import com.daneng.dict.data.db.WordDao
import com.daneng.dict.util.TextUtils

data class DictionaryEntry(
    val word: Word,
    val senses: List<Sense>
)

class DictionaryRepository(private val dao: WordDao) {
    suspend fun search(query: String, baseLanguage: Language, targetLanguage: Language, limit: Int = 50): List<DictionaryEntry> {
        if (query.isBlank()) return emptyList()
        val norm = TextUtils.normalize(query)
        val baseWords = dao.searchByPrefix(baseLanguage, norm, limit)
        return baseWords.map { w ->
            val senses = dao.getSensesForWord(w.id, targetLanguage)
            DictionaryEntry(w, senses)
        }.filter { it.senses.isNotEmpty() }
    }
}
