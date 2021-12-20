package com.example.superapp.ui.encuesta

import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EncuestaViewModel : ViewModel() {

    var textViewColorResult: TextView? = null
    var rGroup: RadioGroup? = null

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text




    private fun setColor( result: TextView, rButton: RadioButton ){

//        var chosenButton: Int = rGroup.checkedRadioButtonId


//        textViewColorResult.apply {
//            text = chosenButton
//        }
    }
}