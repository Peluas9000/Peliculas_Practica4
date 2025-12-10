package com.ayoub.peliculas

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
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

        // 2. Configurar ListView y Adaptador
        val listView = findViewById<ListView>(R.id.lvPeliculas)
        adaptador = MiAdaptador(this, lista)
        listView.adapter = adaptador

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
}