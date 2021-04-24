package com.example.grpc_chat_android.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Utils {

    @Singleton
    @Provides
    fun providesDataStore (@ApplicationContext context : Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    fun provideStringPreferenceKey(): Preferences.Key<String> {
    return stringPreferencesKey("user")
    }
}

 val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user")

