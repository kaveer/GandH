package com.kavsoftware.kaveer.gandh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //check android db for credential
        //if found take credentital to generate access token
        //navigate to main screen
        //if not found
        // navigate to login screen
    }
}
