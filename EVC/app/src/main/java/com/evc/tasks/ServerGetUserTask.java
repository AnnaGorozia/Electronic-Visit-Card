package com.evc.tasks;

import com.evc.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerGetUserTask extends UserServiceObjectTask {

    private static Gson gson = new GsonBuilder().create();
    private String url = "http://192.168.0.100:8082/UserService";

    private User user;

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        networkEventListener.onUserObjectReturned(user);
    }

    @Override
    protected User doInBackground(String... params) {

        url += "/users/id/" + params[0];

        String body = "";

        user = sendGetRequest(url, body);

        return user;
    }

    private User sendGetRequest(String url, String body) {
        HttpURLConnection httpURLConnection;
        String data = body;
        String result = null;
        try{
            //Connect
            httpURLConnection = (HttpURLConnection) ((new URL(url).openConnection()));
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

//            //Write
//            OutputStream os = httpURLConnection.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//            writer.write(data);
//            writer.close();
//            os.close();

            //Read
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = gson.fromJson(result, User.class);
        return user;
    }
}
