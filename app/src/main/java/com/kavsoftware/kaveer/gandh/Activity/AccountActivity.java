package com.kavsoftware.kaveer.gandh.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.kavsoftware.kaveer.gandh.Configuration.AppConfig;
import com.kavsoftware.kaveer.gandh.Fragment.LoginFragment;
import com.kavsoftware.kaveer.gandh.Fragment.NoInternetFragment;
import com.kavsoftware.kaveer.gandh.R;

public class AccountActivity extends AppCompatActivity {

    public static final String tokenSession = "TokenSession" ;
    public static final String tokenKey = "tokenKey";
    AppConfig config = new AppConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        try{
            if (isNetworkConnected(AccountActivity.this, getBaseContext())){
                  NavigateToLogin();
            }else {
                NavigateToNoInternet();
                Toast messageBox = Toast.makeText(AccountActivity.this , "No internet ..." , Toast.LENGTH_LONG);
                messageBox.show();
            }

        }catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    private void NavigateToNoInternet() {
        NoInternetFragment fragment = new NoInternetFragment();
        android.support.v4.app.FragmentTransaction fmTransaction = getSupportFragmentManager().beginTransaction();
        fmTransaction.replace(R.id.MainFrameLayout, fragment);
        fmTransaction.commit();
    }

    public  boolean isNetworkConnected(FragmentActivity activity, Context context){
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) activity.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return (mNetworkInfo == null) ? false : true;

        }catch (NullPointerException e){
            return false;

        }
    }

    private void NavigateToLogin() {
        LoginFragment fragment = new LoginFragment();
        android.support.v4.app.FragmentTransaction fmTransaction = getSupportFragmentManager().beginTransaction();
        fmTransaction.replace(R.id.MainFrameLayout, fragment);
        fmTransaction.commit();
    }

}
