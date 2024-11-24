package com.naulian.keeper

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.datastore by preferencesDataStore(name = "keeper")

@Module
@InstallIn(SingletonComponent::class)
object KeepModule {

    @Provides
    @Singleton
    fun providesDb(@ApplicationContext context: Context) = context.datastore

    @Provides
    @Singleton
    fun providesKeeper(datastore: DsPrefs) = Keeper(datastore)
}
