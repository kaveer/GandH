package com.kavsoftware.kaveer.gandh.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kavsoftware.kaveer.gandh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {
    Button signUp;
    EditText username, email, password, conformPassword;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);


        //async signup
        //return userid and status code
        //if != 200 go to login fragment
        // if 200 get token
        // save as shared preference
        // go to main activity
        return view;
    }

}
