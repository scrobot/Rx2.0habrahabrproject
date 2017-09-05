package mobile.geekbit.rx20habrahabrproject.backpressure

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mobile.geekbit.rx20habrahabrproject.R
import org.jetbrains.anko.find

/**
 * Created by aleksejskrobot on 05.09.17.
 */

class BackpressureAdapter: RecyclerView.Adapter<BackpressureAdapter.BackpressureHolder>() {

    private val list: MutableList<Int?> = ArrayList()

    fun addItem(int: Int?) {
        list.add(int)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BackpressureHolder?, position: Int) {
        holder?.text?.text = list[position].toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BackpressureHolder =
            BackpressureHolder(LayoutInflater.from(parent?.context).inflate(R.layout.backpressure_adapter, null))

    override fun getItemCount(): Int = list.size


    inner class BackpressureHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val text: TextView = view.find(R.id.text)
    }
}
