package Adaptadores

import Modelo.Registro
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.clienteapi2023.R

/**
 * Adaptador de la RecyclerView
 */
class MiAdaptadorRV (private var context: Context, private var datos: ArrayList<Registro>) : RecyclerView.Adapter<MiAdaptadorRV.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        holder.idR.text = datos[position].id.toString()
        holder.title.text = datos[position].title
        holder.body.text = datos[position].body
        holder.itemView.setOnClickListener {
            Toast.makeText(context, datos[position].toString(), Toast.LENGTH_SHORT).show()
        }
    }
    override fun getItemCount(): Int {
        return datos.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var idR: TextView = itemView.findViewById<View>(R.id.txtNombre) as TextView
        var title: TextView = itemView.findViewById<View>(R.id.txtEmail) as TextView
        var body: TextView = itemView.findViewById<View>(R.id.txtMovil) as TextView
    }
}