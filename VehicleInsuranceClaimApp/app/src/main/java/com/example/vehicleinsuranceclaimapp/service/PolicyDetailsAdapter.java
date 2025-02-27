package com.example.vehicleinsuranceclaimapp.service;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.vehicleinsuranceclaimapp.R;
import com.example.vehicleinsuranceclaimapp.database.AppDatabase;
import com.example.vehicleinsuranceclaimapp.model.Claim;
import com.example.vehicleinsuranceclaimapp.model.Policy;

import java.util.List;
import java.util.concurrent.Executors;

//public class PolicyDetailsAdapter {
//}
//public class PolicyDetailsAdapter extends ArrayAdapter<Claim> {
//
//    private final AppDatabase database;
//
//    public PolicyDetailsAdapter(@NonNull Context context, @NonNull List<Policy> claims) {
//        super(context, 0, claims);
//        this.database = AppDatabase.getInstance(context);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Claim claim = getItem(position);
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_policy_details, parent, false);
//        }
//
//        TextView tvPolicyId = convertView.findViewById(R.id.tvPolicyId);
//        TextView tvPolicyName = convertView.findViewById(R.id.tvPolicyName);
//        TextView tvVehicleType = convertView.findViewById(R.id.tvVehicleType);
//        TextView tvVehicleNumber = convertView.findViewById(R.id.tvVehicleNumber);
//
//        if (claim != null) {
//            tvPolicyId.setText("Policy ID: " + claim.getPolicyId());
//
//            Executors.newSingleThreadExecutor().execute(() -> {
//                Policy policy = database.policyDao().getPolicyById(claim.getPolicyId());
//                if (policy != null) {
//                    String policyName = policy.getPolicyName();
//                    String vehicleType = policy.getVehicleType();
//                    String vehicleNumber = policy.getVehicleNumber();
//
//                    // Update UI on the main thread
//                    ((Activity) getContext()).runOnUiThread(() -> {
//                        tvPolicyName.setText("Policy Name: " + policyName);
//                        tvVehicleType.setText("Vehicle Type: " + vehicleType);
//                        tvVehicleNumber.setText("Vehicle Number: " + vehicleNumber);
//                    });
//                }
//            });
//        }
//
//        return convertView;
//    }
//}











import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehicleinsuranceclaimapp.R;
import com.example.vehicleinsuranceclaimapp.model.Policy;

import java.util.List;

public class PolicyDetailsAdapter extends RecyclerView.Adapter<PolicyDetailsAdapter.PolicyViewHolder> {

    private final Context context;
    private final List<Policy> policyList;

    public PolicyDetailsAdapter(Context context, List<Policy> policyList) {
        this.context = context;
        this.policyList = policyList;
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_policy_details, parent, false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, int position) {
        Policy policy = policyList.get(position);

        holder.policyIdTextView.setText("Policy ID: " + policy.policyId);
        holder.policyNameTextView.setText("Policy Name: " + policy.policyName);
        holder.vehicleTypeTextView.setText("Vehicle Type: " + policy.vehicleType);
        holder.vehicleNumberTextView.setText("Vehicle Number: " + policy.vehicleNumber);
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        TextView policyIdTextView, policyNameTextView, vehicleTypeTextView, vehicleNumberTextView;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            policyIdTextView = itemView.findViewById(R.id.tvPolicyId);
            policyNameTextView = itemView.findViewById(R.id.tvPolicyName);
            vehicleTypeTextView = itemView.findViewById(R.id.tvVehicleType);
            vehicleNumberTextView = itemView.findViewById(R.id.tvVehicleNumber);

        }
    }
}
