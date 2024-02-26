package com.softeer.togeduck.ui.home.article_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.home.article_detail.RouteListModel
import com.softeer.togeduck.databinding.RvItemRouteListBinding
import com.softeer.togeduck.utils.ItemClick

class RouteListAdapter(private val items: List<RouteListModel>) :
    RecyclerView.Adapter<ViewHolder>() {
    private lateinit var binding: RvItemRouteListBinding
    var itemClick: ItemClick? = null
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RvItemRouteListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[holder.adapterPosition])
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)

        }
    }
}

class ViewHolder(private val binding: RvItemRouteListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RouteListModel) {
        binding.route = item
    }
}