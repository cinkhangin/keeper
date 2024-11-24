@file:Suppress("unused")

package com.naulian.keeper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

typealias DsPrefs = DataStore<Preferences>

class Keeper @Inject constructor(private val dsPrefs: DataStore<Preferences>) {
    suspend fun recallStringAsFlow(
        key: String, defValue: String = "", action: (String) -> Unit
    ) = dsPrefs.recallStringAsFlow(key, defValue).collect(action)

    suspend fun recallBooleanAsFlow(
        key: String, defValue: Boolean = false, action: (Boolean) -> Unit
    ) = dsPrefs.recallBooleanAsFlow(key, defValue).collect(action)

    suspend fun recallIntAsFlow(
        key: String, defValue: Int = 0, action: (Int) -> Unit
    ) = dsPrefs.recallIntAsFlow(key, defValue).collect(action)

    suspend fun recallLongAsFlow(
        key: String, defValue: Long = 0L, action: (Long) -> Unit
    ) = dsPrefs.recallLongAsFlow(key, defValue).collect(action)

    suspend fun recallFloatAsFlow(
        key: String, defValue: Float = 0f, action: (Float) -> Unit
    ) = dsPrefs.recallFloatAsFlow(key, defValue).collect(action)


    suspend fun keepBoolean(key: String, value: Boolean) = dsPrefs.keepBoolean(key, value)
    suspend fun keepInt(key: String, value: Int) = dsPrefs.keepInt(key, value)
    suspend fun keepString(key: String, value: String) = dsPrefs.keepString(key, value)
    suspend fun keepLong(key: String, value: Long) = dsPrefs.keepLong(key, value)
    suspend fun keepFloat(key: String, value: Float) = dsPrefs.keepFloat(key, value)

    fun recallBoolean(key: String, defValue: Boolean = false) = dsPrefs.recallBoolean(key, defValue)
    fun recallInt(key: String, defValue: Int = 0) = dsPrefs.recallInt(key, defValue)
    fun recallString(key: String, defValue: String = "") = dsPrefs.recallString(key, defValue)
    fun recallLong(key: String, defValue: Long = 0L) = dsPrefs.recallLong(key, defValue)
    fun recallFloat(key: String, defValue: Float = 0f) = dsPrefs.recallFloat(key, defValue)
}

//flow
fun DsPrefs.recallStringAsFlow(key: String, defValue: String = "") =
    data.map { it[stringPreferencesKey(key)] ?: defValue }

fun DsPrefs.recallBooleanAsFlow(key: String, defValue: Boolean = false) =
    data.map { it[booleanPreferencesKey(key)] ?: defValue }

fun DsPrefs.recallIntAsFlow(key: String, defValue: Int = 0) =
    data.map { it[intPreferencesKey(key)] ?: defValue }

fun DsPrefs.recallLongAsFlow(key: String, defValue: Long = 0L) =
    data.map { it[longPreferencesKey(key)] ?: defValue }

fun DsPrefs.recallFloatAsFlow(key: String, defValue: Float = 0f) =
    data.map { it[floatPreferencesKey(key)] ?: defValue }

//recall
fun DsPrefs.recallBoolean(key: String, defValue: Boolean = false) =
    runBlocking { recallBooleanAsFlow(key, defValue).first() }

fun DsPrefs.recallInt(key: String, defValue: Int = 0) =
    runBlocking { recallIntAsFlow(key, defValue).first() }

fun DsPrefs.recallString(key: String, defValue: String = "") =
    runBlocking { recallStringAsFlow(key, defValue).first() }

fun DsPrefs.recallLong(key: String, defValue: Long = 0L) =
    runBlocking { recallLongAsFlow(key, defValue).first() }

fun DsPrefs.recallFloat(key: String, defValue: Float = 0f) =
    runBlocking { recallFloatAsFlow(key, defValue).first() }

//write
suspend fun DsPrefs.keepBoolean(key: String, value: Boolean) =
    edit { it[booleanPreferencesKey(key)] = value }

suspend fun DsPrefs.keepInt(key: String, value: Int) =
    edit { it[intPreferencesKey(key)] = value }

suspend fun DsPrefs.keepString(key: String, value: String) =
    edit { it[stringPreferencesKey(key)] = value }

suspend fun DsPrefs.keepLong(key: String, value: Long) =
    edit { it[longPreferencesKey(key)] = value }

suspend fun DsPrefs.keepFloat(key: String, value: Float) =
    edit { it[floatPreferencesKey(key)] = value }

