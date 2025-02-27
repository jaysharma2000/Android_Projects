package com.example.bookmyslot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmyslot.databinding.ItemInterviewSlotBinding
import com.example.bookmyslot.model.InterviewSlot

//Adapter for displaying All interview slots
class InterviewSlotAdapter(private val slots: MutableList<InterviewSlot>) :
    RecyclerView.Adapter<InterviewSlotAdapter.SlotViewHolder>() {

    var onItemClick: ((InterviewSlot) -> Unit)? = null

    inner class SlotViewHolder(val binding: ItemInterviewSlotBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val binding = ItemInterviewSlotBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SlotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        val slot = slots[position]
        with(holder.binding) {
            jobRoleTextView.text = "Job Role: ${slot.jobRole}"
            interviewNameTextView.text = "Interviewer Name: ${slot.interviewName}"
            designationTextView.text = "Designation: ${slot.designation}"
            dateTextView.text = "Available Date: ${slot.date}"
            timeTextView.text = "Available Time: ${slot.time}"
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(slot)
        }
    }

    override fun getItemCount(): Int = slots.size

    //Function to remove the interviewSlot
    fun removeItem(position: Int): InterviewSlot {
        val removed = slots.removeAt(position)
        notifyItemRemoved(position)
        return removed
    }

    //Function to update the interviewSlot
    fun updateData(newSlots: List<InterviewSlot>) {
        slots.clear()
        slots.addAll(newSlots)
        notifyDataSetChanged()
    }
}
