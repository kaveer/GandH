package com.kavsoftware.kaveer.gandh.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kavsoftware.kaveer.gandh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoInternetFragment extends Fragment {


    public NoInternetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_internet, container, false);

        //button click
        //check internet
        //if has internet
        //then navigate to login
    }

}
