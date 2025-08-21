@file:Suppress("unused")

package com.naulian.keeper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import javax.inject.Inject

typealias DsPrefs = DataStore<Preferences>
typealias PrefKey<T> = Preferences.Key<T>

interface KeeperData

class Keeper @Inject constructor(private val dsPrefs: DataStore<Preferences>) {
    //as Flow
    private fun <T> DsPrefs.takePrefAsFlow(
        key: PrefKey<T>, defValue: T
    ) = data.map { it[key] ?: defValue }

    fun takeAsFlow(
        key: PrefKey<String>, defValue: String = ""
    ) = dsPrefs.takePrefAsFlow(key, defValue)

    fun takeAsFlow(
        key: PrefKey<Boolean>, defValue: Boolean = false
    ) = dsPrefs.takePrefAsFlow(key, defValue)

    fun takeAsFlow(
        key: PrefKey<Int>, defValue: Int = 0
    ) = dsPrefs.takePrefAsFlow(key, defValue)

    fun takeAsFlow(
        key: PrefKey<Long>, defValue: Long = 0L
    ) = dsPrefs.takePrefAsFlow(key, defValue)

    fun takeAsFlow(
        key: PrefKey<Float>, defValue: Float = 0f
    ) = dsPrefs.takePrefAsFlow(key, defValue)

    //collect
    suspend fun takeAsFlow(
        key: PrefKey<String>, defValue: String = "", action: (String) -> Unit
    ) = dsPrefs.takePrefAsFlow(key, defValue).collect(action)

    suspend fun takeAsFlow(
        key: PrefKey<Boolean>, defValue: Boolean = false, action: (Boolean) -> Unit
    ) = dsPrefs.takePrefAsFlow(key, defValue).collect(action)

    suspend fun takeAsFlow(
        key: PrefKey<Int>, defValue: Int = 0, action: (Int) -> Unit
    ) = dsPrefs.takePrefAsFlow(key, defValue).collect(action)

    suspend fun takeAsFlow(
        key: PrefKey<Long>, defValue: Long = 0L, action: (Long) -> Unit
    ) = dsPrefs.takePrefAsFlow(key, defValue).collect(action)

    suspend fun takeAsFlow(
        key: PrefKey<Float>, defValue: Float = 0f, action: (Float) -> Unit
    ) = dsPrefs.takePrefAsFlow(key, defValue).collect(action)


    //keep
    private suspend fun <T> DsPrefs.keepPref(
        key: PrefKey<T>, value: T
    ) = edit { it[key] = value }

    suspend fun keep(
        key: PrefKey<Boolean>, value: Boolean
    ) = dsPrefs.keepPref(key, value)

    suspend fun keep(
        key: PrefKey<Int>, value: Int
    ) = dsPrefs.keepPref(key, value)

    suspend fun keep(
        key: PrefKey<String>, value: String
    ) = dsPrefs.keepPref(key, value)

    suspend fun keep(
        key: PrefKey<Long>, value: Long
    ) = dsPrefs.keepPref(key, value)

    suspend fun keep(
        key: PrefKey<Float>, value: Float
    ) = dsPrefs.keepPref(key, value)


    //recall
    fun take(key: PrefKey<Boolean>, defValue: Boolean = false) =
        runBlocking { takeAsFlow(key, defValue).first() }

    fun take(key: PrefKey<Int>, defValue: Int = 0) =
        runBlocking { takeAsFlow(key, defValue).first() }

    fun take(key: PrefKey<String>, defValue: String = "") =
        runBlocking { takeAsFlow(key, defValue).first() }

    fun take(key: PrefKey<Long>, defValue: Long = 0L) =
        runBlocking { takeAsFlow(key, defValue).first() }

    fun take(key: PrefKey<Float>, defValue: Float = 0f) =
        runBlocking { takeAsFlow(key, defValue).first() }

    suspend inline fun <reified T : KeeperData> keep(key: PrefKey<String>, value: T) {
        val dataString = Json.encodeToString(value)
        keep(key, dataString)
    }

    inline fun <reified T> take(key: PrefKey<String>, defValue: T): T {
        val dataString = take(key)
        if (dataString.isEmpty()) return defValue

        val dataObj = Json.decodeFromString<T>(dataString)
        return dataObj
    }

    suspend inline fun <reified T> takeAsFlow(
        key: PrefKey<String>, defValue: T, crossinline action: (T) -> Unit
    ) = takeAsFlow(key) {
        if (it.isEmpty()) action(defValue)
        else action(Json.decodeFromString<T>(it))
    }
}

