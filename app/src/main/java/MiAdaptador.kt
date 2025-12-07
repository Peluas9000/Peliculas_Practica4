package com.ayoub.peliculas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class MiAdaptador(private val context: Context, private val dataSource: List<Pelicula>) : BaseAdapter() {

    // Servicio para inflar (crear) vistas XML
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return dataSource.size // Devuelve 6 si tienes 6 películas
    }

    override fun getItem(position: Int): Pelicula {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 1. Inflamos el layout 'pelicula.xml' (NO activity_main)
        val view = convertView ?: inflater.inflate(R.layout.pelicula, parent, false)

        // 2. Buscamos los componentes dentro de ESE layout
        val tvTitulo = view.findViewById<TextView>(R.id.tvTitulo)
        val tvSubtitulo=view.findViewById<TextView>(R.id.subtitulo)
        val ivCaratula = view.findViewById<ImageView>(R.id.ivCaratula)

        // 3. Obtenemos los datos de la película actual
        val peliculaActual = getItem(position)

        // 4. Rellenamos los datos
        tvTitulo.text = peliculaActual.titulo


        // Asegúrate de usar imágenes reales o recursos de sistema para probar
        ivCaratula.setImageResource(peliculaActual.caratula)

        return view
    }
}