package com.example.vehicleinsuranceclaimapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.vehicleinsuranceclaimapp.fragment.LoginFragment;
import com.example.vehicleinsuranceclaimapp.service.ClaimUpdateService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent serviceIntent = new Intent(this, ClaimUpdateService.class);
        startService(serviceIntent);



        TextView beginButton = findViewById(R.id.beginButton);
        LinearLayout mainContent = findViewById(R.id.mainContent);
        FrameLayout fragmentContainer = findViewById(R.id.fragmentContainer);


        beginButton.setOnClickListener(v -> {
            mainContent.setVisibility(View.GONE);

            fragmentContainer.setVisibility(View.VISIBLE);

            loadFragment(new LoginFragment(), "LoginFragment");
        });
    }

    public void loadFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(null).commit();

    }
}