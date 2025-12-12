package com.ayoub.peliculas

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Declaramos lista y adaptador a nivel de clase para acceder desde el resultado
    private lateinit var lista: ArrayList<Pelicula>
    private lateinit var adaptador: MiAdaptador

    // LANZADOR: Se encarga de recibir la respuesta cuando volvemos del Detalle
    private val launcherDetalle = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            // Recuperamos los datos que nos envía DetalleActivity
            val posicion = data?.getIntExtra("POSICION", -1) ?: -1
            val estaSeleccionada = data?.getBooleanExtra("ESTADO_CHECKBOX", false) ?: false

            if (posicion != -1) {
                // Actualizamos el objeto en la lista original
                lista[posicion].seleccionado = estaSeleccionada
                // Avisamos al adaptador para que repinte la lista (y aplique el color rojo)
                adaptador.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Crear los datos
        lista = ArrayList()
        lista.add(Pelicula("F1", "James Cameron", 2023, "Pues es una pelicula", R.drawable.f1))
        lista.add(Pelicula("JUJUTSU KAISEN", "Ayoub", 2025, "Anime de accion", R.drawable.jujutsu))
        lista.add(Pelicula("SUPERMAN", "Henry Calvin", 2023, "Pelicula DC", R.drawable.superman))
        lista.add(Pelicula("Spider Man no Way Home", "Zendaya", 2021, "Pelicula Marvel", R.drawable.spiderman))
        lista.add(Pelicula("Demon Slayer Castillo Infinito", "Ufotable", 2025, "Pelicula Anime Ufotable", R.drawable.demon))
        lista.add(Pelicula("Weapons", "Zach Cregger", 2025, "Misterio escolar", R.drawable.weapons))


        // añadimos el botón para el popup menu
        val btnPopup = findViewById<Button>(R.id.btnFiltroRapido)

        btnPopup.setOnClickListener { view ->
            // Le pasamos al PopupMenu el contexto (this) y el botón que acabo de pulsar donde va anclado el menu(view)
            val popup = android.widget.PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.menupopup, popup.menu) // Creamos la esctructura del menu
            // Gestionamos el clic y mostramos el menu
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.popup_favorito -> {
                        Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.popup_ocultar -> {
                        Toast.makeText(this, "Ocultando elementos...", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
            popup.show() //debemos mostrar el menu
        }



        // 2. Configurar ListView y Adaptador
        val listView = findViewById<ListView>(R.id.lvPeliculas)
        adaptador = MiAdaptador(this, lista)
        listView.adapter = adaptador


        registerForContextMenu(listView)

        // 3. Clic en un elemento
        listView.setOnItemClickListener { parent, view, position, id ->
            val peliculaSeleccionada = lista[position]

            val intent = Intent(this, DetalleActivity::class.java)
            // Pasamos la película y SU POSICIÓN para saber cuál actualizar al volver
            intent.putExtra("EXTRA_PELICULA", peliculaSeleccionada)
            intent.putExtra("POSICION", position)

            // IMPORTANTE: Usamos el launcher, NO startActivity
            launcherDetalle.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        return true //para que muestre el menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_config -> {
                Toast.makeText(this, "Abriendo Configuración...", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_item_compartir -> {
                Toast.makeText(this, "Compartiendo...", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.submenu_precio -> { // directamente capturamos el hijo, no el submenú intermedio
                lista.sortBy{it.anio }
                adaptador.notifyDataSetChanged() // refrescar
                Toast.makeText(this, "Ordenado por nota", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.submenu_nombre -> {
                lista.sortBy { it.titulo }
                adaptador.notifyDataSetChanged() // refrescar
                Toast.makeText(this, "Ordenado por titulo", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        // Titulo del menú contextual flotante
        menu.setHeaderTitle("Acciones del Producto")

        // Inflamos el XML de menú contextual (menucontextual.xml)
        menuInflater.inflate(R.menu.menucontextual, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {


        val info = item.menuInfo as? AdapterView.AdapterContextMenuInfo

        return when (item.itemId) {
            R.id.ctx_eliminar -> {
                if (info != null) {
                    // info.position nos dice qué fila del ListView se pulsó
                    val producto = lista[info.position]
                    //Borro el producto
                    lista.removeAt(info.position) // Borra el dato de la lista
                    adaptador.notifyDataSetChanged()   // Aviso al GridView para que se refresque
                    Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }



}