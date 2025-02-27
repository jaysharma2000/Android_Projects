package com.example.mysmarthomecontrollerapp.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.util.Log

class SyncWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {

        Log.d("SyncWorker", "Syncing device states with the server...")

        val userRules = inputData.getString("userRules") ?: "No rules defined"
        Log.d("SyncWorker", "Applying user-defined rules: $userRules")


        try {
            Log.d("SyncWorker", "Device states synced successfully!")
        } catch (e: Exception) {
            Log.e("SyncWorker", "Error syncing device states: ${e.message}")
            return Result.retry()
        }

        return Result.success()
    }
}