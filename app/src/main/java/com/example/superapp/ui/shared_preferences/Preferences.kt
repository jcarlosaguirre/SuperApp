package com.example.superapp.ui.shared_preferences

import android.content.Context

class Preferences(context: Context) {

    val PREF_NOMBRE: String = "MIS DATOS"
    val PREF_COLOR: String = "MI COLOR"

    val storage = context.getSharedPreferences(PREF_NOMBRE, Context.MODE_PRIVATE)


    fun guardarDatos( nombre: String, color: Int ){
        storage.edit().putString(PREF_NOMBRE, nombre).apply()
        storage.edit().putInt(PREF_COLOR, color).apply()
    }

    fun borrarDatos(){
        storage.edit().remove(PREF_NOMBRE).apply()
        storage.edit().remove(PREF_COLOR).apply()
    }

    fun getNombre(): String{
        return storage.getString(PREF_NOMBRE, "Sin rellenar")!!
    }

    fun getColor(): Int{
        return storage.getInt(PREF_COLOR, 0)
    }

}