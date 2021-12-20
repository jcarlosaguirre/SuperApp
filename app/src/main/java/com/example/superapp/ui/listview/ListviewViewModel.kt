package com.example.superapp.ui.listview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superapp.Producto
import com.example.superapp.R

class ListviewViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }


    /*Creamos un par de ptroductos*/
    val producto = Producto("Cámara", "100.0€", "Cámara último modelo", R.drawable.camara)
    val producto2 = Producto("PC", "1000.0€", "16 GB RAM", R.drawable.pc)

    private var _listaProductos : ArrayList<Producto> = arrayListOf( producto, producto2 )

    /*Añadimos los productos al arrayList*/
//    listaProductos.add(producto)
//    listaProductos.add(producto2)

    val text: LiveData<String> = _text
    val listaProductos : ArrayList<Producto> = _listaProductos
}