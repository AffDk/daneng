package com.daneng.dict.util

import java.text.Normalizer

object TextUtils {
    // Normalize case and diacritics for search (æ, ø, å, etc.)
    fun normalize(input: String): String {
        val lower = input.lowercase()
        val decomposed = Normalizer.normalize(lower, Normalizer.Form.NFD)
        val stripped = decomposed.replace("\\p{Mn}".toRegex(), "") // remove diacritics
        return stripped
            .replace('æ', 'a') // keep simple fallbacks though diacritics removed handles many
            .replace('ø', 'o')
            .replace('å', 'a')
    }

    fun toFtsQuery(query: String): String {
        val norm = normalize(query).trim()
        return if (norm.isEmpty()) "" else "$norm*"
    }
}
