@file:Suppress("unused")

package com.ckgin.keeper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


typealias Keeper = DataStore<Preferences>
typealias PrefKey<T> = Preferences.Key<T>

//flow====================================================================================flow//
fun <T> Keeper.takeAsFlow(key: PrefKey<T>, defValue: T) = takePrefAsFlow(key, defValue)

suspend fun <T> Keeper.collect(key: PrefKey<T>, defValue: T, action: (T) -> Unit) =
    takePrefAsFlow(key, defValue).collect(action)


//suspend==============================================================================suspend//
suspend fun <T> Keeper.keep(key: PrefKey<T>, value: T) = keepPref(key, value)

suspend fun <T> Keeper.take(key: PrefKey<T>, defValue: T) = data.first()[key] ?: defValue


//blocking============================================================================blocking//
fun <T> Keeper.takeBlocking(key: PrefKey<T>, defValue: T) =
    runBlocking { takeAsFlow(key, defValue).first() }


//private==============================================================================private//
private fun <T> Keeper.takePrefAsFlow(key: PrefKey<T>, defValue: T) =
    data.map { it[key] ?: defValue }

private suspend fun <T> Keeper.keepPref(key: PrefKey<T>, value: T) = edit { it[key] = value }

