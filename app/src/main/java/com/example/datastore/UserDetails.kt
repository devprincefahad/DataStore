package com.example.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class UserDetails(val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("pref")
        val USERNAME = stringPreferencesKey("USER_NAME")
        val AGE = intPreferencesKey("AGE")
    }

    suspend fun storeDetails(userName: String, age: Int) {
        context.dataStore.edit {
            it[USERNAME] = userName
            it[AGE] = age
        }
    }

    fun getName() = context.dataStore.data.map {
        it[USERNAME] ?: ""
    }

    fun getAge() = context.dataStore.data.map {
        it[AGE] ?: -1
    }

}