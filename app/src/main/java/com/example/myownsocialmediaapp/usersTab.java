package com.example.myownsocialmediaapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class usersTab extends Fragment {

    private ListView mListView;
    private TextView animatedTxt;
    private ArrayList<String> userNameArray;

    private ArrayAdapter mArrayAdapter;

    public usersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        mListView= view.findViewById(R.id.listView);
        userNameArray = new ArrayList<>();

        mArrayAdapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1,userNameArray);

        animatedTxt= view.findViewById(R.id.animatedTxt);
        mListView.setAdapter(mArrayAdapter);

        ParseQuery<ParseUser> queryToGetUsers = ParseUser.getQuery();
        queryToGetUsers.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        queryToGetUsers.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
             if(e==null && objects.size()>0){

                for(ParseUser user : objects){

                    userNameArray.add(user.getUsername());

                }
                 animatedTxt.animate().alpha(0.0f);
                mListView.setVisibility(View.VISIBLE);
                 mListView.setAdapter(mArrayAdapter);
             }
            else{
             }

            }
        });






       return view;
    }

}
