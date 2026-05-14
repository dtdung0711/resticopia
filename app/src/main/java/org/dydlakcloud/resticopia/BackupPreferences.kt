package org.dydlakcloud.resticopia

import android.content.Context
import android.content.SharedPreferences

/**
 * Helper class for managing backup constraint preferences.
 * Provides centralized access to backup-related settings.
 */
object BackupPreferences {
    private const val PREFS_NAME = "MyPrefs"
    private const val KEY_REQUIRE_CHARGING = "backup_require_charging"
    private const val KEY_ADD_TAGS = "backup_add_tags"
    private const val KEY_ALLOW_CELLULAR = "backup_allow_cellular"

    /**
     * Gets the SharedPreferences instance.
     */
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Checks if scheduled backups should only run when device is charging.
     * 
     * @param context The application context
     * @return true if backups require charging, false otherwise (default: false)
     */
    fun requiresCharging(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_REQUIRE_CHARGING, false)
    }

    /**
     * Sets whether scheduled backups should only run when device is charging.
     * 
     * @param context The application context
     * @param required true to require charging, false otherwise
     */
    fun setRequiresCharging(context: Context, required: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_REQUIRE_CHARGING, required).apply()
    }


    /**
     * Checks if manual and scheduled backups will be tagged
     *
     * @param context The application context
     * @return true if backups require tag, false otherwise (default: false)
     */
    fun requiresTag(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_ADD_TAGS, false)
    }

    /**
     * Sets whether manual or scheduled backups should be tagged
     *
     * @param context The application context
     * @param required true to require charging, false otherwise
     */
    fun setAddTag(context: Context, required: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_ADD_TAGS, required).apply()
    }

    /**
     * Checks if backups are allowed over cellular data.
     * 
     * @param context The application context
     * @return true if cellular data is allowed, false for WiFi only (default: false - WiFi only)
     */
    fun allowsCellular(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_ALLOW_CELLULAR, false)
    }

    /**
     * Sets whether backups are allowed over cellular data.
     * 
     * @param context The application context
     * @param allowed true to allow cellular data, false for WiFi only
     */
    fun setAllowsCellular(context: Context, allowed: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_ALLOW_CELLULAR, allowed).apply()
    }
}

