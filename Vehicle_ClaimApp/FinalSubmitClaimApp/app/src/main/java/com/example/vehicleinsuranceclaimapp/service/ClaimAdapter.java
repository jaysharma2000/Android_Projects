package com.example.vehicleinsuranceclaimapp.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.vehicleinsuranceclaimapp.R;
import com.example.vehicleinsuranceclaimapp.dao.ClaimDao;
import com.example.vehicleinsuranceclaimapp.database.AppDatabase;
import com.example.vehicleinsuranceclaimapp.model.Claim;
import java.util.List;
import java.util.concurrent.Executors;

public class ClaimAdapter extends ArrayAdapter<Claim> {

    private final Context context;
    private final AppDatabase database;

    public ClaimAdapter(@NonNull Context context, @NonNull List<Claim> claims) {
        super(context, 0, claims);
        this.context = context;
        this.database = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Claim claim = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_claim, parent, false);
        }


        TextView tvClaimTitle = convertView.findViewById(R.id.tvClaimTitle);
        TextView tvClaimId = convertView.findViewById(R.id.tvClaimId);
        TextView tvPolicy = convertView.findViewById(R.id.tvPolicy);
        TextView tvDescription = convertView.findViewById(R.id.tvDescription);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);
        TextView tvDateSubmitted = convertView.findViewById(R.id.tvDateSubmitted);
        TextView tvDateUpdated = convertView.findViewById(R.id.tvDateUpdated);
        Button deleteBtn = convertView.findViewById(R.id.deleteClaimBtn);
        ImageView claimImage = convertView.findViewById(R.id.claimImage);

        if (claim != null) {

            tvClaimTitle.setText("Claim " + claim.getClaimId());
            tvClaimId.setText("Claim ID: " + claim.getClaimId());
            tvDescription.setText("Description: " + claim.getDiscription());
            tvStatus.setText("Status: " + claim.getStatus());
            tvDateSubmitted.setText("Date Submitted: " + claim.getDateSubmitted());
            tvDateUpdated.setText("Date Updated: " + claim.getDateUpdated());


            Executors.newSingleThreadExecutor().execute(() -> {
                String policyName = database.policyDao().getPolicyNameById(claim.getPolicyId());
                ((Activity) context).runOnUiThread(() -> tvPolicy.setText("Policy:" + policyName));

            });

            Executors.newSingleThreadExecutor().execute(() -> {

                ClaimDao dao = database.claimDao();

                String firstImagePath = dao.getImagePathByClaimId(claim.getClaimId());

                    if (!firstImagePath.isEmpty()) {
                        ((Activity) context).runOnUiThread(() -> {

                            Bitmap bitmap = BitmapFactory.decodeFile(firstImagePath);

                            if (bitmap != null) {
                                claimImage.setImageBitmap(bitmap);

                                claimImage.setOnClickListener(v -> showFullScreenImage(firstImagePath));
                            } else {

                                Toast.makeText( ((Activity) context), "Failed to load image", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {

                        ((Activity) context).runOnUiThread(() ->
                                Toast.makeText( ((Activity) context), "No images found for this claim", Toast.LENGTH_SHORT).show()
                        );
                    }
            });


            deleteBtn.setOnClickListener(v -> {
                Executors.newSingleThreadExecutor().execute(() -> {
                    database.claimDao().deleteClaimById(claim.getClaimId());

                    ((Activity) context).runOnUiThread(() -> {
                        remove(claim);
                        notifyDataSetChanged();
                    });
                });
            });


        }

        return convertView;
    }

    private void showFullScreenImage(String imagePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_fullscreen_image, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        ImageView fullScreenImageView = dialogView.findViewById(R.id.fullScreenImageView);
        Button closeButton = dialogView.findViewById(R.id.closeButton);

        Glide.with(context)
                .load(imagePath)
                .into(fullScreenImageView);

        closeButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

}
