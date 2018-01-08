package com.kavsoftware.kaveer.gandh.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kaveer on 1/6/2018.
 */

public class Login extends AsyncTask<String, String, String> {

    ProgressDialog progressDialog;
    HttpURLConnection connection = null;
    BufferedReader reader = null;

    Activity activity;

    public Login(Activity activity) {
        this.activity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // display a progress dialog for good user experiance
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;
        try {
            URL url = new URL(params[0]);
            String email = params[1];
            String password = params[2];
            String loginType = params[3];

            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("logintype", loginType);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();

            int statusCode = connection.getResponseCode();

            if(statusCode == 200){
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                String  jsonObjectHome = buffer.toString();
                result = jsonObjectHome;
            }else {
                return result;
            }

        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.cancel();
    }

}
