package shafat.projects.assesment.utils.sharedpreference


import android.content.Context


inline fun <reified T : Any> Context.loadSp(key: String): T? {
    if (!this.prefs.contains(key)) return null

    val result = when (T::class) {
        Boolean::class -> this.prefs.getBoolean(key, false)
        Int::class -> this.prefs.getInt(key, 0)
        Long::class -> this.prefs.getLong(key, 0)
        Float::class -> this.prefs.getFloat(key, 0.0f)
        String::class -> this.prefs.getString(key, "")
        else -> {
            throw Exception("Unsupported Type")
        }
    }

    return result as T
}

fun Context.saveSp(key: String, value: Any) {
    with(prefs.edit()) {
        when (value) {
            is Boolean -> putBoolean(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is String -> putString(key, value)
            else -> throw Exception("Unsupported Type")
        }

        commit()
    }
}