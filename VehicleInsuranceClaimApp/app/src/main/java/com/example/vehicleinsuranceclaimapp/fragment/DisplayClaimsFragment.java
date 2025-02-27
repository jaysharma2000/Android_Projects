package com.example.vehicleinsuranceclaimapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.vehicleinsuranceclaimapp.R;
import com.example.vehicleinsuranceclaimapp.database.AppDatabase;
import com.example.vehicleinsuranceclaimapp.model.Claim;
import com.example.vehicleinsuranceclaimapp.service.ClaimAdapter;
import com.example.vehicleinsuranceclaimapp.service.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


public class DisplayClaimsFragment extends Fragment {

    private ListView allFiledClaims;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_display_claims, container, false);

        allFiledClaims = view.findViewById(R.id.allFiledClaims);
        AppDatabase dataBase = AppDatabase.getInstance(requireContext());

        List<Claim> claimList = new ArrayList<>();
        ClaimAdapter claimAdapter = new ClaimAdapter(requireContext(), claimList);
        allFiledClaims.setAdapter(claimAdapter);

        Executors.newSingleThreadExecutor().execute(() -> {
            int loggedInUserId = UserSession.getInstance().getLoggedInUserId();
            List<Claim> dbClaimList = dataBase.claimDao().getClaimsByUser(loggedInUserId);

            requireActivity().runOnUiThread(() -> {
                claimList.clear();
                claimList.addAll(dbClaimList);
                claimAdapter.notifyDataSetChanged();
            });
        });

        return view;
    }
}



