package com.example.bookmyslot.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmyslot.R
import com.example.bookmyslot.model.JobRole

//Adapter for displaying all Available job roles
class JobRoleAdapter(private val jobList: List<JobRole>) :
    RecyclerView.Adapter<JobRoleAdapter.JobRoleViewHolder>() {

    class JobRoleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.jobTitleTextView)
        val techStackTextView: TextView = view.findViewById(R.id.techStackTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobRoleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job_role, parent, false)
        return JobRoleViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobRoleViewHolder, position: Int) {
        val job = jobList[position]
        holder.titleTextView.text = "Position: ${job.title}"
        holder.techStackTextView.text = "Technical Skills: ${job.techStack}"
    }

    override fun getItemCount(): Int = jobList.size
}
