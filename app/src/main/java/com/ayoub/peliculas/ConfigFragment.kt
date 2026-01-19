package com.ayoub.peliculas.com.ayoub.peliculas
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.ayoub.peliculas.R


class ConfigFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}