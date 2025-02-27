package com.example.vehicleinsuranceclaimapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.vehicleinsuranceclaimapp.model.Policy;

import java.util.List;

@Dao
public interface PolicyDao {
    @Insert
    void insertPolicy(Policy policy);
    @Query("SELECT * FROM Policy WHERE userId = :userId")
    List<Policy> getPoliciesByUserId(int userId);

    @Query("SELECT policyName FROM Policy WHERE policyId = :policyId")
    String getPolicyNameById(int policyId);

    @Query("SELECT * FROM Policy WHERE policyId = :policyId LIMIT 1")
    Policy getPolicyById(int policyId);

}
