package com.example.mysmarthomecontrollerapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mysmarthomecontrollerapp.R
import com.example.mysmarthomecontrollerapp.databinding.FragmentDashBoardBinding
import com.example.mysmarthomecontrollerapp.viewmodel.DashBoardViewModel

class DashBoardFragment : Fragment() {
    private lateinit var binding: FragmentDashBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashBoardBinding.inflate(inflater, container, false)

        val viewModel = DashBoardViewModel()

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.fetchDevices()

        return binding.root
    }
}