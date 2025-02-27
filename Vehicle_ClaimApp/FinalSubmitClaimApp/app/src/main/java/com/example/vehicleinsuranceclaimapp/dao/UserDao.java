package com.example.vehicleinsuranceclaimapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.vehicleinsuranceclaimapp.model.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password =:password")
    User login(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username OR email = :email LIMIT 1")
    User checkIfUserExists(String username, String email);
}
