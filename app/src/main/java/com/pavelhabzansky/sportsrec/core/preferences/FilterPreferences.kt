package com.pavelhabzansky.sportsrec.core.preferences

import android.content.Context
import android.content.SharedPreferences
import com.pavelhabzansky.domain.core.common.FilterOptions

class FilterPreferences(context: Context) {
    private val prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun storeFilterOptions(options: FilterOptions) {
        prefs.edit()
            .putBoolean(LOCAL_OPTION_KEY, options.local)
            .putBoolean(REMOTE_OPTION_KEY, options.remote)
            .apply()
    }

    fun loadFilterOptions(): FilterOptions {
        return FilterOptions(
            local = prefs.getBoolean(LOCAL_OPTION_KEY, true),
            remote = prefs.getBoolean(REMOTE_OPTION_KEY, true)
        )
    }

    companion object {
        const val PREFS_NAME = "FILTER_PREFERENCES"
        const val LOCAL_OPTION_KEY = "LOCAL"
        const val REMOTE_OPTION_KEY = "REMOTE"
    }
}