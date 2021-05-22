package com.example.grpc_chat_android

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceManager @Inject constructor(private val datastore: DataStore<Preferences>) {
    private val usernameKey = stringPreferencesKey("username")
    private val tokenKey = stringPreferencesKey("jwtToken")

    suspend fun saveUserPhone(phone: String) {
        datastore.edit { user ->
            user[usernameKey] = phone
        }
    }

    suspend fun saveToken(token: String) {
        datastore.edit { jwt ->
            jwt[tokenKey] = token
        }
    }

    suspend fun getUserPhone() : String? {
        return datastore.data.first()[usernameKey]
    }

    suspend fun getToken() : String? {
        return datastore.data.first()[tokenKey]
    }
}