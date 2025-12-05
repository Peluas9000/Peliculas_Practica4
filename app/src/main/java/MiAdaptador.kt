import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ayoub.peliculas.Pelicula
import com.ayoub.peliculas.R

class MiAdaptador(private val context: Context, private val dataSource: List<Pelicula>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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

        val filaView = convertView ?: inflater.inflate(R.layout.activity_main, parent, false)
        val textView: TextView = filaView.findViewById(R.id.textoFila)
        val imageView: ImageView = filaView.findViewById(R.id.imagenFila)
        val pais = getItem(position)
        textView.text = pais.nombre
        imageView.setImageResource(pais.idImagen)
        return filaView
    }
}