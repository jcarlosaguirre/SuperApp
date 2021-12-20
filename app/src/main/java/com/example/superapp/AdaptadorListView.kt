package com.example.superapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class AdaptadorListView(
    private val context: Activity,
    private val listaProductos: ArrayList<Producto>
) : ArrayAdapter<Producto>(context, R.layout.items, listaProductos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(
            R.layout.items,
            null
        )


        /*Aquí no podemos usar binding porque no tenemos un Activity asociado.*/
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtPrecio: TextView = view.findViewById(R.id.txtPrecio)
        val imgFoto: ImageView = view.findViewById(R.id.imgFoto)
        val producto = listaProductos[position]
        txtNombre.text = producto.nombre
        txtPrecio.text = producto.precio
        imgFoto.setImageResource(producto.imgID)

        return view
    }
}