package com.example.bookmyslot.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmyslot.R
import com.example.bookmyslot.adapter.JobRoleAdapter
import com.example.bookmyslot.databinding.ActivityMainBinding
import com.example.bookmyslot.databinding.FragmentDashBoardBinding
import com.example.bookmyslot.model.JobRole

class DashBoardFragment : Fragment() {

    private lateinit var binding: FragmentDashBoardBinding
    private lateinit var jobRoleAdapter: JobRoleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashBoardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //created a static job roles list here for interviewer to select
        val jobList = listOf(
            JobRole("Android Developer", "Kotlin, Jetpack, Room"),
            JobRole("iOS Developer", "Swift, Core Data, UIKit"),
            JobRole("Backend Developer", "Node.js, Express, MongoDB"),
            JobRole("Frontend Developer", "React, TypeScript, TailwindCSS"),
            JobRole("DevOps Engineer", "Docker, Kubernetes, AWS"),
            JobRole("QA Engineer", "Selenium, Appium, TestNG"),
            JobRole("Data Scientist", "Python, R, TensorFlow"),
            JobRole("Cybersecurity Specialist", "Penetration Testing, SIEM, Network Security"),
            JobRole("Cloud Engineer", "AWS, Azure, Google Cloud"),
            JobRole("Full Stack Developer", "JavaScript, Node.js, React, PostgreSQL")
        )

        jobRoleAdapter = JobRoleAdapter(jobList)
        binding.recyclerView.adapter = jobRoleAdapter

        //Added navigation to the add interview slots page
        binding.addInterviewSlot.setOnClickListener{
            findNavController().navigate(R.id.action_dashBoardFragment_to_addInterviewSlotFragment)
        }

        //Added navigation to the view all interview slots page
        binding.viewInterviewSlot.setOnClickListener{
            findNavController().navigate(R.id.action_dashBoardFragment_to_viewAllSlotsFragment)
        }
    }
}
