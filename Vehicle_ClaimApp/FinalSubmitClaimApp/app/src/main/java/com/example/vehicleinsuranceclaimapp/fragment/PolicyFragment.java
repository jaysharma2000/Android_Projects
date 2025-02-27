//package com.example.vehicleinsuranceclaimapp.fragment;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.example.vehicleinsuranceclaimapp.R;
//import com.example.vehicleinsuranceclaimapp.dao.PolicyDao;
//import com.example.vehicleinsuranceclaimapp.database.AppDatabase;
//import com.example.vehicleinsuranceclaimapp.model.Policy;
//import com.example.vehicleinsuranceclaimapp.service.PolicyDetailsAdapter;
//import com.example.vehicleinsuranceclaimapp.service.UserSession;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//
//public class PolicyFragment extends Fragment {
//
//
//    private List<Policy> policyList = new ArrayList<>();
//    private ArrayAdapter<Policy> policyAdapter;
//    private Spinner policyDropdown;
//    private EditText vehicleTypeEditText, vehicleNumberEditText;
//    private Button savePolicyButton;
//    private ListView policyListView;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        // Inflate the layout for this fragment
//        View view =  inflater.inflate(R.layout.fragment_policy, container, false);
//
//        policyDropdown = view.findViewById(R.id.policyDropdown);
//        vehicleTypeEditText = view.findViewById(R.id.vehicleTypeEditText);
//        vehicleNumberEditText = view.findViewById(R.id.vehicleNumberEditText);
//        savePolicyButton = view.findViewById(R.id.savePolicyButton);
//        policyListView = view.findViewById(R.id.policyListView);
//
//        policyAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, policyList);
//        policyListView.setAdapter(policyAdapter);
//
//        savePolicyButton.setOnClickListener(v -> savePolicy());
//        loadPolicies();
//
//        return view;
//    }
//
//    private void savePolicy(){
//        String policyName = policyDropdown.getSelectedItem().toString();
//        String vehicleType = vehicleTypeEditText.getText().toString().trim();
//        String vehicleNumber = vehicleNumberEditText.getText().toString().trim();
//        int userId = UserSession.getInstance().getLoggedInUserId();
//
//        if(TextUtils.isEmpty(vehicleType) || TextUtils.isEmpty(vehicleNumber)){
//            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Executors.newSingleThreadExecutor().execute(() -> {
//            try {
//                AppDatabase db = AppDatabase.getInstance(requireContext());
//                PolicyDao policyDao = db.policyDao();
//
//                Policy policy = new Policy();
//                policy.policyName = policyName;
//                policy.vehicleType = vehicleType;
//                policy.vehicleNumber = vehicleNumber;
//                policy.userId = userId;
//
//                policyDao.insertPolicy(policy);
//
//                requireActivity().runOnUiThread(() -> {
//                    Toast.makeText(requireContext(), "Policy added!", Toast.LENGTH_SHORT).show();
//                    loadPolicies();
//                });
//            } catch (Exception e) {
//                Log.e("PolicyFragment", "Error saving policy: ", e);
//            }
//        });
//
//    }
//
//    private void loadPolicies(){
//        int userId = UserSession.getInstance().getLoggedInUserId();
//        Executors.newSingleThreadExecutor().execute(() -> {
//            AppDatabase db = AppDatabase.getInstance(requireContext());
//            List<Policy> userPolicies = db.policyDao().getPoliciesByUserId(userId);
//
//            requireActivity().runOnUiThread(() ->{
//                policyList.clear();
//                policyList.addAll(userPolicies);
//                policyAdapter.notifyDataSetChanged();
//            });
//        });
//    }
//}




package com.example.vehicleinsuranceclaimapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vehicleinsuranceclaimapp.R;

import com.example.vehicleinsuranceclaimapp.database.AppDatabase;
import com.example.vehicleinsuranceclaimapp.model.Policy;
import com.example.vehicleinsuranceclaimapp.service.PolicyDetailsAdapter;
import com.example.vehicleinsuranceclaimapp.service.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class PolicyFragment extends Fragment {

    private List<Policy> policyList = new ArrayList<>();
    private RecyclerView policyRecyclerView;
    private PolicyDetailsAdapter policyAdapter;
    private Spinner policyDropdown;
    private EditText vehicleTypeEditText, vehicleNumberEditText;
    private Button savePolicyButton;
    private ListView policyListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_policy, container, false);

        policyDropdown = view.findViewById(R.id.policyDropdown);
        vehicleTypeEditText = view.findViewById(R.id.vehicleTypeEditText);
        vehicleNumberEditText = view.findViewById(R.id.vehicleNumberEditText);
        savePolicyButton = view.findViewById(R.id.savePolicyButton);
        policyRecyclerView = view.findViewById(R.id.policyRecyclerView);
        policyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        policyAdapter = new PolicyDetailsAdapter(requireContext(), policyList);
        policyRecyclerView.setAdapter(policyAdapter);

        savePolicyButton.setOnClickListener(v -> savePolicy());
        loadPolicies();

        return view;
    }

    private void savePolicy() {
        String policyName = policyDropdown.getSelectedItem().toString();
        String vehicleType = vehicleTypeEditText.getText().toString().trim();
        String vehicleNumber = vehicleNumberEditText.getText().toString().trim();
        int userId = UserSession.getInstance().getLoggedInUserId();

        if (TextUtils.isEmpty(vehicleType) || TextUtils.isEmpty(vehicleNumber)) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                AppDatabase db = AppDatabase.getInstance(requireContext());

                Policy policy = new Policy();
                policy.policyName = policyName;
                policy.vehicleType = vehicleType;
                policy.vehicleNumber = vehicleNumber;
                policy.userId = userId;

                db.policyDao().insertPolicy(policy);

                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "Policy added!", Toast.LENGTH_SHORT).show();
                    loadPolicies();
                });
            } catch (Exception e) {
                Log.e("PolicyFragment", "Error saving policy: ", e);
            }
        });
    }

    private void loadPolicies() {
        int userId = UserSession.getInstance().getLoggedInUserId();
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            List<Policy> userPolicies = db.policyDao().getPoliciesByUserId(userId);

            requireActivity().runOnUiThread(() -> {
                policyList.clear();
                policyList.addAll(userPolicies);
                policyAdapter.notifyDataSetChanged();
            });
        });
    }
}
