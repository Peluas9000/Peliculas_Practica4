package com.ayoub.peliculas

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MiAdaptador(private val context: Context, private val dataSource: List<Pelicula>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Pelicula {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 1. Inflamos el layout correcto 'pelicula.xml'
        val view = convertView ?: inflater.inflate(R.layout.pelicula, parent, false)

        // 2. Buscamos los componentes (IDs deben coincidir con pelicula.xml)
        val tvTitulo = view.findViewById<TextView>(R.id.tvTitulo)
        val tvSubtitulo = view.findViewById<TextView>(R.id.tvSubtitulo) // Corregido: ID coincide con XML
        // val tvSinopsis = view.findViewById<TextView>(R.id.tvSinopsis) // Opcional si quieres mostrarla
        val ivCaratula = view.findViewById<ImageView>(R.id.ivCaratula)

        // 3. Obtenemos los datos
        val peliculaActual = getItem(position)

        // 4. Rellenamos los datos
        tvTitulo.text = peliculaActual.titulo
        tvSubtitulo.text = peliculaActual.director // O lo que quieras mostrar ahí
        ivCaratula.setImageResource(peliculaActual.caratula)

        // 5. LÓGICA DEL COLOR DE FONDO
        // Usamos la propiedad 'seleccionada' del objeto
        if (peliculaActual.seleccionado) {
            view.setBackgroundColor(Color.GREEN)
        } else {
            view.setBackgroundColor(Color.TRANSPARENT) // O Color.WHITE, el color por defecto
        }

        // Añadimos la logica del botón de los 3 puntos
        val btnMenu = view.findViewById<ImageButton>(R.id.btnTresPuntos)
        btnMenu.setOnClickListener { view ->
            val popup = android.widget.PopupMenu(context, view)
            popup.menuInflater.inflate(R.menu.menupopup, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.popup_favorito -> {
                        Toast.makeText(context, "Favorito: ${peliculaActual.titulo}", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.popup_ocultar -> {
                        Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
        //para evitar que el botón se quede bloqueado
        btnMenu.isFocusable = false


        return view
    }
}