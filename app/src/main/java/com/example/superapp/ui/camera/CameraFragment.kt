package com.example.superapp.ui.camera

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.superapp.databinding.FragmentCameraBinding
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class CameraFragment : Fragment() {

    val Fragment.packageManager get() = activity?.packageManager

    // Binding
    private lateinit var _binding: FragmentCameraBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var photoFile: File? = null


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            111 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    abrirCamara()
                } else {
                    Toast.makeText(
                        activity,
                        "Permisos rechazados la primera vez",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
            else -> {
                // Vacio para controlar excepciones
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        binding.cameraButton.setOnClickListener { this.comprobarPermisos(0) }
        binding.videoButton.setOnClickListener { this.comprobarPermisos(1) }

        val root: View = binding.root
        return root
    }

    fun comprobarPermisos(type: Int) {

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            /*Permiso no aceptado todavía esto SOLO ocurre la primera vez que el usuario accede a esta función.
            ¡¡CUIDADO!! porque en el caso de que los haya rechazado tendrá que activar la cámara manualmente,
            ya que no se los podemos volver a pedir*/
            pedirPermisoCamara()
        } else {

            /* abrir cámara, llamaremos a ese método siempre y cuando los permisos estén aceptados */
            if(type == 0) abrirCamara()
            else abrirCamaraVideo()
        }
    }


    fun pedirPermisoCamara() {
        /*Aquí vamos a comprobar si el permiso ha sido rechazado*/
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            )
        ) {
            /*Si llegamos a este punto es porque el usuario ha rechazado aceptar los permisos, NOOOOOOO podemos volver * intentarlo pero podemos darle un mensaje para que vaya a ajustes y manualmente los cambie*/
            Toast.makeText(
                activity,
                "No tienes permisos para abrir la cámara ve a ajustes",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            /*Todavía no los ha rechazado ni aceptado*/
            //Pedimos el permismo
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                111
            )
        }
    }


    // Inicia el Intent para fotos o videos
    private fun dispatchTakeMediaIntent(type: String) {

        if( type == "photo" ){

            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->

                takePictureIntent.resolveActivity(packageManager!!).also {
                    photoFile = try {
                        createMediaFile("fotoPrueba", type)
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        Toast.makeText(activity, "Error al guardar la captura", Toast.LENGTH_SHORT).show()
                        null
                    }

                    // Continue only if the File was successfully created
                    photoFile?.also {

                        val fileProvider = FileProvider.getUriForFile(
                            requireActivity(),
                            "com.example.superapp.fileprovider",
                            photoFile!!
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

                        if(takePictureIntent.resolveActivity(packageManager!!) != null)
                            getResult.launch( takePictureIntent )
                        else
                            Toast.makeText(activity, "Error al abrir la camara", Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }
        else{

            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
                // Ensure that there's a camera activity to handle the intent
                takeVideoIntent.resolveActivity(packageManager!!)?.also {
                    // Create the File where the photo should go
                    val videoFile: File? = try {
                        createMediaFile("videoPrueba", type )
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    videoFile?.also {
                        val videoURI: Uri = FileProvider.getUriForFile(
                            requireActivity(),
                            "com.example.superapp.fileprovider",
                            videoFile
                        )
                        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
                        startActivity(takeVideoIntent)
                    }
                }
            }

        }
    }


    fun abrirCamara() {

        dispatchTakeMediaIntent("photo")
    }

    fun abrirCamaraVideo() {

        dispatchTakeMediaIntent("video")
    }


    // Función para crear el archivo que contiene la foto o video capturados
    @Throws(IOException::class)
    private fun createMediaFile( fileName: String, type: String ): File {
        // Create an image file name
        val timeStamp: String = "_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val suffix = if (type == "photo") ".jpg" else ".mp4"

        val filesDirectory = requireContext().getExternalFilesDir("my_media")
        return File.createTempFile(fileName + timeStamp, suffix, filesDirectory)
    }


    // Función para recuperar la imagen y montarla en el fragment
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {

                val imageBitmap = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                binding.imageView2.setImageBitmap(imageBitmap)

                Toast.makeText(activity, "Foto hecha!", Toast.LENGTH_SHORT).show()
            }
        }


//    private fun galleryAddPic() {
//
//        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
//            val f = File(photoFile!!.absolutePath)
//            mediaScanIntent.data = Uri.fromFile(f)
//            sendBroadcast(mediaScanIntent)
//        }
//    }


}