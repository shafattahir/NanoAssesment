package shafat.projects.assesment.utils.sharedpreference


import android.content.Context
import android.content.SharedPreferences
import java.lang.Exception
import kotlin.reflect.KClass


val Context.prefs: SharedPreferences get() = this.getSharedPreferences(
    "preferences", Context.MODE_PRIVATE)

fun Context.save(key: String, value: Any) {
    prefs.save(key, value)
}


fun Context.safeSave(key: String, value: Any) {
    prefs.safeSave(key, value)
}


inline fun <reified T: Any> Context.load(key: String): T? {
    return prefs.load(key)
}

inline fun <reified T: Any> Context.safeLoad(key: String): T? {
    return prefs.safeLoad(key)
}


inline fun <reified T: Any> Context.delete(key: String) {
    return prefs.delete<T>(key)
}


inline fun <reified T: Any> Context.safeDelete(key: String) {
    return prefs.safeDelete<T>(key)
}


fun SharedPreferences.safeSave(key: String, value: Any) {
    safe { save(key, value) }
}

inline fun <reified T: Any> SharedPreferences.safeLoad(key: String): T? {
    return safeLoad(key, T::class)
}

fun SharedPreferences.save(key: String, value: Any) {
    with (edit()) {
        when (value) {
            is Boolean      -> putBoolean(key, value)
            is Int          -> putInt(key, value)
            is Long         -> putLong(key, value)
            is Float        -> putFloat(key, value)
            is String       -> putString(key, value)
            else -> putString(key, objectToString(value))
        }

        commit()
    }
}

inline fun <reified T: Any> SharedPreferences.safeDelete(key: String) {
    try {
        delete<T>(key)
    } catch (e: Exception) {
        // Do nothing
    }
}

inline fun <reified T: Any> SharedPreferences.delete(key: String) {
    return delete(key, T::class)
}

fun <T: Any> SharedPreferences.delete(key: String, type: KClass<T>) {
    val editor = this.edit()

    when(type) {
        Boolean::class        -> editor.remove(key)
        Int::class            -> editor.remove(key)
        Long::class           -> editor.remove(key)
        Float::class          -> editor.remove(key)
        String::class         -> editor.remove(key)
        else -> editor.remove(key)
    }

    editor.apply()
}

inline fun <reified T: Any> SharedPreferences.load(key: String): T? {
    return load(key, T::class)
}

fun <T: Any> SharedPreferences.safeLoad(key: String, type: KClass<T>): T? {
    return try {
        load(key, type)
    } catch (e: Exception) {
        null
    }
}

fun <T: Any> SharedPreferences.load(key: String, type: KClass<T>): T? {
    if(!contains(key)) return null

    val result =  when(type) {
        Boolean::class        -> getBoolean(key, false)
        Int::class            -> getInt(key, 0)
        Long::class           -> getLong(key, 0)
        Float::class          -> getFloat(key, 0.0f)
        String::class         -> getString(key, "")
        else -> stringToObject(getString(key, ""), type)
    }

    return result as T?
}