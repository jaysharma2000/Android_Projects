package com.example.bookreviewapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookreviewapp.model.Book;

import java.util.List;
@Dao
public interface BookDao {

    @Insert
    void insert(Book user);

    @Update
    void update(Book user);

    @Delete
    void delete(Book user);

    @Query("DELETE FROM book_table WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM book_table ORDER BY id ASC")
    LiveData<List<Book>> getAllBooks();
}
