package com.example.myownsocialmediaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class logInActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText loggedInEnterUserName,loggedInEnterPass;
    private Button loggedInLogInBtn;

    private ConstraintLayout consLoLogInActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        if (ParseUser.getCurrentUser() != null) {
        //    ParseUser.getCurrentUser().logOut();
            transitionToSMA();
        }

        consLoLogInActivity = findViewById(R.id.consLyLogInActivity);
        loggedInEnterUserName = findViewById(
                R.id.loggedInEnterUn);
        loggedInEnterPass = findViewById(R.id.loggedInEnterPass);
        loggedInEnterPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
               if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){

                   onClick(loggedInLogInBtn);


               }


                return false;
            }
        });

        loggedInLogInBtn = findViewById(R.id.btnLoggedInLogIn);


        loggedInLogInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        ParseUser user = new ParseUser();
        final  ProgressDialog dialog = new ProgressDialog(logInActivity.this);
        dialog.setMessage("Logging In");
        dialog.show();

        user.logInInBackground(loggedInEnterUserName.getText().toString(),
                loggedInEnterPass.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e==null){

         Toast.makeText(logInActivity.this, "Success", Toast.LENGTH_SHORT).show();
         transitionToSMA();
                        }
                        else{
         Toast.makeText(logInActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });


    }


    public void layoutTapped (View view){
        try{

        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);}

        catch(Exception e){
            e.printStackTrace();
        }



    }

    private void transitionToSMA(){
        Intent intentLoggedIn = new Intent (logInActivity.this,loggedInUser.class);
        startActivity(intentLoggedIn);
        finish();



    }
}