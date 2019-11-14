package com.example.helloworldactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private File mPhotoFile;
    private Context mContext;
    private static final int REQUEST_PHOTO=0;
    private ImageView mPhotoView;

    @Override
            protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mContext = this.getApplicationContext();
        mPhotoFile = getPhotoFile();
        mPhotoView = findViewById(R.id.captured_photo);
    }

    public void ferFoto(View view) {
        ImageView nPhotoView;

        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri uri = FileProvider.getUriForFile(this, "com.example.helloworldactivity", mPhotoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        List<ResolveInfo> cameraActivities =
                this.getPackageManager().queryIntentActivities(
                        captureImage, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo activity : cameraActivities) {
            this.grantUriPermission(activity.activityInfo.packageName,
                    uri, getIntent().FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        startActivityForResult(captureImage, REQUEST_PHOTO);
    }

    public String getPhotoFilename() {return "IMG_Prova.jpg";}

    public File getPhotoFile(){
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, getPhotoFilename());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_PHOTO){
            Uri uri = FileProvider.getUriForFile(this,
                    "com.example.helloworldactivity.cameracapture.fileprovider",
                    mPhotoFile);

            this.revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
            }
        }
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), this);
            mPhotoView.setImageBitmap(bitmap);
        }
    }
    }

    /*private ImageView imageHolder;
    private final int requestCode = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageHolder = (ImageView) findViewById(R.id.captured_photo);
        Button capturedImageButton = (Button) findViewById(R.id.button1);
        capturedImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, requestCode);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageHolder.setImageBitmap(bitmap);
        }
    }
*/