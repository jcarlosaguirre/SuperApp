package com.example.superapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.superapp.R
import com.example.superapp.databinding.ActivityProductoBinding

class ProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        binding = ActivityProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var imagenProducto: Int = intent.getIntExtra("imgID", 0)
        var nombreProducto: String? = intent.getStringExtra("nombre")
        var precioProducto: String? = intent.getStringExtra("precio")
        var descProducto: String? = intent.getStringExtra("desc")

        binding.nombreProducto.setText( nombreProducto )
        binding.precioProducto.setText( precioProducto )
        binding.descProducto.setText( descProducto )
        binding.imagenProducto.setImageResource( imagenProducto )

//        println( savedInstanceState )
//
//        if (userNameInput != null) {
//            println("dentro")
//            textViewName.apply {
//                text = userNameInput
//            }
//        }
    }
}