package com.example.vehicleinsuranceclaimapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.vehicleinsuranceclaimapp.GoogleMapActivity;
import com.example.vehicleinsuranceclaimapp.R;


public class DashboardFragment extends Fragment {

    private Button startClaim;
    private Button claimHistory, myPolicies, updateClaim, showCompanies, googleMaps;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

            startClaim = view.findViewById(R.id.startClaim);

            startClaim.setOnClickListener(v -> navigateToFileClaimFragment());

            claimHistory = view.findViewById(R.id.claimHistory);
            claimHistory.setOnClickListener(v -> navigateToDisplayClaimsFragment());

            myPolicies = view.findViewById(R.id.myPolicies);
            myPolicies.setOnClickListener(v -> navigateToPolicyFragment());

            updateClaim = view.findViewById(R.id.updateClaim);
            updateClaim.setOnClickListener(v -> navigateToUpdateFragment());

            showCompanies = view.findViewById(R.id.showCompanies);
            showCompanies.setOnClickListener(v -> navigateToCompaniesFragment());

            googleMaps = view.findViewById(R.id.googleMaps);
            googleMaps.setOnClickListener(v -> navigateToMapActivity());

            return view;
        }


    private void navigateToFileClaimFragment() {

        FileClaimFragment fileClaimFragment = new FileClaimFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fileClaimFragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToDisplayClaimsFragment() {

        DisplayClaimsFragment displayClaimsFragment = new DisplayClaimsFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, displayClaimsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToPolicyFragment(){
            PolicyFragment policyFragment = new PolicyFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, policyFragment)
                    .addToBackStack(null)
                    .commit();

    }

    private void navigateToUpdateFragment(){
        UpdateFragment updateFragment = new UpdateFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, updateFragment)
                .addToBackStack(null)
                .commit();

    }

    private void navigateToCompaniesFragment(){
            CompaniesFragment companiesFragment = new CompaniesFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, companiesFragment)
                    .addToBackStack(null)
                    .commit();
    }

    private void navigateToMapActivity() {
        Intent intent = new Intent(requireContext(), GoogleMapActivity.class);
        startActivity(intent);
    }

}
