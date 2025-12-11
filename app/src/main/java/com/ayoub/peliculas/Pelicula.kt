package com.ayoub.peliculas

import java.io.Serializable

data class Pelicula (val titulo:String,
                     val director:String,
                     val anio:Int,
                     val sinopsis:String,
                     val caratula:Int,//R.drawable.imagen
                     var seleccionado: Boolean=false
                    ): Serializable