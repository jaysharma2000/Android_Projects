package com.example.vehicleinsuranceclaimapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.vehicleinsuranceclaimapp.model.ClaimImage;

import java.util.List;

@Dao
public interface ClaimImageDao {
    @Insert
    void insertImage(ClaimImage claimImage);

    @Query("SELECT * FROM claim_images WHERE claim_id = :claimId")
    List<ClaimImage> getImageByClaimId(String claimId);
}
