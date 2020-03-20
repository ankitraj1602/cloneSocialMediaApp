package com.example.myownsocialmediaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ProgressDialog;
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
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.net.URI;

import static android.graphics.Bitmap.CompressFormat.PNG;

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

        if(item.getItemId()==R.id.postImgageItem){
            if(Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.
   permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},2000);
            }
            else{captureImage();}
        }

        else if(item.getItemId()==R.id.logoutUserItem){
            ParseUser.getCurrentUser().logOut();
            finish();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);//THIS IS TO DIRECT THE APP TO ORIGINAL PAGE
        }

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
             Bitmap imageBit = MediaStore.Images.Media.
                     getBitmap(getContentResolver(),uriForImage);
           //  new sharePicturesTab().uploadImagetoServer(imageBit);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imageBit.compress(PNG,100,byteArrayOutputStream);
                byte[] byteHoldingImage = byteArrayOutputStream.toByteArray();

                ParseFile imgFile = new ParseFile("img.png",byteHoldingImage);
                ParseObject imgClass = new ParseObject("user_posts") ;
                imgClass.put("posts",imgFile);
                imgClass.put("username",ParseUser.getCurrentUser().getUsername());

                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("UPLOADING....");
                dialog.show();
                imgClass.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(getApplicationContext(), "SUCCESSFULLY POSTED", Toast.LENGTH_SHORT).show();

                        }

                        else{
                            Toast.makeText(getApplicationContext(), e.getMessage()+"\n"+"PLEASE RETRY", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });

            }


             catch (Exception e) {
                e.printStackTrace();}

        }



    }








}
