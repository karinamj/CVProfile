package com.example.cvprofile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cvprofile.data.local.entity.SkillEntity
import com.example.cvprofile.databinding.ItemSkillBinding

class SkillAdapter(
    private val onDeleteClick: ((SkillEntity) -> Unit)? = null
) : ListAdapter<SkillEntity, SkillAdapter.SkillViewHolder>(SkillDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val binding = ItemSkillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SkillViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SkillViewHolder(private val binding: ItemSkillBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(skill: SkillEntity) {
            binding.tvSkillName.text = skill.name
            binding.ivDeleteSkill.setOnClickListener {
                onDeleteClick?.invoke(skill)
            }
        }
    }
}

class SkillDiffCallback : DiffUtil.ItemCallback<SkillEntity>() {
    override fun areItemsTheSame(oldItem: SkillEntity, newItem: SkillEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SkillEntity, newItem: SkillEntity): Boolean =
        oldItem == newItem
}