package com.kavsoftware.kaveer.gandh.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kavsoftware.kaveer.gandh.AsyncTask.ValidToken;
import com.kavsoftware.kaveer.gandh.Configuration.AppConfig;
import com.kavsoftware.kaveer.gandh.Model.AccountViewModel;
import com.kavsoftware.kaveer.gandh.Model.TokenViewModel;
import com.kavsoftware.kaveer.gandh.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TokenViewModel token = new TokenViewModel();
    SharedPreferences sharedpreferences;
    AppConfig config = new AppConfig();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        token = GetTokenFromSession();
        if (token == null){
            Logout();
            NavigateToAccountActivity();
        }
        else {
            String userDetailsJson = GetUserDetailsFromToken(token.getToken(), MainActivity.this);
            AccountViewModel userDetails = DeserializeUserDetails(userDetailsJson);
            if (userDetails.getStatusCode() == 200){

            }
            else {
                Toast messageBox = Toast.makeText(MainActivity.this , userDetails.getMessage() , Toast.LENGTH_LONG);
                messageBox.show();

                Logout();
                NavigateToAccountActivity();
            }
        }
       
    }

    private AccountViewModel DeserializeUserDetails(String userDetailsJson) {
        AccountViewModel result = new AccountViewModel();

        JSONObject jsonResult = null;
        try {
            jsonResult = new JSONObject(userDetailsJson);
            result.setStatusCode(jsonResult.getInt("StatusCode"));
            result.setMessage(jsonResult.getString("Message"));

            if (result.getStatusCode() == 200) {
                result.setUserId(jsonResult.getInt("userid"));
                result.setUsername(jsonResult.getString("user"));
                result.setEmail(jsonResult.getString("email"));
                result.setPassword(jsonResult.getString("password"));
                result.setActive(jsonResult.getBoolean("isactive"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  result;
    }

    private String GetUserDetailsFromToken(String token, MainActivity activity) {
        String result = null;
        try {
            result = new ValidToken(activity).execute(config.getValidTokenEndPoint(), token).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void NavigateToAccountActivity() {
        Intent main = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(main);
    }

    private void Logout() {
        sharedpreferences = getSharedPreferences(AccountActivity.tokenSession, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    private TokenViewModel GetTokenFromSession() {
        TokenViewModel result = new TokenViewModel();
       
        sharedpreferences = getSharedPreferences(AccountActivity.tokenSession, Context.MODE_PRIVATE);
        result.setToken(sharedpreferences.getString(AccountActivity.tokenKey.toString(), ""));

        if (result.getToken() == "")
            return null;
        
        return result;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
