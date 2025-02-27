package com.example.bookreviewapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreviewapp.adapter.BookAdapter;
import com.example.bookreviewapp.dao.BookDao;
import com.example.bookreviewapp.database.BookDatabase;
import com.example.bookreviewapp.fragments.DeleteFragment;
import com.example.bookreviewapp.fragments.UpdateFragment;
import com.example.bookreviewapp.model.Book;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private BookDao bookDao;
    private Button btnAddData, btnUpdateData, btnDeleteData;
    private EditText editTextName, editTextAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddData = findViewById(R.id.btnAddData);
        btnUpdateData = findViewById(R.id.btnUpdateData);
        btnDeleteData = findViewById(R.id.btnDeleteData);
        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);

        adapter = new BookAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        BookDatabase db = BookDatabase.getInstance(this);
        bookDao = db.bookDao();

        LiveData<List<Book>> booksLiveData = bookDao.getAllBooks();
        booksLiveData.observe(this, books -> adapter.submitList(books));

        btnAddData.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String author = editTextAuthor.getText().toString().trim();

            if (!name.isEmpty() && !author.isEmpty()) {
                new Thread(() -> bookDao.insert(new Book(0, name, author))).start();
                editTextName.setText("");
                editTextAuthor.setText("");
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