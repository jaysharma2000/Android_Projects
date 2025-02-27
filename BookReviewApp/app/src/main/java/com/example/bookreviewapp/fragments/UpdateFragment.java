package com.example.bookreviewapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookreviewapp.R;
import com.example.bookreviewapp.dao.BookDao;
import com.example.bookreviewapp.database.BookDatabase;
import com.example.bookreviewapp.model.Book;

import java.util.concurrent.Executors;

public class UpdateFragment extends Fragment {

    private EditText idToUpdate, newName, newAuthor;
    private Button btnUpdateData;
    private BookDao bookDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        idToUpdate = view.findViewById(R.id.idToUpdate);
        newName = view.findViewById(R.id.newUpdateValue);
        newAuthor = view.findViewById(R.id.newAuthor);
        btnUpdateData = view.findViewById(R.id.btnAddData);

        bookDao = BookDatabase.getInstance(requireContext()).bookDao();

        btnUpdateData.setOnClickListener(v -> {
            String idText = idToUpdate.getText().toString().trim();
            String name = newName.getText().toString().trim();
            String email = newAuthor.getText().toString().trim();

            if (!idText.isEmpty() && !name.isEmpty() && !email.isEmpty()) {
                int userId = Integer.parseInt(idText);

                Executors.newSingleThreadExecutor().execute(() -> {
                    Book updatedUser = new Book(userId, name, email);
                    bookDao.update(updatedUser);

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