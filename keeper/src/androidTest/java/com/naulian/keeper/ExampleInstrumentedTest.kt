package com.naulian.keeper

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

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
        val intPref = keeper.recallInt(intPreferencesKey("int"), 0)
        assertEquals(0, intPref)
    }

    @Test
    fun stringPref_isCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val keeper = Keeper(appContext.datastore)
        val stringPref = keeper.recallString(stringPreferencesKey("string"), "")
        assertEquals("", stringPref)
    }

    @Test
    fun booleanPref_isCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val keeper = Keeper(appContext.datastore)
        val booleanPref = keeper.recallBoolean(booleanPreferencesKey("boolean"), false)
        assertEquals(false, booleanPref)
    }

    @Test
    fun floatPref_isCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val keeper = Keeper(appContext.datastore)
        val floatPref = keeper.recallFloat(floatPreferencesKey("float"), 0f)
        assertEquals(0f, floatPref)
    }

    @Test
    fun longPref_isCorrect(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val keeper = Keeper(appContext.datastore)
        val longPref = keeper.recallLong(longPreferencesKey("long"), 0L)
        assertEquals(0L, longPref)
    }
}