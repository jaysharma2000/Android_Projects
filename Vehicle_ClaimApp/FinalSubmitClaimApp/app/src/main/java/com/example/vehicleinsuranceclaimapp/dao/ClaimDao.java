package com.example.vehicleinsuranceclaimapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vehicleinsuranceclaimapp.model.Claim;

import java.util.List;

@Dao
public interface ClaimDao {
    @Insert
    void insertClaim(Claim claim);
    @Query("SELECT * FROM Claim")
    List<Claim> getAllClaims();

    @Query("DELETE FROM Claim WHERE claimId = :id")
    void deleteClaimById(int id);


    @Query("SELECT * FROM Claim WHERE userId = :userId")
    List<Claim> getClaimsByUser(int userId);

    @Query("SELECT * FROM Claim WHERE userId = :userId AND policyId = :policyId")
    List<Claim> getClaimsByPolicy(int userId, int policyId);

    @Query("SELECT * FROM Claim WHERE claimId = :claimId")
    Claim getClaimById(int claimId);

    @Query("SELECT imagePath FROM Claim WHERE claimId = :claimId")
    String getImagePathByClaimId(int claimId);

    @Update
    void updateClaim(Claim claim);


    @Insert
    long insertClaimAndGetId(Claim claim);
}
