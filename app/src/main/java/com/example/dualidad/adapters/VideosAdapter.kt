package com.example.dualidad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dualidad.databinding.ItemMeasureBinding
import com.example.dualidad.models.Measure

class VideosAdapter(
    private val dataSet: List<Measure>,
    private val listener: (Measure) -> Unit
) :
    RecyclerView.Adapter<VideosAdapter.VideosHolder>() {

    inner class VideosHolder(private val binding: ItemMeasureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Measure) {
            binding.title.text = item.title
            binding.desc.text = item.desc

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosHolder = VideosHolder(
        ItemMeasureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: VideosHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    override fun getItemCount(): Int = dataSet.size

}