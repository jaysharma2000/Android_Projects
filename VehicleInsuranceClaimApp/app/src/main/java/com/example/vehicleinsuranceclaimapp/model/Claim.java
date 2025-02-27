package com.example.vehicleinsuranceclaimapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Claim {

    @PrimaryKey(autoGenerate = true)
    private int claimId;
    private String discription;
    private String status;
    private String dateSubmitted;
    private String dateUpdated;
    private int userId;

    public int policyId;

    public String imagePath;

    public Claim(String discription, String status, String dateSubmitted, String dateUpdated, int userId, int policyId, String imagePath) {

        this.discription = discription;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
        this.dateUpdated = dateUpdated;
        this.userId = userId;
        this.policyId = policyId;
        this.imagePath = imagePath;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "claimId=" + claimId +
                ", discription='" + discription + '\'' +
                ", status='" + status + '\'' +
                ", dateSubmitted='" + dateSubmitted + '\'' +
                ", dateUpdated='" + dateUpdated + '\'' +
                ", userId=" + userId +
                '}';
    }
}
