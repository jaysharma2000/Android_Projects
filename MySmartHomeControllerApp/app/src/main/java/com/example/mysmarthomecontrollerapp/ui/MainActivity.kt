package com.example.mysmarthomecontrollerapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mysmarthomecontrollerapp.R
import com.example.mysmarthomecontrollerapp.databinding.ActivityMainBinding
import com.example.mysmarthomecontrollerapp.workmanager.SyncWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)



        val userRules = "Turn off Wi-Fi at night"

        val inputData = Data.Builder()
            .putString("userRules", userRules)
            .build()

        val syncWorkRequest = PeriodicWorkRequestBuilder<SyncWorker>(3, TimeUnit.MINUTES)
            .setInputData(inputData)

            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DeviceSyncWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            syncWorkRequest
        )

        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData("DeviceSyncWork")
            .observe(this) { workInfos ->
                workInfos?.forEach { workInfo ->
                    Log.d("MainActivity", "Work Status: ${workInfo.state}")
                }
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}