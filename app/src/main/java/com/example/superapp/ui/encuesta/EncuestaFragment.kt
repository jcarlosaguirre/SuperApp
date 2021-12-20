package com.example.superapp.ui.encuesta

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBindings
import com.example.superapp.R
import com.example.superapp.databinding.FragmentEncuestaBinding
import kotlin.math.log
import android.widget.RadioGroup
import android.widget.RadioButton







class EncuestaFragment : Fragment() {

    private lateinit var encuestaViewModel: EncuestaViewModel
    private var _binding: FragmentEncuestaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        encuestaViewModel =
            ViewModelProvider(this).get(EncuestaViewModel::class.java)

        _binding = FragmentEncuestaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        encuestaViewModel.textViewColorResult = binding.textViewColorResult
        encuestaViewModel.rGroup = binding.radioGroupColors

        binding.checkBoxPrivacy.setOnCheckedChangeListener{ checkbox, isChecked ->

            val text = if (isChecked)  "Has aceptado las politicas de privacidad" else "Has rechazado las politicas de privacidad"
            Toast.makeText( activity, text, Toast.LENGTH_SHORT).show()
        }

        binding.switch1.setOnCheckedChangeListener{checkbox, isChecked ->

            val textResult = if (isChecked) "Todo está claro" else "No entiendo nada"
            binding.textViewGenial.apply {
                text = textResult
            }
        }

        binding.radioGroupColors.setOnCheckedChangeListener { group, checkedId ->

            var element: RadioButton = group.findViewById( checkedId )

            binding.textViewColorResult.apply {
                text = element.text.toString()
            }

            // This will get the radiobutton that has changed in its check state
//            val checkedRadioButton = group.findViewById<View>(checkedId) as RadioButton
//            // This puts the value (true/false) into the variable
//            val isChecked = checkedRadioButton.isChecked
//            // If the radiobutton that has changed in check state is now checked...
//            if (isChecked) {
//                // Changes the textview's text to "Checked: example radiobutton text"
//                tv.setText("Checked:" + checkedRadioButton.text)
//            }
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}