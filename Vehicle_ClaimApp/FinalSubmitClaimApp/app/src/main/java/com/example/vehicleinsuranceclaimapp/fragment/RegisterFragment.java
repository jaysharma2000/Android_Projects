package com.example.vehicleinsuranceclaimapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehicleinsuranceclaimapp.R;
import com.example.vehicleinsuranceclaimapp.dao.UserDao;
import com.example.vehicleinsuranceclaimapp.database.AppDatabase;
import com.example.vehicleinsuranceclaimapp.model.User;

import java.util.concurrent.Executors;

public class RegisterFragment extends Fragment {
    private EditText usernameEditText, emailEditText,  passwordEditText;
    private Button registerButton;
    private TextView loginBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        registerButton = view.findViewById(R.id.registerButton);
        loginBtn = view.findViewById(R.id.loginBtn);

        registerButton.setOnClickListener(v ->{
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(getContext(), "Please fill all details", Toast.LENGTH_SHORT).show();

                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase db = AppDatabase.getInstance(getContext());
                UserDao userDao = db.userDao();

                if(userDao.checkIfUserExists(username,email) != null){
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "User already exists", Toast.LENGTH_SHORT).show();
                    });
                }else{
                    userDao.insertUser(new User(username, email, password));
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "User Registered successfully", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    });
                }
            });
        });

        loginBtn.setOnClickListener(v -> navigateToLoginFragment());


        return view;
    }


    private void navigateToLoginFragment() {

        LoginFragment loginFragment = new LoginFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, loginFragment)
                .addToBackStack(null)
                .commit();
    }
}