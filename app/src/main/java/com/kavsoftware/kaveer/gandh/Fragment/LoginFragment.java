package com.kavsoftware.kaveer.gandh.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kavsoftware.kaveer.gandh.DB.DbHandler.DBHandler;
import com.kavsoftware.kaveer.gandh.Model.AccountViewModel;
import com.kavsoftware.kaveer.gandh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        try{
            DBHandler db = new DBHandler(getContext());
            AccountViewModel result = db.GetUserDetails();
        }catch (Exception ex){
            String v = ex.getMessage();
        }


        return view;
    }

}
