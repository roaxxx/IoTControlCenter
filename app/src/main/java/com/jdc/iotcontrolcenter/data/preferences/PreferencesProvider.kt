package com.jdc.iotcontrolcenter.data.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object PreferencesProvider {

    /**
     * Saves a value in SharedPreferences.
     *
     * @param context the application context.
     * @param key the key associated with the value.
     * @param value the value to be saved.
     */
    fun set(context: Context, key:String, value:String) {
        val editor = pref(context).edit()
        editor.putString(key,value).apply()
    }

    /**
     * Retrieves a String value from SharedPreferences.
     *
     * @param context the application context.
     * @param key the key associated with the value.
     * @return the retrieved String value, or an empty string if not found.
     */
    @SuppressLint("CommitPrefEdits")
    fun string(context: Context, key: String): String? {
        return pref(context).getString(key, "")
    }

    /**
     * Saves a Boolean value in SharedPreferences.
     *
     * @param context the application context.
     * @param key the key associated with the value.
     * @param value the Boolean value to be saved.
     */
    fun set(context: Context, key: String, value: Boolean) {
        val editor = pref(context).edit()
        editor.putBoolean(key, value).apply()
    }

    /**
     * Retrieves a Boolean value from SharedPreferences.
     *
     * @param context the application context.
     * @param key the key associated with the value.
     * @return the retrieved Boolean value, or false if not found.
     */
    fun bool(context: Context, key: String): Boolean? {
        return pref(context).getBoolean(key, false)
    }

    /**
     * Removes a value from SharedPreferences.
     *
     * @param context the application context.
     * @param key the key associated with the value to be removed.
     */
    fun remove(context: Context, key: String) {
        val editor = pref(context).edit()
        editor.remove(key).apply()
    }

    /**
     * Clears all values from SharedPreferences.
     *
     * @param context the application context.
     */
    fun clear(context: Context) {
        val editor = pref(context).edit()
        editor.clear().apply()
    }

    /**
     * Retrieves the default SharedPreferences instance.
     *
     * @param context the application context.
     * @return the SharedPreferences instance.
     */
    private fun pref(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}