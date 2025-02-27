package com.example.vehicleinsuranceclaimapp.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.vehicleinsuranceclaimapp.R;
import com.example.vehicleinsuranceclaimapp.model.CompanyUpdate;
import com.example.vehicleinsuranceclaimapp.service.ApiService;
import com.example.vehicleinsuranceclaimapp.service.CompanyAdapter;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CompaniesFragment extends Fragment {

    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_companies, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://677636ef12a55a9a7d0ae13a.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getCompanyUpdates().enqueue(new Callback<List<CompanyUpdate>>() {
            @Override
            public void onResponse(Call<List<CompanyUpdate>> call, Response<List<CompanyUpdate>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", response.body().toString());
                    List<CompanyUpdate> companyUpdates = response.body();
                    CompanyAdapter companyAdapter = new CompanyAdapter(companyUpdates);
                    recyclerView.setAdapter(companyAdapter);
                } else {
                    Log.e("API Error", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<List<CompanyUpdate>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch data: " + t.getMessage());
            }
        });
        return view;
    }
}