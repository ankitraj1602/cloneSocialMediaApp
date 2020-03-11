package com.example.myownsocialmediaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

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

//    @Override
//    public void onClick(View v) {
//        ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e==null){
//
//                    finish();
//                }
//                else{
//                    Toast.makeText(loggedInUser.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }







}
