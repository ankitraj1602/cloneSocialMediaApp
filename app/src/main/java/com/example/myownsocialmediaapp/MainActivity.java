package com.example.myownsocialmediaapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.security.Key;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private EditText userNameSignUp,emailSignUp,passSignUp;
private Button btnSignUp,btnLogInSignUp;

private ProgressBar progressBar;

private ConstraintLayout constraintLayout;
private InputMethodManager inputMethodManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameSignUp =findViewById(R.id.signUpUserName);
        constraintLayout = findViewById(R.id.consMainLayout);


        passSignUp=findViewById(R.id.signUpPassWord);
        emailSignUp=findViewById(R.id.enterEmailSignUp);

        passSignUp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);


                }

               return false;

            }
        });



        btnLogInSignUp=findViewById(R.id.logInMainActivityBtn);
        btnLogInSignUp.setOnClickListener(this);
        btnSignUp=findViewById(R.id.signUpMainActivityBtn);
        btnSignUp.setOnClickListener(this);

        if(ParseUser.getCurrentUser()!=null){

        //    ParseUser.getCurrentUser().logOut();
        transitionToSMA();
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.signUpMainActivityBtn:
if(emailSignUp.getText().toString().equals("")){
    Toast.makeText(this, "Please Provide a Valid Email", Toast.LENGTH_LONG).show();
}

else{
                final ParseUser parseUser = new ParseUser();

  parseUser.setUsername(userNameSignUp.getText().toString());
  parseUser.setEmail(emailSignUp.getText().toString());
  parseUser.setPassword(passSignUp.getText().toString());

  final ProgressDialog progressDialog = new ProgressDialog(this);
  progressDialog.setMessage("Signing up "+ userNameSignUp.getText().toString());
  progressDialog.show();

             parseUser.signUpInBackground(new SignUpCallback() {
                 @Override
                 public void done(ParseException e) {


                     if(e==null){
                         Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                         transitionToSMA();
                     }

                     else{
  AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
  alertDialog.setTitle("Please Retry");
  alertDialog.setMessage(e.getMessage());
  //alertDialog.setCancelable(true);
  alertDialog.setNegativeButton("CLICK TO RETRY", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
          dialog.dismiss();
      }
  });

  alertDialog.show();
                     }
                     progressDialog.dismiss();
                 }
             });}

                break;


            case R.id.logInMainActivityBtn:

                Intent intentToLoggedInActivity = new Intent(this,logInActivity.class);
                startActivity(intentToLoggedInActivity);




                break;








        }





    }

    public void layoutTapped(View view){
       try{
        inputMethodManager =(InputMethodManager) getSystemService
                (INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow
                (getCurrentFocus().getWindowToken(),0);}

       catch(Exception e){

           e.printStackTrace();
       }




    }


    private void transitionToSMA(){
        Intent intentLoggedIn = new Intent (MainActivity.this,loggedInUser.class);
        startActivity(intentLoggedIn);



    }
}
