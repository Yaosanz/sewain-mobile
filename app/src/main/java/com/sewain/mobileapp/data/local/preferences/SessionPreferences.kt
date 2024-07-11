package com.sewain.mobileapp.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sewain.mobileapp.data.local.model.SessionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "session")

class SessionPreferences private constructor(private val datastore: DataStore<Preferences>) {
    private val ID_KEY = stringPreferencesKey("id")
    private val TOKEN_KEY = stringPreferencesKey("token")
    private val IS_SHOP_KEY = booleanPreferencesKey("is_shop")

    fun getSession(): Flow<SessionModel> {
        return datastore.data.map { data ->
            SessionModel(
                data[ID_KEY] ?: "",
                data[TOKEN_KEY] ?: "",
                data[IS_SHOP_KEY] ?: false,
            )
        }
    }

    suspend fun saveSession(sessionModel: SessionModel) {
        datastore.edit { data ->
            data[ID_KEY] = sessionModel.id
            data[TOKEN_KEY] = sessionModel.token
            data[IS_SHOP_KEY] = sessionModel.isShop
        }
    }

    suspend fun logout() {
        datastore.edit { data ->
            data.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SessionPreferences? = null

        fun getInstance(datastore: DataStore<Preferences>): SessionPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SessionPreferences(datastore)
                INSTANCE = instance
                instance
            }
        }
    }
}