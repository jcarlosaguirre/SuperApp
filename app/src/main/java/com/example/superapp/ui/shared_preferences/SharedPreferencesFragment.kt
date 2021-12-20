package com.example.superapp.ui.shared_preferences

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.superapp.R
import com.example.superapp.databinding.FragmentSensoresBinding
import com.example.superapp.databinding.FragmentSharedPreferencesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PreferencesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SharedPreferencesFragment() : Fragment() {


    // Binding
    private lateinit var _binding: FragmentSharedPreferencesBinding


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Preferencias
    lateinit var preferences : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSharedPreferencesBinding.inflate(inflater, container, false)

        preferences = Preferences( requireContext() )

        binding.buttonGuardar.setOnClickListener{
            guardarDatos()
        }

        binding.buttonBorrar.setOnClickListener{
            borrarDatos()
        }

        binding.buttonMostrar.setOnClickListener{
            mostrarValor( preferences.getNombre() )
        }

        val root = binding.root

        // Inflate the layout for this fragment
        return root
    }

    private fun guardarDatos(){

        if( binding.editText1.text.toString().isNotEmpty() ){
            preferences.guardarDatos( binding.editText1.text.toString(), preferences.getColor() )
        }

        if( binding.editText2.text.toString().isNotEmpty() ){

            val colores = mapOf<String, Int>(
                "rojo" to Color.RED,
                "azul" to Color.BLUE,
                "verde" to Color.GREEN
            )

            try{

                var nombre = binding.editText1.text.toString()
                var color = binding.editText2.text.toString().lowercase()

                preferences.guardarDatos( nombre, colores[ color ]!! )
                binding.editText2.setBackgroundColor( colores[ color ]!! )
            }
            catch (error: Exception){

            }
        }
    }

    private fun borrarDatos(){

        if( binding.editText1.text.toString().isNotEmpty() ){
            preferences.borrarDatos()
            mostrarValor("Datos borrados")
        }
    }

    private fun mostrarValor( message: String ){

        var alerta = AlertDialog.Builder( requireContext() )
        alerta.setTitle(preferences.PREF_NOMBRE)
        alerta.setMessage( message )
        val dialogo = alerta.create()
        dialogo.show()
    }

    companion object {


    }
}