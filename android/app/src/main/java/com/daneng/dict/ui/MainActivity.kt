package com.daneng.dict.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.daneng.dict.data.db.DictionaryDatabase
import com.daneng.dict.data.db.Language
import com.daneng.dict.data.repo.DictionaryRepository
import com.daneng.dict.data.seed.SeedDataImporter
import com.daneng.dict.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = DictionaryDatabase.build(this)
        val repo = DictionaryRepository(db.wordDao())
        val seeder = SeedDataImporter(this, db)

        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DictionaryScreen(repo, seeder)
                }
            }
        }
    }
}

@Composable
fun DictionaryScreen(repo: DictionaryRepository, seeder: SeedDataImporter) {
    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var baseLang by remember { mutableStateOf(Language.DA) }
    val targetLang = if (baseLang == Language.DA) Language.EN else Language.DA

    var results by remember { mutableStateOf(emptyList<com.daneng.dict.data.repo.DictionaryEntry>()) }

    LaunchedEffect(Unit) {
        seeder.ensureSeeded()
    }

    Column(Modifier.padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Danish ↔ English", style = MaterialTheme.typography.titleLarge)
            AssistChip(
                onClick = { baseLang = if (baseLang == Language.DA) Language.EN else Language.DA },
                label = { Text(if (baseLang == Language.DA) "DA → EN" else "EN → DA") }
            )
        }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                scope.launch {
                    results = repo.search(it.text, baseLang, targetLang, limit = 50)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Type a word…") },
            singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        LazyColumn(Modifier.fillMaxSize()) {
            items(results) { entry ->
                ResultCard(entry)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ResultCard(entry: com.daneng.dict.data.repo.DictionaryEntry) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(entry.word.headword, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            entry.word.phonetic?.let { Text("/$it/", style = MaterialTheme.typography.bodyMedium, fontStyle = FontStyle.Italic) }
            Spacer(Modifier.height(6.dp))
            entry.senses.forEach { sense ->
                Text(sense.partOfSpeech, style = MaterialTheme.typography.labelLarge)
                if (sense.synonyms.isNotEmpty()) {
                    Text("Synonyms: " + sense.synonyms.joinToString(", "), style = MaterialTheme.typography.bodyMedium)
                }
                if (sense.examples.isNotEmpty()) {
                    sense.examples.forEach { ex ->
                        Text("• ${ex.base}", style = MaterialTheme.typography.bodyMedium)
                        Text("  ${ex.translation}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                    }
                }
                Spacer(Modifier.height(6.dp))
            }
        }
    }
}
