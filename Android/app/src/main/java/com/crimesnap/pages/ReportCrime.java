package com.crimesnap.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.crimesnap.ImageAdapter;
import com.crimesnap.R;
import com.crimesnap.provider.LocationProvider;
import com.crimesnap.provider.PermissionManager;
import com.crimesnap.provider.SimpleCallback;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ReportCrime extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    private Spinner spinnerCrimeType;
    private EditText etCrimeName;
    private Button btnCaptureImage;
    private Button btnSubmit;
    private CheckBox checkBoxAnonymous;
    private GridView gridView;
    private ImageAdapter imageAdapter;
    private List<String> imagePaths;

    public int getSelectedImageCount() {
        return selectedImageCount;
    }

    public void setSelectedImageCount(int selectedImageCount) {
        this.selectedImageCount = selectedImageCount;
    }

    private int selectedImageCount;
    private double crimeLatitude;
    private double crimeLongitude;

    // Firebase instances
    private FirebaseStorage storage;
    private FirebaseFirestore firestore;

    // Activity result launcher for capturing images
    private final ActivityResultLauncher<Intent> imageCaptureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        // Handle gallery image selection
                        handleImageSelection(data.getData());
                    } else {
                        // Handle camera image capture
                        handleCameraImage(data);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_crime);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PermissionManager.checkAndRequestPermissions(this);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize Firebase normally for Android:
        FirebaseApp.initializeApp(this); // 'this' refers to the current context

        // Now you can initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Initialize Firebase instances
        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();

        SimpleCallback permissionDeniedCallback = () -> Log.d("Permission", "Permission Denied");

        LocationProvider locationProvider = new LocationProvider(
                this,
                LocationProvider.PRIORITY_HIGH,
                permissionDeniedCallback,
                location -> {
                    if (location != null) {
                        crimeLatitude = location.getLatitude();
                        crimeLongitude = location.getLongitude();
                        Log.d("Location", "Location: " + crimeLatitude + ", " + crimeLongitude);
                    }
                }
        );

        locationProvider.getLocation();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        spinnerCrimeType = findViewById(R.id.spinner_crime_type);
        etCrimeName = findViewById(R.id.et_crime_name);
        btnCaptureImage = findViewById(R.id.btn_capture_image);
        btnSubmit = findViewById(R.id.btn_submit);
        checkBoxAnonymous = findViewById(R.id.checkBox);
        gridView = findViewById(R.id.grid_view_images);

        // Initialize image paths list and adapter
        imagePaths = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, imagePaths);
        gridView.setAdapter(imageAdapter);

        // Set up crime type spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.crime_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCrimeType.setAdapter(adapter);

        // Capture or upload image button click listener
        btnCaptureImage.setOnClickListener(view -> openImageChooser());

        // Submit button click listener
        btnSubmit.setOnClickListener(view -> submitCrimeReport());
    }

    private void openImageChooser() {
        if (selectedImageCount < 3) {
            Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            Intent chooserIntent = Intent.createChooser(pickPhotoIntent, "Select or take a new picture");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePictureIntent});

            imageCaptureLauncher.launch(chooserIntent);
        } else {
            Toast.makeText(this, "Maximum 3 images already selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleImageSelection(Uri selectedImageUri) {
        if (selectedImageCount < 3) {
            String imagePath = selectedImageUri.toString();
            addImage(imagePath);
            selectedImageCount++;
            Log.d("Image Selection", "Selected image: " + imagePath);
            Log.d("Image Selection", "Total selected images: " + selectedImageCount);
        } else {
            Toast.makeText(this, "Maximum 3 images allowed", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCameraImage(Intent cameraData) {
        Bundle extras = cameraData.getExtras();
        if (extras != null && extras.get("data") != null) {
            // Handle camera image capture
            // Convert captured image to bitmap or save it to file
            // For demonstration, let's use a placeholder path
            String imagePath = "file:///path/to/image.jpg";
            addImage(imagePath);
            Log.d("Camera Image", "Captured image path: " + imagePath);
        }
    }

    private void addImage(String imagePath) {
        if (imagePaths.size() < 3) {
            imagePaths.add(imagePath);
            imageAdapter.notifyDataSetChanged();
            Log.d("Add Image", "Image added: " + imagePath);
            Log.d("Add Image", "Total images: " + imagePaths.size());
        } else {
            Toast.makeText(this, "Maximum 3 images already selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteImage(int position) {
        imagePaths.remove(position);
        imageAdapter.notifyDataSetChanged();
        selectedImageCount--; // Decrement the selectedImageCount
        updateImageSelectionToast();
        Log.d("Delete Image", "Image at position " + position + " deleted");
        Log.d("Delete Image", "Total images: " + imagePaths.size());
    }

    private void updateImageSelectionToast() {
        if (selectedImageCount == 3) {
            Toast.makeText(ReportCrime.this, "Maximum 3 images already selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitCrimeReport() {
        String crimeType = spinnerCrimeType.getSelectedItem().toString();
        String crimeName = etCrimeName.getText().toString();

        if (crimeType.isEmpty() || crimeName.isEmpty()) {
            Toast.makeText(ReportCrime.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("Submit Crime Report", "Image paths before upload: " + imagePaths.toString());

        uploadImagesAndLogReport(crimeType, crimeName);
        clearForm();
    }

    private void uploadImagesAndLogReport(String crimeType, String crimeName) {
        List<String> uploadedImageUrls = new ArrayList<>();
        int imagesSize = imagePaths.size();
        for (String imagePath : imagePaths) {
            Uri fileUri = Uri.parse(imagePath);
            String fileName = fileUri.getLastPathSegment();
            StorageReference storageRef = storage.getReference().child("crime_images/" + fileName);

            storageRef.putFile(fileUri)
                    .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                uploadedImageUrls.add(uri.toString());
                                Log.d("Image Upload", "Uploaded image URL: " + uri.toString());
                                if (uploadedImageUrls.size() == imagesSize) {
                                    logCrimeReport(crimeType, crimeName, uploadedImageUrls, crimeLongitude, crimeLatitude);
                                }
                            }))
                    .addOnFailureListener(e -> {
                        Toast.makeText(ReportCrime.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Image Upload", "Failed to upload image", e);
                    });
        }
    }

    private void logCrimeReport(String crimeType, String crimeName, List<String> imageUrls, double longitude, double latitude) {
        JSONObject crimeReport = new JSONObject();
        try {
            crimeReport.put("crimeType", crimeType);
            crimeReport.put("crimeDesc", crimeName);
            crimeReport.put("crimeLong", longitude);
            crimeReport.put("crimeLat", latitude);
            crimeReport.put("anonymous", checkBoxAnonymous.isChecked());

            Random random = new Random();
            int userId = random.nextInt(100000);
            crimeReport.put("userId", userId);

            JSONArray imagesArray = new JSONArray();
            for (String imageUrl : imageUrls) {
                imagesArray.put(imageUrl);
            }
            crimeReport.put("crimeImages", imagesArray);

            // Convert JSON object to a Map
            Map<String, Object> crimeReportMap = new HashMap<>();
            crimeReport.keys().forEachRemaining(key -> {
                try {
                    Object value = crimeReport.get(key);
                    if (value instanceof JSONArray) {
                        List<String> list = new ArrayList<>();
                        JSONArray jsonArray = (JSONArray) value;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            list.add(jsonArray.getString(i));
                        }
                        crimeReportMap.put(key, list);
                    } else {
                        crimeReportMap.put(key, value);
                    }
                } catch (JSONException e) {
                    Log.e("Crime Report JSON", "Error creating map from JSON object", e);
                }
            });

            // Save the report to Firestore
            firestore.collection("crimes")
                    .add(crimeReportMap)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(ReportCrime.this, "Crime report submitted successfully", Toast.LENGTH_SHORT).show();
                        Log.d("Crime Report", "DocumentSnapshot added with ID: " + documentReference.getId());
                        clearForm();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ReportCrime.this, "Error adding document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.w("Crime Report", "Error adding document", e);
                    });
        } catch (JSONException e) {
            Log.e("Crime Report JSON", "Error creating JSON object", e);
        }
    }


    private void clearForm() {
        // Clear form fields
        etCrimeName.getText().clear();
        imagePaths.clear();
        imageAdapter.notifyDataSetChanged();
        // Reset image count
        selectedImageCount = 0;
        Log.d("Clear Form", "Form cleared");
    }
}
