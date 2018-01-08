package com.kavsoftware.kaveer.gandh.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.kavsoftware.kaveer.gandh.AsyncTask.Login;
import com.kavsoftware.kaveer.gandh.Configuration.AppConfig;
import com.kavsoftware.kaveer.gandh.DB.DbHandler.DBHandler;
import com.kavsoftware.kaveer.gandh.Model.AccountViewModel;
import com.kavsoftware.kaveer.gandh.Model.TokenViewModel;
import com.kavsoftware.kaveer.gandh.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AccountActivity extends AppCompatActivity {

    public static final String tokenSession = "TokenSession" ;
    public static final String tokenKey = "tokenKey";
    AppConfig config = new AppConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        try{
            AccountViewModel account = GetLocalCredential(getBaseContext());
            if (account != null || account.getEmail() != ""){
                account = GetUserDetails(AccountActivity.this, account);

                if (account.getUserId() > 0 || account != null){

                    if (account.isReactivated()){
                        Toast messageBox = Toast.makeText(AccountActivity.this , "Account Reactivated" , Toast.LENGTH_LONG);
                        messageBox.show();
                    }

                    TokenViewModel token = GetToken(AccountActivity.this, account);
                    SaveTokenSession(token);
                    NavigateToMainActivity(token);
                }
                else {
                    Toast messageBox = Toast.makeText(AccountActivity.this , "Login fail try again..." , Toast.LENGTH_LONG);
                    messageBox.show();
                    NavigateToLogin();
                }

            }else {
                NavigateToLogin();
            }
        }catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    private void SaveTokenSession(TokenViewModel token) {
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(tokenSession, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(tokenKey, token.getToken());
        editor.commit();
    }

    private void NavigateToLogin() {
//        MainFragment fragment = new MainFragment();
//        android.support.v4.app.FragmentTransaction fmTransaction = getSupportFragmentManager().beginTransaction();
//        fmTransaction.replace(R.id.Frame_container, fragment);
//        fmTransaction.commit();
    }

    private AccountViewModel GetUserDetails(AccountActivity accountActivity, AccountViewModel account) {
        AccountViewModel result = null;

        try {

            String item = new Login(accountActivity).execute(config.getLoginEndPoint(), account.getEmail(), account.getPassword(), String.valueOf(account.getLoginType())).get();
            if (item != null){
                result = DeserializeModel(item);
            }
            else {
                result = null;
                return result;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    private AccountViewModel DeserializeModel(String item) {
        AccountViewModel result = new AccountViewModel();

        JSONObject jsonResult = null;
        try {
            jsonResult = new JSONObject(item);
            result.setUserId(jsonResult.getInt("userid"));
            result.setUsername(jsonResult.getString("user"));
            result.setEmail(jsonResult.getString("email"));
            result.setPassword(jsonResult.getString("password"));
            result.setActive(jsonResult.getBoolean("isactive"));
            result.setLoginType(jsonResult.getInt("logintype"));
            result.setReactivated(jsonResult.getBoolean("isreactivated"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  result;
    }

    private TokenViewModel GetToken(AccountActivity accountActivity, AccountViewModel account) {
        TokenViewModel result = new TokenViewModel();

        return result;
    }

    private void NavigateToMainActivity(TokenViewModel token) {
       // Intent main = new Intent(SignUp.this, VMS.class);
      //  startActivity(main);
    }

    private AccountViewModel GetLocalCredential(Context baseContext) {
        AccountViewModel result;

        DBHandler db = new DBHandler(baseContext);
        result = db.GetUserDetails();

        return result;
    }
}
