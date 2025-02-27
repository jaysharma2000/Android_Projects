package com.example.mytodolistapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.mytodolistapp.R;
import com.example.mytodolistapp.dao.UserDao;
import com.example.mytodolistapp.database.UserDatabase;
import com.example.mytodolistapp.model.User;
import java.util.concurrent.Executors;
import android.widget.Toast;

public class UpdateFragment extends Fragment {

    private EditText idToUpdate, newName, newEmail;
    private Button btnUpdateData;
    private UserDao userDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        idToUpdate = view.findViewById(R.id.idToUpdate);
        newName = view.findViewById(R.id.newUpdateValue);
        newEmail = view.findViewById(R.id.newEmail);
        btnUpdateData = view.findViewById(R.id.btnAddData);

        userDao = UserDatabase.getInstance(requireContext()).userDao();

        btnUpdateData.setOnClickListener(v -> {
            String idText = idToUpdate.getText().toString().trim();
            String name = newName.getText().toString().trim();
            String email = newEmail.getText().toString().trim();

            if (!idText.isEmpty() && !name.isEmpty() && !email.isEmpty()) {
                int userId = Integer.parseInt(idText);

                Executors.newSingleThreadExecutor().execute(() -> {
                    User updatedUser = new User(userId, name, email);
                    userDao.update(updatedUser);

                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "User updated successfully", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    });
                });
            } else {
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
