package com.example.grpc_chat_android.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.grpc_chat_android.models.ChatServiceGrpc
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import io.grpc.ManagedChannel
import io.grpc.android.AndroidChannelBuilder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatStub {
    @Provides
    @Singleton
    fun provideChannel(@ApplicationContext context: Context): ManagedChannel =
        AndroidChannelBuilder.forAddress("192.168.1.6", 8080).context(context).usePlaintext()
            .build()

    @Provides
    @Singleton
    fun provideStub(channel: ManagedChannel): ChatServiceGrpc.ChatServiceStub =
        ChatServiceGrpc.newStub(channel)

    @Provides
    @ViewModelScoped
    @AuthToken
    fun provideAuthToken(dataStore: DataStore<Preferences>): String {
        return runBlocking { dataStore.data.first()[stringPreferencesKey("jwtToken")] ?: "" }
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthToken
