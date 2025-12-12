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

        // Recuperar datos
        val pelicula = intent.getSerializableExtra("EXTRA_PELICULA") as? Pelicula
        val posicionRecibida = intent.getIntExtra("POSICION", -1)

        val checkBox = findViewById<CheckBox>(R.id.seleccionar)

        if (pelicula != null) {
            val ivFoto = findViewById<ImageView>(R.id.portada)
            val tvTitulo = findViewById<TextView>(R.id.titulo)
            val tvSubtitulo = findViewById<TextView>(R.id.subtitulo)
            val tvSinopsis = findViewById<TextView>(R.id.sinopsis)

            ivFoto.setImageResource(pelicula.caratula)
            tvTitulo.text = pelicula.titulo
            tvSubtitulo.text = "${pelicula.director} - ${pelicula.anio}"
            tvSinopsis.text = pelicula.sinopsis

            // IMPORTANTE: Marcar el checkbox si la película ya venía seleccionada
            checkBox.isChecked = pelicula.seleccionado
        }

        // Botón Volver
        findViewById<Button>(R.id.botonVolver).setOnClickListener {
            val intentResultado = Intent()

            // Devolvemos el estado ACTUAL del checkbox (true o false)
            intentResultado.putExtra("ESTADO_CHECKBOX", checkBox.isChecked)
            intentResultado.putExtra("POSICION", posicionRecibida)

            // Confirmamos quetodo fue bien
            setResult(RESULT_OK, intentResultado)

            // Cerramos la actividad actual para volver al Main
            finish()
        }
    }
}