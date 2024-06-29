package com.crimesnap.provider;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.os.IBinder;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCapture.OnImageSavedCallback;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ForegroundService extends Service implements LifecycleOwner {
    public static final String CHANNEL_ID = "FSC";
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private static ImageCapture imageCapture;

    @Override
    public void onCreate() {
        super.onCreate();
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel(input);

        if(Objects.equals(intent.getStringExtra("inputExtra"), "image")) {
            initImageCapture();
        }

        return START_REDELIVER_INTENT;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    public void initImageCapture() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(HelperClass.universalContext);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                if(cameraProvider == null) Log.d("Listener_SSPPYY : Image", "Camera provider is null");

                assert cameraProvider != null;
                bindImageCapture(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Log.e("Listener_SSPPYY : Image", "Error binding camera provider: " + e.getMessage());
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(HelperClass.universalContext));

        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
    }

    private void bindImageCapture(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(HelperClass.cameraFacing)
                .build();

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        Preview preview = new Preview.Builder().build();

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

        Log.d("Listener_SSPPYY : Image", "ImageCapture initialized and bound to lifecycle");

        takePhoto();
    }

    public void takePhoto() {
        try {
            if (imageCapture == null) {
                Log.e("Listener_SSPPYY : Image", "ImageCapture is not initialized");
                return;
            }

            File photoFile = new File("/storage/emulated/0/Pictures/.intruder");

            if (!photoFile.exists()) {
                boolean isCreated = photoFile.mkdirs();
                if (!isCreated) {
                    Log.e("Listener_SSPPYY : Image", "Failed to create directory: " + photoFile.getAbsolutePath());
                    return;
                }
            }

            String time = String.valueOf(System.currentTimeMillis());

            photoFile = new File(photoFile, "intruder_" + time + "_.jpg");

            Log.d("Listener_SSPPYY : Image", "Photo capture started: " + photoFile.getAbsolutePath());

            File finalPhotoFile = photoFile;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(photoFile).build(),
                        getMainExecutor(),
                        new OnImageSavedCallback() {
                            @Override
                            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                                Log.d("Listener_SSPPYY : Image", "Photo capture succeeded: " + finalPhotoFile.getAbsolutePath());
                            }

                            @Override
                            public void onError(@NonNull ImageCaptureException exception) {
                                Log.e("Listener_SSPPYY : Image", "Photo capture failed: " + exception.getMessage(), exception);
                            }
                        });
            }
        } catch (Exception e) {
            Log.e("Listener_SSPPYY : Image", "Photo capture failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void onDestroy() {
        Intent serviceIntents = new Intent(getApplicationContext(), InternalService.class);
        startService(serviceIntents);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel(String input) {
        NotificationChannel serviceChannel = new NotificationChannel(
                CHANNEL_ID,
                "Notifications Service",
                NotificationManager.IMPORTANCE_DEFAULT
        );

        if(Objects.equals(input, "image")) input = "Test Notification 🤖";

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChannel);

        Intent notificationIntent = new Intent(this, InternalService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Notifications Service")
                .setContentText(input)
                .setSmallIcon(android.R.drawable.ic_secure)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        Intent serviceIntents = new Intent(getApplicationContext(), InternalService.class);
        startService(serviceIntents);
    }
}
