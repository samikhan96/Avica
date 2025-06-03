package com.optimum.Avica.UI.Patient.History;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.optimum.Avica.HttpUtils.AppServices;
import com.optimum.Avica.Listener.ServiceListener;
import com.optimum.Avica.Models.User;
import com.optimum.Avica.R;
import com.optimum.Avica.UI.Patient.Dialogs.AddPHRDialog;
import com.optimum.Avica.Utils.AppUtils;
import com.optimum.Avica.Utils.UserPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class AddRecords extends AppCompatActivity {


    EditText et_title, et_desc;
    Button loginBtn;
    ImageView img;
    String s_title, s_desc;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    String imageUrl;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        user= UserPrefs.getGetUser();
        loginBtn = findViewById(R.id.sendBtn);
        et_title = findViewById(R.id.et_title);
        et_desc = findViewById(R.id.et_desc);
        img = findViewById(R.id.img);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

        }
    }

    public File getFileFromUri(Context context, Uri uri) {
        File file = null;
        try {
            String fileName = "temp_upload_file_" + System.currentTimeMillis();
            File dir = context.getCacheDir();
            file = new File(dir, fileName);

            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public void uploadImage() {

        // Convert URI to File
        File imageFile = getFileFromUri(this, imageUri);  // <-- Use this instead of real path

        if (imageFile != null) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("filePath", imageFile); // send to uploader
                AppServices.Uploader("UPLOAD_TAG", obj, new ServiceListener<String, String>() {
                    @Override
                    public void success(String result) {
                        AppUtils.Toast("Upload Success: " + result);
                        try {
                            JSONObject data = new JSONObject(result);
                            imageUrl = data.getString("url");
                            validate();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Invalid response format", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void error(String error) {
                        AppUtils.Toast("Upload Failed: " + error);

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validate() {
        s_title = et_title.getText().toString();
        s_desc = et_desc.getText().toString();
        if (TextUtils.isEmpty(s_title) || TextUtils.isEmpty(s_desc)) {
            AppUtils.Toast("Please fill all fields");
            return false;
        }
        addPHR();
        return true;
    }

    public void addPHR() {
        AppUtils.showProgressDialog(AddRecords.this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("patient_id", user.id);
            jsonObject.put("uri", imageUrl);
            jsonObject.put("title", s_title);
            jsonObject.put("description", s_desc);
            jsonObject.put("type", "RECORD");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppServices.AddPHR(AddRecords.class.getSimpleName(), jsonObject, new ServiceListener<String, String>() {
            @Override
            public void success(String success) {
                AppUtils.dismisProgressDialog(AddRecords.this);
                AddPHRDialog cdd = new AddPHRDialog(AddRecords.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(AddRecords.this);

            }
        });
    }
}
