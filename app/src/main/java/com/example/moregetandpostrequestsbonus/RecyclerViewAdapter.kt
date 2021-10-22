package com.example.moregetandpostrequestsbonus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moregetandpostrequestsbonus.databinding.ItemRowBinding

class RecyclerViewAdapter (private var names : ArrayList<String>): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val name = names[position]
        holder.binding.apply {
            tv.text = name
        }
    }
    override fun getItemCount() = names.size
}