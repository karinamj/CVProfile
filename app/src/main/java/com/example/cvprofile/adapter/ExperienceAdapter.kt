package com.example.cvprofile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cvprofile.data.local.entity.ExperienceEntity
import com.example.cvprofile.databinding.ItemExperienceBinding

class ExperienceAdapter(
    private val onDelete: (ExperienceEntity) -> Unit
) : RecyclerView.Adapter<ExperienceAdapter.ViewHolder>() {

    private val experiences = mutableListOf<ExperienceEntity>()

    fun setData(newList: List<ExperienceEntity>) {
        experiences.clear()
        experiences.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemExperienceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExperienceEntity) {
            binding.tvCompanyName.text = item.companyName
            binding.tvPosition.text = item.position
            binding.tvPeriod.text = "${item.startDate} - ${item.endDate ?: "Present"}"
            binding.tvDescription.text = item.description ?: ""

            binding.root.setOnLongClickListener {
                onDelete(item)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExperienceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(experiences[position])
    }

    override fun getItemCount(): Int = experiences.size
}