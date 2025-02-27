package com.example.milestone4.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.milestone4.R
import com.example.milestone4.databinding.FragmentFundTransferBinding

class FundTransferFragment : Fragment() {
    private lateinit var binding: FragmentFundTransferBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFundTransferBinding.inflate(inflater, container, false)

        return binding.root
    }
}