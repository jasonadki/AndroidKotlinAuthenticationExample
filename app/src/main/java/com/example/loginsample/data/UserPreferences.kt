package com.example.loginsample.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class UserPreferences(context: Context) {
    val dataStore: DataStore<Preferences> = context.dataStore



    val authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    val refreshToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_REFRESH]
        }


    suspend fun saveAuthToken(authToken: String){
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }


    suspend fun clear(){
        dataStore.edit {
            preferences -> preferences.clear()
        }
    }

    suspend fun saveRefreshToken(refToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_REFRESH] = refToken
        }

    }

    companion object{
        private val KEY_AUTH = stringPreferencesKey("key_auth")
        private val KEY_REFRESH = stringPreferencesKey("key_refresh")
    }

}
