package kkorionkk.test.bankcardtask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class QueryHistoryAdapter(private val history: List<Info>): RecyclerView
.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val historyView = LayoutInflater.from(parent.context)
        return ItemViewHolder(historyView, parent)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Info = history[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return history.size
    }




}