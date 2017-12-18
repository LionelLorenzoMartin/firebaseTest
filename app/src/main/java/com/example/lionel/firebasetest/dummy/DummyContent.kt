package com.example.lionel.firebasetest.dummy

import java.util.ArrayList
import java.util.HashMap
import com.example.lionel.firebasetest.Contactos
object DummyContent {

    val ITEMS: MutableList<DummyItem> = ArrayList()

    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    private val COUNT = 8

    init {

    }

    fun clearItems(){
        ITEMS.clear()
    }

    fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    fun createDummyItem(c: Contactos): DummyItem {
        return DummyItem(c.nombre, ""+c.telefono , makeDetails(c))
    }

    private fun makeDetails(c: Contactos): String {
        val builder = StringBuilder()
        builder.append("Nombre contacto: ").append(c.nombre)
        builder.append("\nTelefono: ").append(c.telefono)
        return builder.toString()
    }

    data class DummyItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}