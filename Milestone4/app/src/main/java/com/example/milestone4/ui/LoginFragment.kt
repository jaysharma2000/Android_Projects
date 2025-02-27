package com.example.milestone4.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.milestone4.R
import com.example.milestone4.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

//        val email = binding.email.text
//        val password = binding.password.text
//        val button  = binding.loginButton
//
//        if(email.equals("jaysharma@gmail.com") && password.equals("12345")){
//            binding.loginButton.setOnClickListener{
//                findNavController().navigate(
//                    LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
//                )
//            }
//        }else{
//            Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener{
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if(email == "jaysharma@gmail.com" && password == "12345"){
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            }else{
                Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

}














