package com.example.superapp.ui.listview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.superapp.databinding.FragmentListviewBinding
import android.content.Intent
import android.widget.ListView
import com.example.superapp.AdaptadorListView
import com.example.superapp.ProductoActivity
import com.example.superapp.R


class ListviewFragment : Fragment() {

    // Modelo que incluye datos para el fragment
    private lateinit var listviewViewModel: ListviewViewModel

    // Binding
    private var _binding: FragmentListviewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        listviewViewModel =
            ViewModelProvider(this).get(ListviewViewModel::class.java)

        _binding = FragmentListviewBinding.inflate(inflater, container, false)

        /*Hacemos que el ListView responda ante los clicks*/
        binding.listView.isClickable = true
        /*Y le asignamos el adaptador que hemos creado previamente*/
        binding.listView.adapter = activity?.let { AdaptadorListView (it, listviewViewModel.listaProductos) }


        /*Este listener tenemos que hacerlo solo en el caso de que cuando pinchemos en el listView
        queramos que se cargue otra actividad con los detalles del producto, para ello tendremos
        que crear previamente El activity ProductosActivity y su layout */
        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(
                activity,
                ProductoActivity::class.java
            )
            i.putExtra("nombre", listviewViewModel.listaProductos[position].nombre)
            i.putExtra("precio", listviewViewModel.listaProductos[position].precio)
            i.putExtra("desc", listviewViewModel.listaProductos[position].desc)
            i.putExtra("imgID", listviewViewModel.listaProductos[position].imgID)

            startActivity( i )
        }

        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}