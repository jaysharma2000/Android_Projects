package com.example.vehicleinsuranceclaimapp.service;

import com.example.vehicleinsuranceclaimapp.model.Claim;

import java.util.ArrayList;
import java.util.Optional;

public class ClaimManagar {
    private ArrayList<Claim> claims;

    public ClaimManagar(){
        this.claims = new ArrayList<>();
    }

    public void addClaim(Claim claim){
        claims.add(claim);
    }

    public  ArrayList<Claim> getAllClaims(){
        return claims;
    }

    public Claim findClaimById(int claimId){
        for(Claim claim : claims){
            if(claim.getClaimId() == claimId){
                return claim;
            }
        }
        return null;
    }

    public void updateClaimStatus(int claimId, String newStatus, String updatedDate){
        Claim claim = findClaimById(claimId);

        if(claim != null){
            claim.setStatus(newStatus);
            claim.setDateUpdated(updatedDate);
            claims.add(claim);

            Optional<Claim> claimToUpdate = claims.stream().filter(claim1 -> claim1.getClaimId() == claimId).findFirst();

            claimToUpdate.ifPresent(claimLatest -> {
                claimLatest.setStatus(newStatus);
                claimLatest.setDateUpdated(updatedDate);
            });
        }
    }
}



