@file:Suppress("unused")

package com.naulian.keeper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

typealias DsPrefs = DataStore<Preferences>

interface KeeperData

class Keeper @Inject constructor(private val dsPrefs: DataStore<Preferences>) {
    suspend fun recallStringAsFlow(
        key: Preferences.Key<String>, defValue: String = "", action: (String) -> Unit
    ) = dsPrefs.recallStringAsFlow(key, defValue).collect(action)

    suspend fun recallBooleanAsFlow(
        key: Preferences.Key<Boolean>, defValue: Boolean = false, action: (Boolean) -> Unit
    ) = dsPrefs.recallBooleanAsFlow(key, defValue).collect(action)

    suspend fun recallIntAsFlow(
        key: Preferences.Key<Int>, defValue: Int = 0, action: (Int) -> Unit
    ) = dsPrefs.recallIntAsFlow(key, defValue).collect(action)

    suspend fun recallLongAsFlow(
        key: Preferences.Key<Long>, defValue: Long = 0L, action: (Long) -> Unit
    ) = dsPrefs.recallLongAsFlow(key, defValue).collect(action)

    suspend fun recallFloatAsFlow(
        key: Preferences.Key<Float>, defValue: Float = 0f, action: (Float) -> Unit
    ) = dsPrefs.recallFloatAsFlow(key, defValue).collect(action)


    suspend fun keepBoolean(key: Preferences.Key<Boolean>, value: Boolean) =
        dsPrefs.keepBoolean(key, value)

    suspend fun keepInt(key: Preferences.Key<Int>, value: Int) = dsPrefs.keepInt(key, value)
    suspend fun keepString(key: Preferences.Key<String>, value: String) =
        dsPrefs.keepString(key, value)

    suspend fun keepLong(key: Preferences.Key<Long>, value: Long) = dsPrefs.keepLong(key, value)
    suspend fun keepFloat(key: Preferences.Key<Float>, value: Float) = dsPrefs.keepFloat(key, value)

    fun recallBoolean(key: Preferences.Key<Boolean>, defValue: Boolean = false) =
        dsPrefs.recallBoolean(key, defValue)

    fun recallInt(key: Preferences.Key<Int>, defValue: Int = 0) = dsPrefs.recallInt(key, defValue)
    fun recallString(key: Preferences.Key<String>, defValue: String = "") =
        dsPrefs.recallString(key, defValue)

    fun recallLong(key: Preferences.Key<Long>, defValue: Long = 0L) =
        dsPrefs.recallLong(key, defValue)

    fun recallFloat(key: Preferences.Key<Float>, defValue: Float = 0f) =
        dsPrefs.recallFloat(key, defValue)

    suspend inline fun <reified T : KeeperData> keep(key: Preferences.Key<String>, value: T) {
        val dataString = Json.encodeToString(value)
        keepString(key, dataString)
    }

    inline fun <reified T> recall(key: Preferences.Key<String>, defValue: T): T {
        val dataString = recallString(key)
        if (dataString.isEmpty()) return defValue

        val dataObj = Json.decodeFromString<T>(dataString)
        return dataObj
    }

    suspend inline fun <reified T> recallAsFlow(
        key: Preferences.Key<String>, defValue: T, crossinline action: (T) -> Unit
    ) = recallStringAsFlow(key) {
        if (it.isEmpty()) action(defValue)
        else action(Json.decodeFromString<T>(it))
    }
}

//flow
fun DsPrefs.recallStringAsFlow(
    key: Preferences.Key<String>, defValue: String = ""
) = recallPrefAsFlow(key, defValue)

fun DsPrefs.recallBooleanAsFlow(
    key: Preferences.Key<Boolean>, defValue: Boolean = false
) = recallPrefAsFlow(key, defValue)

fun DsPrefs.recallIntAsFlow(
    key: Preferences.Key<Int>, defValue: Int = 0
) = recallPrefAsFlow(key, defValue)

fun DsPrefs.recallLongAsFlow(
    key: Preferences.Key<Long>, defValue: Long = 0L
) = recallPrefAsFlow(key, defValue)

fun DsPrefs.recallFloatAsFlow(
    key: Preferences.Key<Float>, defValue: Float = 0f
) = recallPrefAsFlow(key, defValue)

private fun <T> DsPrefs.recallPrefAsFlow(
    key: Preferences.Key<T>, defValue: T
) = data.map { it[key] ?: defValue }

//recall
fun DsPrefs.recallBoolean(
    key: Preferences.Key<Boolean>, defValue: Boolean = false
) = runBlocking { recallBooleanAsFlow(key, defValue).first() }

fun DsPrefs.recallInt(
    key: Preferences.Key<Int>, defValue: Int = 0
) = runBlocking { recallIntAsFlow(key, defValue).first() }

fun DsPrefs.recallString(
    key: Preferences.Key<String>, defValue: String = ""
) = runBlocking { recallStringAsFlow(key, defValue).first() }

fun DsPrefs.recallLong(
    key: Preferences.Key<Long>, defValue: Long = 0L
) = runBlocking { recallLongAsFlow(key, defValue).first() }

fun DsPrefs.recallFloat(
    key: Preferences.Key<Float>, defValue: Float = 0f
) = runBlocking { recallFloatAsFlow(key, defValue).first() }

//write
suspend fun DsPrefs.keepBoolean(
    key: Preferences.Key<Boolean>, value: Boolean
) = keepPref(key, value)

suspend fun DsPrefs.keepInt(
    key: Preferences.Key<Int>, value: Int
) = keepPref(key, value)

suspend fun DsPrefs.keepString(
    key: Preferences.Key<String>, value: String
) = keepPref(key, value)

suspend fun DsPrefs.keepLong(
    key: Preferences.Key<Long>, value: Long
) = keepPref(key, value)

suspend fun DsPrefs.keepFloat(
    key: Preferences.Key<Float>, value: Float
) = keepPref(key, value)

private suspend fun <T> DsPrefs.keepPref(
    key: Preferences.Key<T>, value: T
) = edit { it[key] = value }
