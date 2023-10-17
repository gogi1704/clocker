package com.l_george.clocker.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.l_george.clocker.data.models.WorldTimeModel
import com.l_george.clocker.databinding.ItemWorldLayoutBinding

class WorldAdapter : ListAdapter<WorldTimeModel, WorldAdapter.WorldViewHolder>(WorldCallBack()) {


    class WorldViewHolder(private val binding: ItemWorldLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WorldTimeModel) {
            with(binding) {
                city.text = item.city
                time.text = item.time
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldViewHolder {
        val binding =
            ItemWorldLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorldViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorldViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}