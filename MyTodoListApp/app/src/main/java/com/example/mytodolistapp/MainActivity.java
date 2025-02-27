package com.example.mytodolistapp;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mytodolistapp.adapter.UserAdapter;
import com.example.mytodolistapp.dao.UserDao;
import com.example.mytodolistapp.database.UserDatabase;
import com.example.mytodolistapp.model.User;
import java.util.List;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import com.example.mytodolistapp.fragments.DeleteFragment;
import com.example.mytodolistapp.fragments.UpdateFragment;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private UserDao userDao;
    private Button btnAddData, btnUpdateData, btnDeleteData;
    private EditText editTextName, editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddData = findViewById(R.id.btnAddData);
        btnUpdateData = findViewById(R.id.btnUpdateData);
        btnDeleteData = findViewById(R.id.btnDeleteData);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);

        adapter = new UserAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        UserDatabase db = UserDatabase.getInstance(this);
        userDao = db.userDao();

        LiveData<List<User>> usersLiveData = userDao.getAllUsers();
        usersLiveData.observe(this, users -> adapter.submitList(users));

        btnAddData.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();

            if (!name.isEmpty() && !email.isEmpty()) {
                new Thread(() -> userDao.insert(new User(0, name, email))).start();
                editTextName.setText("");
                editTextEmail.setText("");
            }
        });

        // Navigate to UpdateFragment
        btnUpdateData.setOnClickListener(v -> {
            UpdateFragment updateFragment = new UpdateFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main, updateFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Navigate to DeleteFragment
        btnDeleteData.setOnClickListener(v -> {
            DeleteFragment deleteFragment = new DeleteFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main, deleteFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }
}
