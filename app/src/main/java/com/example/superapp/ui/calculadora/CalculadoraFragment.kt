package com.example.superapp.ui.calculadora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.superapp.databinding.FragmentCalculadoraBinding
import java.lang.Exception
import android.widget.Button


class CalculadoraFragment : Fragment() {

    private var num1: EditText? = null
    private var num2: EditText? = null

    // Modelo que incluye datos para el fragment
    private lateinit var calculadoraViewModel: CalculadoraViewModel

    // Binding
    private var _binding: FragmentCalculadoraBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        calculadoraViewModel =
            ViewModelProvider(this).get(CalculadoraViewModel::class.java)

        _binding = FragmentCalculadoraBinding.inflate(inflater, container, false)

        binding.button1.setOnClickListener{ this.operate('+') }
        binding.button2.setOnClickListener{ this.operate('-') }
        binding.button3.setOnClickListener{ this.operate('*') }
        binding.button4.setOnClickListener{ this.operate('/') }



        val root: View = binding.root

        return root
    }





    fun operate( action: Char ) {

        num1 = binding.editTextNumber1
        num2 = binding.editTextNumber2
        var result = binding.textViewResult

        try {

            when ( action ) {
                '+' -> result.setText( ( num1!!.text.toString().toDouble() + num2!!.text.toString().toDouble() ).toString() )
                '-' -> result.setText( ( num1!!.text.toString().toDouble() - num2!!.text.toString().toDouble() ).toString() )
                '/' -> result.setText( ( num1!!.text.toString().toDouble() / num2!!.text.toString().toDouble() ).toString() )
                '*' -> result.setText( ( num1!!.text.toString().toDouble() * num2!!.text.toString().toDouble() ).toString() )
            }

        } catch ( err: Exception){

            result.setText( "Syntax error" )
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}