package com.example.vehicleinsuranceclaimapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vehicleinsuranceclaimapp.R;
import com.example.vehicleinsuranceclaimapp.dao.UserDao;
import com.example.vehicleinsuranceclaimapp.database.AppDatabase;
import com.example.vehicleinsuranceclaimapp.model.User;
import com.example.vehicleinsuranceclaimapp.service.UserSession;

import java.util.concurrent.Executors;

public class LoginFragment extends Fragment {

        private Button registerBtn;
        private Button loginButton;
        private EditText usernameEditText, passwordEditText;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_login, container, false);

            registerBtn = view.findViewById(R.id.registerBtn);
            loginButton = view.findViewById(R.id.loginButton);
            usernameEditText = view.findViewById(R.id.usernameEditText);
            passwordEditText = view.findViewById(R.id.passwordEditText);

            registerBtn.setOnClickListener(v -> navigateToRegisterFragment());
            loginButton.setOnClickListener(v -> {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Executors.newSingleThreadExecutor().execute(() -> {
                    AppDatabase db = AppDatabase.getInstance(getContext());
                    UserDao userDao = db.userDao();

                    User user = userDao.login(username, password);

                    if(user != null){
                        //track claims based on particular user
                        UserSession.getInstance().setLoggedInUserId(user.getId());

                        getActivity().runOnUiThread(() ->{
                            Toast.makeText(getContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                            navigateToDashBoardFragment();
                        });
                    }else{
                        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show());
                    }
                });
            });

            return view;
        }

        private void navigateToRegisterFragment() {

            RegisterFragment registerFragment = new RegisterFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, registerFragment)
                    .addToBackStack(null)
                    .commit();
        }

    private void navigateToDashBoardFragment() {

        DashboardFragment dashboardFragment = new DashboardFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, dashboardFragment)
                .addToBackStack(null)
                .commit();
    }
    }
