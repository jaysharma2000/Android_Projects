package com.example.mytodolistapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mytodolistapp.R;
import com.example.mytodolistapp.dao.UserDao;
import com.example.mytodolistapp.database.UserDatabase;
import java.util.concurrent.Executors;

public class DeleteFragment extends Fragment {

    private EditText deleteId;
    private Button btnDeleteData;
    private UserDao userDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        deleteId = view.findViewById(R.id.deleteId);
        btnDeleteData = view.findViewById(R.id.btnDeleteData);

        userDao = UserDatabase.getInstance(requireContext()).userDao();

        btnDeleteData.setOnClickListener(v -> {
            String idText = deleteId.getText().toString().trim();

            if (!idText.isEmpty()) {
                int userId = Integer.parseInt(idText);

                Executors.newSingleThreadExecutor().execute(() -> {
                    userDao.deleteById(userId);

                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "User deleted successfully", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    });
                });
            } else {
                Toast.makeText(getContext(), "User ID is required", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
