package com.kavsoftware.kaveer.gandh.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kavsoftware.kaveer.gandh.Model.TokenViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kaveer on 1/8/2018.
 */

public class ValidToken extends AsyncTask<String, String, String> {

    ProgressDialog progressDialog;
    HttpURLConnection connection = null;
    BufferedReader reader = null;

    Activity activity;

    public ValidToken(Activity activity) {
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
        TokenViewModel token;
        Gson gson;

        try {
            URL url = new URL(params[0]);
            String authToken = params[1];

            connection = (HttpURLConnection) url.openConnection();
           // connection.setDoInput(true);
           // connection.setDoOutput(true);
            connection.setRequestMethod("GET");
           // connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", authToken);
            connection.connect();


//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("userid", tokenz);

//            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//           // wr.writeBytes(jsonObject.toString());
//            wr.flush();
//            wr.close();

            int statusCode = connection.getResponseCode();

            if (statusCode == 200) {
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String jsonObjectHome = buffer.toString();
                result = jsonObjectHome;
            }

            if (statusCode == 400){
                token = new TokenViewModel();
                token.setStatusCode(statusCode);
                token.setMessage("fail please login again");

                gson = new Gson();
                result = gson.toJson(token);
            }

            if (statusCode == 404){
                token = new TokenViewModel();
                token.setStatusCode(statusCode);
                token.setMessage("fail please login again");

                gson = new Gson();
                result = gson.toJson(token);
            }

            if (statusCode == 500){
                token = new TokenViewModel();
                token.setStatusCode(statusCode);
                token.setMessage("fail please login again");

                gson = new Gson();
                result = gson.toJson(token);
            }

        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
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
