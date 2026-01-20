package com.ayoub.peliculas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat // IMPORTANTE: Importar esto
import com.ayoub.peliculas.R // Asegúrate de importar tu R

class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // No hace falta setContentView si usas android.R.id.content

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(android.R.id.content, ConfigFragment())
                .commit()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Configuración" // Opcional: título de la barra
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    // --- ESTA ES LA PARTE QUE TE FALTA ---
    class ConfigFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            // Aquí enlazamos con tu archivo XML de preferencias
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}