package com.example.superapp.ui.sensores

import android.content.Context.SENSOR_SERVICE
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import com.example.superapp.databinding.FragmentSensoresBinding

class SensoresFragment : Fragment(), SensorEventListener {

    // Modelo que incluye datos para el fragment
    private lateinit var sensoresViewModel: SensoresViewModel
    private lateinit var sensorManager: SensorManager

    // Binding
    private var _binding: FragmentSensoresBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private fun pruebaSensores() {

        /*Creamos el sensor con los datos por defecto del servicio*/
        sensorManager =
            activity?.getSystemService(SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> =
            sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sensor in deviceSensors) {
            Log.i("SENSORES", sensor.name)
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)?.also { light_sensor ->
            sensorManager.registerListener(
                this,
                light_sensor,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)?.also { temp_sensor ->
            sensorManager.registerListener(
                this,
                temp_sensor,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    companion object {
        fun newInstance() = SensoresFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sensoresViewModel =
            ViewModelProvider(this).get(SensoresViewModel::class.java)

        _binding = FragmentSensoresBinding.inflate(inflater, container, false)


        this.pruebaSensores()

        val root: View = binding.root
        return root

    }

    override fun onSensorChanged(evento: SensorEvent?) {

        /*Comprobamos que el sensor que hemos registrado es el acelerómetro*/
        if (evento?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            //Log.d("Main", "onSensorChanged: sides ${event.values[0]} front/back ${event.values[1]} ")
            // Lados = Movimiento del terminal izquierda(10) y derecha (-10)
            val lados = evento.values[0] //El evento tiene dos posiciones

            // la primera nos indical el movimiento de los Laterales. La siguiente el movimiento horizontal
            // ArribaAbajo = Movimiento del terminal arriba(10) plano(0) y abajo(-10)
            val arribaAbajo = evento.values[1]

            /*Esta fórmula la podemos variar en función de cuánto queremos que rote y se traslade el rectángulo con respecto a lo que nosotros movamos el terminal*/
            /*Para probarlo hay que hacer un cambio drástico. De 3 a 10 por ejemplo y de -10 a -100 sino no * se nota mucho*/
            binding.tvRectangulo.apply {
                rotationX = arribaAbajo * 3f
                rotationY = lados * 3f
                rotation = -lados
                translationX = lados *-10
                translationY = arribaAbajo * 10
            }

            // Changes the colour of the square if it's completely flat
            val color = if (arribaAbajo.toInt() == 0 && lados.toInt() == 0) Color.GREEN else Color.RED
            binding.tvRectangulo.setBackgroundColor(color)
            binding.tvRectangulo.text = "arriba/abajo ${ arribaAbajo.toInt() }\n izquierda/derecha ${ lados.toInt() }"

        }

        else if(evento?.sensor?.type == Sensor.TYPE_LIGHT){
            print( evento.values[0] )
            binding.lightValue.text = "LIGHT: ${ evento.values[0] }"
        }

        else if(evento?.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE){
            print( evento.values[0] )
            binding.tempValue.text = "TEMP: ${ evento.values[0] }"
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

}