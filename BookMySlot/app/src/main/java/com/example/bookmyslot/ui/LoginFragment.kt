package com.example.bookmyslot.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookmyslot.R
import com.example.bookmyslot.database.AppDatabase
import com.example.bookmyslot.databinding.FragmentLoginBinding
import com.example.service.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Added navigation to the register page
        binding.registerBtn.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }

        //Called the login function here onclick of login button
        binding.loginButton.setOnClickListener { handleLogin() }
    }

    //function to login the existing user only
    private fun handleLogin() {
        val username = binding.usernameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val userDao = AppDatabase.getInstance(requireContext()).userDao()
            val user = userDao.login(username, password)

            withContext(Dispatchers.Main) {
                if (user != null) {
                    UserSession.getInstance().loggedInUserId = user.id

                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_dashBoardFragment)
                } else {
                    Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}