package kkorionkk.test.bankcardtask

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(R.layout.item, parent, false)) {
    private var mHeaderView: TextView? = null
    private var mDescriptionView: TextView? = null

    init {
        mHeaderView = itemView.findViewById(R.id.text_header)
        mDescriptionView = itemView.findViewById(R.id.text_description)
    }

    fun bind(item: Info) {
        mHeaderView?.text = item.scheme
        mDescriptionView?.text = item.bank?.name
    }
}