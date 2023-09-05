package com.example.filmust.workdata

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmust.databinding.ItemPageBinding

class PageAdapter(private val pages : List<PageData>) : RecyclerView.Adapter<PageItem>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageItem = PageItem(
        ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = pages.size

    override fun onBindViewHolder(holder: PageItem, position: Int) {
        holder.itemView.apply {
            holder.binding.pageImage.setImageResource(pages[position].image)
            holder.binding.pageTitle.text = pages[position].title
            holder.binding.pageSubtitle.text = pages[position].subtitle
        }
    }
}