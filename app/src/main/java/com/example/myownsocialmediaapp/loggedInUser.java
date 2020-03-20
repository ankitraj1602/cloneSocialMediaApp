package com.example.myownsocialmediaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.net.URI;

public class loggedInUser extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabAdapter mTabAdapter;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user);

        setTitle("MY SOCIAL MEDIA APP");

        mViewPager = findViewById(R.id.myViewPager);
        mTabLayout = findViewById(R.id.myTabLayout);
        mToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(mToolbar);

        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabAdapter);

        mTabLayout.setupWithViewPager(mViewPager,true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.logoutUserItem){
            if(Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.
   permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},2000);
            }

        }
        else{captureImage();}
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==2000){

            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                captureImage();

            }

            else{
                Toast.makeText(this, "UNABLE TO PERFORM ACTION,PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }

        }


    }

    public void captureImage(){
        Intent intentForCameraItem = new Intent(Intent.ACTION_PICK, MediaStore.Images.
                Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentForCameraItem,3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3000 && resultCode==RESULT_OK && data != null) {
            try {
             Uri uriForImage = data.getData();
             ImageDecoder.Source imageSource = ImageDecoder.createSource
             (getContentResolver(), uriForImage);
             Bitmap imageBitmap = ImageDecoder.decodeBitmap(imageSource);
             new sharePicturesTab().uploadImagetoServer(imageBitmap);


            } catch (Exception e) {
                e.printStackTrace();}

        }



    }








}
