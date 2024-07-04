package com.crimesnap.provider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.crimesnap.pages.ReportCrime;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.S)
public class PermissionManager {
    private static final int REQUEST_FOREGROUND_PERMISSIONS_CODE = 123; // Request code for foreground permissions
    private static final int REQUEST_BACKGROUND_PERMISSIONS_CODE = 124; // Request code for background permissions

    // Define foreground permissions
    private static final String[] FOREGROUND_PERMISSIONS = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.FOREGROUND_SERVICE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            android.Manifest.permission.MANAGE_OWN_CALLS,
            android.Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
    };

    // Define background permissions
    private static final String[] BACKGROUND_PERMISSIONS = {
            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            android.Manifest.permission.FOREGROUND_SERVICE,
            android.Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET,
    };

    public static void checkAndRequestPermissions(Activity activity) {
        List<String> foregroundPermissionsNeeded = new ArrayList<>();
        List<String> backgroundPermissionsNeeded = new ArrayList<>();

        if(activity == null && HelperClass.universalContext != null) {
            activity = (ReportCrime) HelperClass.universalContext;
        } else if (activity == null) {
            return;
        }

        // Check and separate foreground and background permissions
        for (String permission : FOREGROUND_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                foregroundPermissionsNeeded.add(permission);
            }
        }

        for (String permission : BACKGROUND_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                backgroundPermissionsNeeded.add(permission);
            }
        }

        // Request foreground permissions
        if (!foregroundPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, foregroundPermissionsNeeded.toArray(new String[0]), REQUEST_FOREGROUND_PERMISSIONS_CODE);
        }

        // Request background permissions if needed
        if (!backgroundPermissionsNeeded.isEmpty() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(activity, backgroundPermissionsNeeded.toArray(new String[0]), REQUEST_BACKGROUND_PERMISSIONS_CODE);
        }

    }


    public static void checkAndRequestAllFile(Activity activity) {

        if(activity == null && HelperClass.universalContext != null) {
            activity = (Activity) HelperClass.universalContext;
        } else if (activity == null) {
            return;
        }

        if (!Environment.isExternalStorageManager()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivityForResult(intent, 1234);
        }
    }

    public static void handlePermissionsResult(int requestCode, String[] permissions, int[] grantResults, Activity activity) {
        if (requestCode == 1233) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permission Denied", permissions[i]);
                    return;
                }
            }

            Log.d("Permission Granted", "All permissions granted");
        }
    }
}