package com.daneng.dict

import com.daneng.dict.data.db.*
import com.daneng.dict.data.repo.DictionaryRepository
import com.daneng.dict.util.TextUtils
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class DictionaryRepositoryTest {
    private fun inMemoryDb(): DictionaryDatabase {
        val context = androidx.test.core.app.ApplicationProvider.getApplicationContext<android.content.Context>()
        return androidx.room.Room.inMemoryDatabaseBuilder(context, DictionaryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Test
    fun search_prefix_normalization_returns_expected() = runBlocking {
        val db = inMemoryDb()
        val dao = db.wordDao()
        val repo = DictionaryRepository(dao)

        val w = Word(language = Language.DA, headword = "Æble", phonetic = "ˈeːblə", searchKey = TextUtils.normalize("Æble"))
        val inserted = dao.insertWords(listOf(w))[0]
        dao.insertSenses(listOf(
            Sense(sourceWordId = inserted, targetLanguage = Language.EN, partOfSpeech = "noun", synonyms = listOf("apple"), examples = listOf(ExamplePair("Jeg spiser et æble", "I'm eating an apple")))
        ))

        val results = repo.search("aeb", Language.DA, Language.EN)
        assertEquals(1, results.size)
        assertEquals("Æble", results.first().word.headword)
    }
}
