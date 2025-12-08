package com.ayoub.peliculas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Recuperar el objeto que nos enviaron
        // "EXTRA_PELICULA" es la clave secreta que usaremos en el Main para pasarlo
        val pelicula = intent.getSerializableExtra("EXTRA_PELICULA") as? Pelicula
        val select=findViewById<CheckBox>(R.id.seleccionar)
        // 2. Si la película llegó bien, rellenamos la pantalla
        if (pelicula != null) {
            val ivFoto = findViewById<ImageView>(R.id.portada)
            val tvTitulo = findViewById<TextView>(R.id.titulo)
            val tvSubtitulo = findViewById<TextView>(R.id.subtitulo)
            val tvSinopsis = findViewById<TextView>(R.id.sinopsis)

            ivFoto.setImageResource(pelicula.caratula)
            tvTitulo.text = pelicula.titulo
            tvSubtitulo.text = "${pelicula.director} - ${pelicula.anio}"
            tvSinopsis.text = pelicula.sinopsis
        }


        val eleccion=select.isChecked
        // 3. Botó n para cerrar
        findViewById<Button>(R.id.botonVolver).setOnClickListener {
            val intento= Intent(this, MainActivity::class.java)
            var favoritos=""
            if(eleccion){
                favoritos="true"
            }else{
                favoritos="false"
            }

            intento.putExtra("eleccion",favoritos)
            //esta la opcion finish() para volver
                startActivity(intento)
        }


    }
}