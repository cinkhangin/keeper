package com.naulian.keeper

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun intPref_isCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val keeper = Keeper(appContext.datastore)
        val key = intPreferencesKey("int")
        runBlocking { keeper.keepInt(key, 42) }
        val intPref = keeper.recallInt(key, 0)
        assertEquals(42, intPref)
    }

    @Test
    fun stringPref_isCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val keeper = Keeper(appContext.datastore)
        val key = stringPreferencesKey("string")
        runBlocking { keeper.keepString(key, "42") }
        val stringPref = keeper.recallString(key, "")
        assertEquals("42", stringPref)
    }

    @Test
    fun booleanPref_isCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val keeper = Keeper(appContext.datastore)
        val key = booleanPreferencesKey("boolean")
        runBlocking { keeper.keepBoolean(key, true) }
        val booleanPref = keeper.recallBoolean(key, false)
        assertEquals(true, booleanPref)
    }

    @Test
    fun floatPref_isCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val keeper = Keeper(appContext.datastore)
        val key = floatPreferencesKey("float")
        runBlocking { keeper.keepFloat(key, 4.2f) }
        val floatPref = keeper.recallFloat(key, 0f)
        assertEquals(4.2f, floatPref)
    }

    @Test
    fun longPref_isCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val keeper = Keeper(appContext.datastore)
        val key = longPreferencesKey("long")
        runBlocking { keeper.keepLong(key, 42L) }
        val longPref = keeper.recallLong(key, 0L)
        assertEquals(42L, longPref)
    }
}