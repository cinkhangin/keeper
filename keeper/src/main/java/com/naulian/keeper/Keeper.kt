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
typealias PrefKey<T> = Preferences.Key<T>

interface KeeperData

class Keeper @Inject constructor(private val dsPrefs: DataStore<Preferences>) {
    //as Flow
    private fun <T> DsPrefs.recallPrefAsFlow(
        key: PrefKey<T>, defValue: T
    ) = data.map { it[key] ?: defValue }

    fun recallStringAsFlow(
        key: PrefKey<String>, defValue: String = ""
    ) = dsPrefs.recallPrefAsFlow(key, defValue)

    fun recallBooleanAsFlow(
        key: PrefKey<Boolean>, defValue: Boolean = false
    ) = dsPrefs.recallPrefAsFlow(key, defValue)

    fun recallIntAsFlow(
        key: PrefKey<Int>, defValue: Int = 0
    ) = dsPrefs.recallPrefAsFlow(key, defValue)

    fun recallLongAsFlow(
        key: PrefKey<Long>, defValue: Long = 0L
    ) = dsPrefs.recallPrefAsFlow(key, defValue)

    fun recallFloatAsFlow(
        key: PrefKey<Float>, defValue: Float = 0f
    ) = dsPrefs.recallPrefAsFlow(key, defValue)

    //collect
    suspend fun recallStringAsFlow(
        key: PrefKey<String>, defValue: String = "", action: (String) -> Unit
    ) = dsPrefs.recallPrefAsFlow(key, defValue).collect(action)

    suspend fun recallBooleanAsFlow(
        key: PrefKey<Boolean>, defValue: Boolean = false, action: (Boolean) -> Unit
    ) = dsPrefs.recallPrefAsFlow(key, defValue).collect(action)

    suspend fun recallIntAsFlow(
        key: PrefKey<Int>, defValue: Int = 0, action: (Int) -> Unit
    ) = dsPrefs.recallPrefAsFlow(key, defValue).collect(action)

    suspend fun recallLongAsFlow(
        key: PrefKey<Long>, defValue: Long = 0L, action: (Long) -> Unit
    ) = dsPrefs.recallPrefAsFlow(key, defValue).collect(action)

    suspend fun recallFloatAsFlow(
        key: PrefKey<Float>, defValue: Float = 0f, action: (Float) -> Unit
    ) = dsPrefs.recallPrefAsFlow(key, defValue).collect(action)


    //keep
    private suspend fun <T> DsPrefs.keepPref(
        key: PrefKey<T>, value: T
    ) = edit { it[key] = value }

    suspend fun keepBoolean(
        key: PrefKey<Boolean>, value: Boolean
    ) = dsPrefs.keepPref(key, value)

    suspend fun keepInt(
        key: PrefKey<Int>, value: Int
    ) = dsPrefs.keepPref(key, value)

    suspend fun keepString(
        key: PrefKey<String>, value: String
    ) = dsPrefs.keepPref(key, value)

    suspend fun keepLong(
        key: PrefKey<Long>, value: Long
    ) = dsPrefs.keepPref(key, value)

    suspend fun keepFloat(
        key: PrefKey<Float>, value: Float
    ) = dsPrefs.keepPref(key, value)


    //recall
    fun recallBoolean(key: PrefKey<Boolean>, defValue: Boolean = false) =
        runBlocking { recallBooleanAsFlow(key, defValue).first() }

    fun recallInt(key: PrefKey<Int>, defValue: Int = 0) =
        runBlocking { recallIntAsFlow(key, defValue).first() }

    fun recallString(key: PrefKey<String>, defValue: String = "") =
        runBlocking { recallStringAsFlow(key, defValue).first() }

    fun recallLong(key: PrefKey<Long>, defValue: Long = 0L) =
        runBlocking { recallLongAsFlow(key, defValue).first() }

    fun recallFloat(key: PrefKey<Float>, defValue: Float = 0f) =
        runBlocking { recallFloatAsFlow(key, defValue).first() }

    suspend inline fun <reified T : KeeperData> keep(key: PrefKey<String>, value: T) {
        val dataString = Json.encodeToString(value)
        keepString(key, dataString)
    }

    inline fun <reified T> recall(key: PrefKey<String>, defValue: T): T {
        val dataString = recallString(key)
        if (dataString.isEmpty()) return defValue

        val dataObj = Json.decodeFromString<T>(dataString)
        return dataObj
    }

    suspend inline fun <reified T> recallAsFlow(
        key: PrefKey<String>, defValue: T, crossinline action: (T) -> Unit
    ) = recallStringAsFlow(key) {
        if (it.isEmpty()) action(defValue)
        else action(Json.decodeFromString<T>(it))
    }
}

