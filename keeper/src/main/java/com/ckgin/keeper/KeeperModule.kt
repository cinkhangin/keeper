package com.ckgin.keeper

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
object KeeperModule {

    @Provides
    @Singleton
    fun providesKeeper(@ApplicationContext context: Context) = context.datastore
}
