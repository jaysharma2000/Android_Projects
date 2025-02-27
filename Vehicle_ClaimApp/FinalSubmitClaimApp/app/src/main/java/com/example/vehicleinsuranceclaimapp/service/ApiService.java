package com.example.vehicleinsuranceclaimapp.service;

import com.example.vehicleinsuranceclaimapp.model.CompanyUpdate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("company")
    Call<List<CompanyUpdate>> getCompanyUpdates();

//    Call<List<Policy>> getPolicyDetails();
}
