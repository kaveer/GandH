package com.kavsoftware.kaveer.gandh.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kavsoftware.kaveer.gandh.Activity.MainActivity;
import com.kavsoftware.kaveer.gandh.AsyncTask.SignUp;
import com.kavsoftware.kaveer.gandh.AsyncTask.Token;
import com.kavsoftware.kaveer.gandh.Configuration.AppConfig;
import com.kavsoftware.kaveer.gandh.Configuration.PasswordEncrypt;
import com.kavsoftware.kaveer.gandh.Model.AccountViewModel;
import com.kavsoftware.kaveer.gandh.Model.TokenViewModel;
import com.kavsoftware.kaveer.gandh.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.kavsoftware.kaveer.gandh.Activity.AccountActivity.tokenKey;
import static com.kavsoftware.kaveer.gandh.Activity.AccountActivity.tokenSession;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {
    Button signUp, back;
    EditText username, email, password, conformPassword;
    AppConfig config = new AppConfig();
    PasswordEncrypt passwordEncrypt = new PasswordEncrypt();


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        try{
            InitializeControl(view);

            signUp = view.findViewById(R.id.BtnSignUp);
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (IsValid()){
                        AccountViewModel account = SignUp(getActivity());

                        if (account.getStatusCode() == 200){
                            TokenViewModel token = GetToken(getActivity(), account);
                            if (token.getStatusCode() == 200){
                                SaveTokenSession(token);
                                NavigateToMainActivity();
                            }else{
                                Toast messageBox = Toast.makeText(getActivity() , token.getMessage() , Toast.LENGTH_LONG);
                                messageBox.show();
                            }
                        }else {
                            Toast messageBox = Toast.makeText(getActivity() , account.getMessage() , Toast.LENGTH_LONG);
                            messageBox.show();
                        }
                    }
                }
            });

            back = view.findViewById(R.id.BtnBackToLogin);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginFragment fragment = new LoginFragment();
                    android.support.v4.app.FragmentTransaction fmTransaction = getFragmentManager().beginTransaction();
                    fmTransaction.replace(R.id.MainFrameLayout, fragment);
                    fmTransaction.commit();
                }
            });

        }
        catch (Exception ex){
           String t = ex.getMessage();
        }

        return view;
    }

    private AccountViewModel SignUp(FragmentActivity activity) {
        AccountViewModel result = new AccountViewModel();

        try {

            String item = new SignUp(activity).execute(config.getSignUpEndPoint(), username.getText().toString(),  email.getText().toString(), passwordEncrypt.Encrypt(password.getText().toString())).get();
            if (item != null){
                result = DeserializeAccountViewModel(item);
                return  result;
            }
            else {
                return null;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    private AccountViewModel DeserializeAccountViewModel(String item) {
        AccountViewModel result = new AccountViewModel();

        JSONObject jsonResult = null;
        try {
            jsonResult = new JSONObject(item);
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

    private boolean IsValid() {
        boolean result = true;

        if (username.getText().toString().length() == 0){
            username.setError("Enter UserName");
            return false;
        }

        if (email.getText().toString().length() == 0){
            email.setError("Enter email");
            return false;
        }

        if (!email.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            email.setError("Invalid Email Format");
            return false;
        }

        if (password.getText().toString().length() == 0){
            password.setError("Enter password");
            return false;
        }

        if (conformPassword.getText().toString().length() == 0){
            conformPassword.setError("Enter confirm password");
            return false;
        }

        if (!conformPassword.getText().toString().equals(password.getText().toString())){
            conformPassword.setError("Password not match");
            return false;
        }

        return  result;
    }

    private void InitializeControl(View view) {
        username = view.findViewById(R.id.TxtUsernmae);
        email = view.findViewById(R.id.TxtEmail);
        password = view.findViewById(R.id.TxtPassword);
        conformPassword = view.findViewById(R.id.TxtConfirmPassword);

    }

    private TokenViewModel GetToken(FragmentActivity activity, AccountViewModel account) {
        TokenViewModel result = new TokenViewModel();

        try {

            String item = new Token(activity).execute(config.getTokenEndPoint(), String.valueOf(account.getUserId())).get();
            if (item != null){
                result = DeserializeTokenViewModel(item);
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

    private TokenViewModel DeserializeTokenViewModel(String item) {
        TokenViewModel result = new TokenViewModel();

        JSONObject jsonResult = null;
        try {
            jsonResult = new JSONObject(item);
            result.setStatusCode(jsonResult.getInt("StatusCode"));
            result.setMessage(jsonResult.getString("Message"));

            if (result.getStatusCode() == 200) {
                result.setToken(jsonResult.getString("Token"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  result;
    }

    private void SaveTokenSession(TokenViewModel token) {
        SharedPreferences sharedpreferences;
        sharedpreferences = getActivity().getSharedPreferences(tokenSession, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(tokenKey, token.getToken());
        editor.commit();
    }

    private void NavigateToMainActivity() {
        Intent main = new Intent(getActivity(), MainActivity.class);
        startActivity(main);
        getActivity().finish();
    }




}
