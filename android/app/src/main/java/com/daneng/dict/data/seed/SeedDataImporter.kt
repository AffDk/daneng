package com.daneng.dict.data.seed

import android.content.Context
import com.daneng.dict.data.db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader

class SeedDataImporter(private val context: Context, private val db: DictionaryDatabase) {
    private val prefs = context.getSharedPreferences("seed", Context.MODE_PRIVATE)

    suspend fun ensureSeeded() = withContext(Dispatchers.IO) {
        if (prefs.getBoolean("seeded", false)) return@withContext
        val words = mutableListOf<Word>()
        val senses = mutableListOf<Sense>()

        // Very small sample to keep repo size down. Extend with larger CSVs offline.
        readCsv("seed/words_da_en.csv").forEach { row ->
            val head = row.getOrElse(0) { "" }
            val phon = row.getOrElse(1) { "" }
            val w = Word(language = Language.DA, headword = head, phonetic = phon.ifBlank { null }, searchKey = com.daneng.dict.util.TextUtils.normalize(head))
            words += w
        }
        val insertedWordIds = db.wordDao().insertWords(words)

        // Re-read to build senses now that we have IDs (index-aligned)
        var idx = 0
        readCsv("seed/words_da_en.csv").forEach { row ->
            val pos = row.getOrElse(2) { "" }
            val synCsv = row.getOrElse(3) { "" }
            val exBase = row.getOrElse(4) { "" }
            val exTr = row.getOrElse(5) { "" }
            val wordId = insertedWordIds[idx].takeIf { it > 0 } ?: (idx + 1).toLong()
            idx++
            val synonyms = synCsv.split(';').map { it.trim() }.filter { it.isNotEmpty() }
            val examples = listOf(ExamplePair(base = exBase, translation = exTr))
            senses += Sense(
                sourceWordId = wordId,
                targetLanguage = Language.EN,
                partOfSpeech = pos,
                synonyms = synonyms,
                examples = examples
            )
        }

        // English to Danish sample
        readCsv("seed/words_en_da.csv").forEach { row ->
            val head = row.getOrElse(0) { "" }
            val phon = row.getOrElse(1) { "" }
            val w = Word(language = Language.EN, headword = head, phonetic = phon.ifBlank { null }, searchKey = com.daneng.dict.util.TextUtils.normalize(head))
            words += w
        }
        val insertedWordIds2 = db.wordDao().insertWords(words.drop(insertedWordIds.size))
        var j = 0
        readCsv("seed/words_en_da.csv").forEach { row ->
            val pos = row.getOrElse(2) { "" }
            val synCsv = row.getOrElse(3) { "" }
            val exBase = row.getOrElse(4) { "" }
            val exTr = row.getOrElse(5) { "" }
            val wordId = insertedWordIds2.getOrNull(j)?.takeIf { it > 0 } ?: (insertedWordIds.size + j + 1).toLong()
            j++
            val synonyms = synCsv.split(';').map { it.trim() }.filter { it.isNotEmpty() }
            val examples = listOf(ExamplePair(base = exBase, translation = exTr))
            senses += Sense(
                sourceWordId = wordId,
                targetLanguage = Language.DA,
                partOfSpeech = pos,
                synonyms = synonyms,
                examples = examples
            )
        }

        db.wordDao().insertSenses(senses)
        prefs.edit().putBoolean("seeded", true).apply()
    }

    private fun readCsv(assetPath: String): List<List<String>> {
        return context.assets.open(assetPath).use { input ->
            BufferedReader(input.reader()).readLines()
                .filter { it.isNotBlank() && !it.startsWith("#") }
                .map { line ->
                    // Simple CSV: head,phon,pos,syn1;syn2,example,translation
                    val parts = line.split(',')
                    val padded = parts + List(maxOf(0, 6 - parts.size)) { "" }
                    padded.take(6)
                }
        }
    }
}
