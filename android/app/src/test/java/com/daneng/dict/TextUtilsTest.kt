package com.daneng.dict

import com.daneng.dict.util.TextUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class TextUtilsTest {
    @Test
    fun normalize_removes_diacritics_and_lowercases() {
        assertEquals("aero", TextUtils.normalize("Ærø"))
        assertEquals("kaabe", TextUtils.normalize("KÅBE"))
    }

    @Test
    fun toFtsQuery_appends_wildcard() {
        assertEquals("test*", TextUtils.toFtsQuery("Test"))
    }
}
