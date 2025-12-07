package com.ayoub.peliculas

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Crear los datos (Materia prima)
        val lista = ArrayList<Pelicula>()
        lista.add(Pelicula("F1", "James Cameron", 2023,"Pues es una pelicula",R.drawable.f1))
        lista.add(Pelicula("JUJUTSU KAISEN", "Ayoub", 2025,"Anime de accion",R.drawable.jujutsu))
        lista.add(Pelicula("SUPERMAN", "Henry Calvin", 2023,"Pelicula DC",R.drawable.superman))
        lista.add(Pelicula("Spider Man no Way Home", "Zendaya", 2021,"Pelicula Marvel",R.drawable.spiderman))

        // 2. Encontrar la lista en la pantalla (El marco)
        // ESTO DEBE COINCIDIR CON activity_main.xml
        val listView = findViewById<ListView>(R.id.lvPeliculas)

        // 3. Inicializar el adaptador (El operario)
        val adaptador = MiAdaptador(this, lista)

        // 4. CONEXIÓN FINAL: Unir la lista con el adaptador
        listView.adapter = adaptador




        listView.setOnItemClickListener { parent, view, position, id ->

            // 1. Obtener la película que se tocó (la posición nos la da el método)
            val peliculaSeleccionada = lista[position]

            // 2. Crear el Intent para viajar a la otra pantalla
            val intent = Intent(this, DetalleActivity::class.java)

            // 3. Meter la película completa en la "maleta" (Intent)
            // Esto funciona porque en Pelicula.kt pusimos ": Serializable"
            intent.putExtra("EXTRA_PELICULA", peliculaSeleccionada)

            // 4. Arrancar el viaje
            startActivity(intent)
        }
    }
}