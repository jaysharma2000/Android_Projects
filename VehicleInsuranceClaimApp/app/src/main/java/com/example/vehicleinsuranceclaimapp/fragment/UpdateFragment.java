package com.example.vehicleinsuranceclaimapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vehicleinsuranceclaimapp.R;
import com.example.vehicleinsuranceclaimapp.database.AppDatabase;
import com.example.vehicleinsuranceclaimapp.model.Claim;
import com.example.vehicleinsuranceclaimapp.notification.NotificationHelper;

import java.util.concurrent.Executors;


public class UpdateFragment extends Fragment {

    private EditText claimId, claimStatus;
    private Button updateClaimBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update, container, false);


        claimId = view.findViewById(R.id.claimId);
        claimStatus = view.findViewById(R.id.claimStatus);
        updateClaimBtn = view.findViewById(R.id.updateClaimBtn);

        AppDatabase database = AppDatabase.getInstance(requireContext());
        NotificationHelper.createNotificationChannel(requireContext().getApplicationContext());

        updateClaimBtn.setOnClickListener(v -> {
            String claimIdStr = claimId.getText().toString().trim();
            String newStatus = claimStatus.getText().toString().trim();

            if (claimIdStr.isEmpty() || newStatus.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int claimId = Integer.parseInt(claimIdStr);

            Executors.newSingleThreadExecutor().execute(() -> {
                Claim claim = database.claimDao().getClaimById(claimId);
                if (claim != null) {
                    claim.setStatus(newStatus);
                    database.claimDao().updateClaim(claim);
                    requireActivity().runOnUiThread(() -> {
                        NotificationHelper.sendNotification(requireContext(), claim.getStatus());
                        Toast.makeText(requireContext(), "Claim updated successfully ", Toast.LENGTH_SHORT).show();
                        navigateToDashBoardFragment();
                    });

                } else {
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(requireContext(), "Claim not found", Toast.LENGTH_SHORT).show());
                }
            });
        });

        return  view;
    }

    private void navigateToDashBoardFragment() {

        DashboardFragment dashboardFragment = new DashboardFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, dashboardFragment)
                .addToBackStack(null)
                .commit();
    }
}
