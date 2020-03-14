package com.example.myownsocialmediaapp;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileTab extends Fragment implements View.OnClickListener {

    private EditText profileHobby,profileName,profileAge,
            profileProfession,profileSport;

    private Button btnProfileUpdateInfo;

    public profileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        profileName = view.findViewById(R.id.edtProfileName);
        profileAge = view.findViewById(R.id.edtProfileAge);
        profileHobby = view.findViewById(R.id.edtProfileHobby);
        profileSport = view.findViewById(R.id.edtProfileSport);
        profileProfession = view.findViewById(R.id.edtProfileProfession);

        btnProfileUpdateInfo = view.findViewById(R.id.updateProfileInfoBtn);
        btnProfileUpdateInfo.setOnClickListener(this);

        ParseUser user = ParseUser.getCurrentUser();

        if(user.get("ProfileName")==null){
            profileName.setText("");}
        else{
        profileName.setText(user.get("ProfileName")+"");}

        if(user.get("ProfileProfession")==null)
        {profileProfession.setText("");}
        else{profileProfession.setText(user.get("ProfileProfession")+"");}

        if(user.get("ProfilleSport")==null)
        {profileSport.setText("");}
        else{profileSport.setText(user.get("ProfilleSport")+"");}

        if(user.get("ProfileHobby")==null){
            profileHobby.setText("");
        }
       else{ profileHobby.setText(user.get("ProfileHobby")+"");}

       if(user.get("ProfileAge")==null)
       {profileAge.setText("");}
       else{profileAge.setText(user.get("ProfileAge")+"");}



           return view;
    }

    @Override
    public void onClick(View v) {
        ParseUser user = ParseUser.getCurrentUser();
        user.put("ProfileName",profileName.getText().toString());
        user.put("ProfileAge",profileAge.getText().toString());
        user.put("ProfileProfession",profileProfession.getText().toString());
        user.put("ProfilleSport",profileSport.getText().toString());
        user.put("ProfileHobby",profileHobby.getText().toString());

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("SAVING YOUR PROFILE INFORMATION");
        progressDialog.show();
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText(getContext(), "Info Saved", Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

        });

    }
}
