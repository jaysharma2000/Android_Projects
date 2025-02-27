package com.example.vehicleinsuranceclaimapp.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.vehicleinsuranceclaimapp.R;
import com.example.vehicleinsuranceclaimapp.dao.ClaimImageDao;
import com.example.vehicleinsuranceclaimapp.database.AppDatabase;
import com.example.vehicleinsuranceclaimapp.model.Claim;
import com.example.vehicleinsuranceclaimapp.model.ClaimImage;
import com.example.vehicleinsuranceclaimapp.model.Policy;
import com.example.vehicleinsuranceclaimapp.service.ClaimManagar;
import com.example.vehicleinsuranceclaimapp.service.UserSession;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class FileClaimFragment extends Fragment {

    private ClaimManagar claimManagar;
    private Button submitClaim;
    private EditText claimDiscription;
    private Spinner policySpinner;
    private PreviewView previewView;
    private Button captureButton;
    private ImageView showCapturedImage;

    private List<Policy> userPolicies;
    private ArrayAdapter<String> policyAdapter;
    AppDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_file_claim, container, false);

        claimManagar = new ClaimManagar();
        submitClaim = view.findViewById(R.id.submitClaim);
        claimDiscription = view.findViewById(R.id.claimDiscription);
        policySpinner = view.findViewById(R.id.policySpinner);
        previewView = view.findViewById(R.id.previewView);
        captureButton = view.findViewById(R.id.captureButton);
//        showCapturedImage = view.findViewById(R.id.showCapturedImage);

        database = AppDatabase.getInstance(requireContext());
        int loggedInUserId = UserSession.getInstance().getLoggedInUserId();

        try {
            checkAndRequestCameraPermission();
        } catch (Exception e) {
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            userPolicies = database.policyDao().getPoliciesByUserId(loggedInUserId);

            requireActivity().runOnUiThread(() -> {
                if (userPolicies.isEmpty()) {
                    Toast.makeText(requireContext(), "No policies found. Please add policies first.", Toast.LENGTH_SHORT).show();
                } else {
                    List<String> policyNames = new ArrayList<>();
                    for (Policy policy : userPolicies) {
                        policyNames.add(policy.policyName + " - " + policy.vehicleNumber);
                    }
                    policyAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, policyNames);
                    policyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    policySpinner.setAdapter(policyAdapter);
                }
            });
        });


        submitClaim.setOnClickListener(v -> {
            if (!isNetworkAvailable()) {
                Toast.makeText(requireContext(), "Network not available. Please check your connection and try again.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (capturedImagePath == null) {
                Toast.makeText(requireContext(), "Please capture an image first!", Toast.LENGTH_SHORT).show();
                return;
            }

            showConfirmationDialog(() -> {
                submitClaimWithImage(loggedInUserId, capturedImagePath);
                navigateToDashBoardFragment();
            });

        });
        return view;
    }

    private void navigateToDashBoardFragment() {

        DashboardFragment dashboardFragment = new DashboardFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, dashboardFragment)
                .addToBackStack(null)
                .commit();
    }

    //network check
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    //custom dialog box
    private void showConfirmationDialog(Runnable onConfirmAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm_claim, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        confirmButton.setOnClickListener(v -> {
            dialog.dismiss();
            if (onConfirmAction != null) {
                onConfirmAction.run();
            }
        });

        dialog.show();
    }



    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private void checkAndRequestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(requireContext(), "Camera permission is required to use this feature", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void submitClaimWithImage(int loggedInUserId, String capturedImagePath) {
        String description = claimDiscription.getText().toString();
        int selectedPolicyPosition = policySpinner.getSelectedItemPosition();

        if (description.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter claim description", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPolicies.isEmpty()) {
            Toast.makeText(requireContext(), "No policies available to attach. Please add policies first.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (capturedImagePath == null || capturedImagePath.isEmpty()) {
            Toast.makeText(requireContext(), "Please capture an image before submitting the claim", Toast.LENGTH_SHORT).show();
            return;
        }

        Policy selectedPolicy = userPolicies.get(selectedPolicyPosition);
        String dateSubmitted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String status = "Pending";

        // Create a new claim with the image path
        Claim newClaim = new Claim(description, status, dateSubmitted, dateSubmitted, loggedInUserId, selectedPolicy.policyId, capturedImagePath);

        Executors.newSingleThreadExecutor().execute(() -> {
            long claimId = database.claimDao().insertClaimAndGetId(newClaim); // Insert claim with image path

            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Claim submitted successfully!", Toast.LENGTH_SHORT).show();
                claimDiscription.setText("");
            });
        });
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());

        cameraProviderFuture.addListener(() -> {
            try {
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                Preview preview = new Preview.Builder().build();

                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                ImageCapture imageCapture = new ImageCapture.Builder().build();

                cameraProviderFuture.get().bindToLifecycle(this, cameraSelector, preview, imageCapture);
                captureButton.setOnClickListener(v -> takePicture(imageCapture));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(getContext()));
    }

    private String capturedImagePath = null;
    private void takePicture(ImageCapture imageCapture) {
        File photoFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                System.currentTimeMillis() + "_vehicle_damage.jpg");

        ImageCapture.OutputFileOptions options = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(options, ContextCompat.getMainExecutor(requireContext()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        capturedImagePath = photoFile.getAbsolutePath();
                        Toast.makeText(requireContext(), "Image saved: " + capturedImagePath, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        exception.printStackTrace();
                        Toast.makeText(requireContext(), "Error saving image", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onResume() {
        super.onResume();
        startCamera();
    }
}
