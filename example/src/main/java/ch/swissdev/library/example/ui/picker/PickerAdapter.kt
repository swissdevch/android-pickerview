package ch.swissdev.library.example.ui.picker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class PickerAdapter(
    @LayoutRes private val elementLayout: Int,
    val callback: (View) -> Unit
) : RecyclerView.Adapter<PickerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val items = listOf("Foo", "Bar", "Lorem ipsum", "Baz")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(elementLayout, parent, false) as TextView
        return ViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as TextView).text = items[position]
        holder.itemView.setOnClickListener {
            callback(it)
        }
    }

}
