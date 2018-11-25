package com.felixchi.qrcodeapp.helper

/*
    Ref:
    https://medium.com/@krupalshah55/manipulating-shared-prefs-with-kotlin-just-two-lines-of-code-29af62440285
    https://blogs.naxam.net/sharedpreferences-made-easy-with-kotlin-generics-extensions-6189d8902fb0
 */
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object PreferenceHelper {

    fun defaultPrefs(context: Context):SharedPreferences
            = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPrefs(context: Context, name: String):SharedPreferences
            = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }
    /*
        put key value pair in share prefs
     */
    operator fun SharedPreferences.set(key: String, value: Any?) {
        when(value) {
            is String? -> edit({ it.putString(key, value) })
            is Int -> edit({ it.putInt(key, value) })
            is Float -> edit({ it.putFloat(key, value) })
            is Boolean -> edit({ it.putBoolean(key, value) })
            is Long -> edit({ it.putLong(key, value) })
            else -> throw UnsupportedOperationException("${value?.javaClass} not yet implementation")
        }
    }

    operator inline fun <reified T: Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
        return when(T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int?: -1) as T?
            Float::class -> getFloat(key, defaultValue as? Float?: -1F) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean?: false) as T?
            Long::class -> getLong(key, defaultValue as? Long?: -1) as T?
            else -> throw UnsupportedOperationException("not yet implementation")
        }
    }
}