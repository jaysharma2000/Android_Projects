package com.example.vehicleinsuranceclaimapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.vehicleinsuranceclaimapp.dao.ClaimDao;
import com.example.vehicleinsuranceclaimapp.dao.ClaimImageDao;
import com.example.vehicleinsuranceclaimapp.dao.PolicyDao;
import com.example.vehicleinsuranceclaimapp.dao.UserDao;
import com.example.vehicleinsuranceclaimapp.model.Claim;
import com.example.vehicleinsuranceclaimapp.model.ClaimImage;
import com.example.vehicleinsuranceclaimapp.model.Policy;
import com.example.vehicleinsuranceclaimapp.model.User;

@Database(entities = {Claim.class, User.class, Policy.class, ClaimImage.class}, version = 6 )
public abstract class AppDatabase extends RoomDatabase {

      public abstract ClaimDao claimDao();
      public abstract UserDao userDao();
      public abstract PolicyDao policyDao();
      public abstract ClaimImageDao claimImageDao();
      private  static AppDatabase instance;

      public static synchronized AppDatabase getInstance(Context context){
          if(instance == null){
              instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "claim_database").fallbackToDestructiveMigration().build();
          }
          return instance;
      }

}
