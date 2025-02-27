package com.example.milestone4.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.milestone4.R
import com.example.milestone4.adapter.TransactionAdapter
import com.example.milestone4.databinding.FragmentDashboardBinding
import com.example.milestone4.model.Transaction

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val transactions = listOf(
        Transaction(1, "Traveling", 3200.0, "Expense"),
        Transaction(1, "Salary", 60000.0, "Income"),
        Transaction(1, "Food", 200.0, "Expense"),
        Transaction(1, "Salary", 30250.3, "Income"),
        Transaction(1, "Shopping", 7200.0, "Expense"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = TransactionAdapter(transactions){ transaction ->
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToFundTransferFragment()
            )
        }

        binding.recyclerView.adapter = adapter

        binding.fundTransfer.setOnClickListener{

                findNavController().navigate(R.id.action_dashboardFragment_to_fundTransferFragment)
        }
        return binding.root
    }
}













