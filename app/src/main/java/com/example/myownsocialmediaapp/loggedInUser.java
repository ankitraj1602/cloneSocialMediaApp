package com.example.myownsocialmediaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class loggedInUser extends AppCompatActivity implements View.OnClickListener {
private Button logOutLoggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user);
        logOutLoggedIn = findViewById(R.id.logOutLoggedInBtn);

        logOutLoggedIn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){

                    finish();
                }
                else{
                    Toast.makeText(loggedInUser.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
