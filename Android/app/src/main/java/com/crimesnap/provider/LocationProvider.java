package com.crimesnap.provider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaRouter;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationProvider {

    public static final int PRIORITY_LOW = 0;
    public static final int PRIORITY_MEDIUM = 1;
    public static final int PRIORITY_HIGH = 2;
    public static final int PRIORITY_HIGHEST = 3;
    private final int priority;

    private final SimpleCallback permissionDeniedCallback;
    private final OnSuccessListener<Location> locationCallback;

    Context context;
    FusedLocationProviderClient fusedLocationProviderClient;

    public LocationProvider(Context context, int priority, SimpleCallback permissionDeniedCallback, OnSuccessListener<Location> locationCallback) {
        this.context = context;
        this.priority = priority;
        this.permissionDeniedCallback =  permissionDeniedCallback;
        this.locationCallback = locationCallback;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void getLocation(){
        checkPermission(priority);

        Log.d("LOG_CALL", "Getting location");
        Log.d("LOG_CALL", "Priority: " + priority);

        assert fusedLocationProviderClient != null;
        assert locationCallback != null;
        assert permissionDeniedCallback != null;
        assert context != null;
        assert priority >= PRIORITY_LOW && priority <= PRIORITY_HIGHEST;

        switch (priority){
            case PRIORITY_LOW:
                @SuppressLint("MissingPermission") Task<Location> task = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_PASSIVE, null);
                task.addOnSuccessListener(locationCallback);
                break;
            case PRIORITY_MEDIUM:
                @SuppressLint("MissingPermission") Task<Location> task1 = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_LOW_POWER, null);
                task1.addOnSuccessListener(locationCallback);
                break;
            case PRIORITY_HIGH:
                @SuppressLint("MissingPermission") Task<Location> task2 = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null);
                task2.addOnSuccessListener(locationCallback);
                break;
            case PRIORITY_HIGHEST:
                @SuppressLint("MissingPermission") Task<Location> task3 = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null);
                task3.addOnSuccessListener(locationCallback);
                break;
            default:
                break;
        }
    }

    private void checkPermission(int priority){
        switch (priority){
            case PRIORITY_HIGH:
            case PRIORITY_HIGHEST:
                if(!checkLocationPermissionAccessFineLocation(context) && !checkLocationPermissionAccessCoarseLocation(context) && !checkLocationPermissionAccessBackgroundLocation(context)){
                    permissionDeniedCallback.execute();
                }
                break;
        }
    }


    public static Boolean checkLocationPermissionAccessFineLocation(Context context){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static Boolean checkLocationPermissionAccessCoarseLocation(Context context){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static Boolean checkLocationPermissionAccessBackgroundLocation(Context context){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}