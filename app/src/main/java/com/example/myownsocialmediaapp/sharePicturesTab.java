package com.example.myownsocialmediaapp;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import static android.graphics.Bitmap.CompressFormat.PNG;


/**
 * A simple {@link Fragment} subclass.
 */
public class sharePicturesTab extends Fragment implements View.OnClickListener {

    private ImageView choosePicImg;
    private EditText descriptionImg;
    private Button sharePicBtn;
    private TextView txtTakePicture;
    private Bitmap imageBitmap;boolean imageBitmapUsed=false;
    private Bitmap picBitmap;boolean picBitmapUsed=false;

    public sharePicturesTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share_pictures_tab, container, false);

         choosePicImg = view.findViewById(R.id.choosePictureImg);
         descriptionImg= view.findViewById(R.id.edtRemindShare);
         sharePicBtn = view.findViewById(R.id.sharePictureBtn);

         choosePicImg.setOnClickListener(sharePicturesTab.this);
         sharePicBtn.setOnClickListener(sharePicturesTab.this);

         txtTakePicture = view.findViewById(R.id.takePictureTxt);
         txtTakePicture.setOnClickListener(sharePicturesTab.this);



    return view;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.choosePictureImg:
                if(Build.VERSION.SDK_INT >=23 && ActivityCompat.checkSelfPermission
                        (getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!=
                        PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);}

                else{

                    getChoosenImage();
                }

                break;

            case R.id.takePictureTxt:

                Intent intentToTakePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intentToTakePic.resolveActivity(getActivity().getPackageManager())
                !=null){
                startActivityForResult(intentToTakePic,4000);}


                break;

            case R.id.sharePictureBtn:
                if(descriptionImg.getText().toString().equals("")){
                    Toast.makeText(getContext(), "PLEASE WRITE A DESCRIPTION",
                            Toast.LENGTH_SHORT).show();

                }

                else{
                    if(picBitmapUsed==true){
                        uploadImagetoServer(picBitmap);
                    }

                    else if(imageBitmapUsed==true){
                        uploadImagetoServer(imageBitmap);
                    }


                }



                break;

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if(requestCode==2000){

           if(grantResults[0]==PackageManager.PERMISSION_GRANTED &&
           grantResults.length>0){

               getChoosenImage();

           }


        }
    }


    public void getChoosenImage(){

      //  Toast.makeText(getContext(), "ACCESS GRANTED", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,3000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==3000){

            if(resultCode == Activity.RESULT_OK){
                try{
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(
                            selectedImage,filePathColumn,null,null,null
                    );
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    imageBitmap = BitmapFactory.decodeFile(picturePath);

                    choosePicImg.setImageBitmap(imageBitmap);
                    imageBitmapUsed=true;//Just so that i can be assured the exter
                    //nal storage has been used.

                }

                catch (Exception e){e.printStackTrace();}
            }



        }

         if(requestCode==4000){

            try{
            if(resultCode==Activity.RESULT_OK){

                Bundle extras = data.getExtras();
                picBitmap = (Bitmap) extras.get("data");
               choosePicImg.setImageBitmap(picBitmap);
               picBitmapUsed=true;//to be assured picBitmap has been used.


            }}

            catch (Exception e){
                e.printStackTrace();
            }




        }
    }


    public void uploadImagetoServer(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(PNG,100,byteArrayOutputStream);
        byte[] byteHoldingImage = byteArrayOutputStream.toByteArray();

        ParseFile imgFile = new ParseFile("img.png",byteHoldingImage);
        ParseObject imgClass = new ParseObject("user_posts") ;
        imgClass.put("posts",imgFile);
        imgClass.put("image_des",descriptionImg.getText().toString());
        imgClass.put("username",ParseUser.getCurrentUser().getUsername());

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("UPLOADING");
        dialog.show();
        imgClass.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){

                    Toast.makeText(getContext(), "SUCCESSFULLY POSTED", Toast.LENGTH_SHORT).show();

                }

                else{
                    Toast.makeText(getContext(), e.getMessage()+"\n"+"PLEASE RETRY", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }
}
