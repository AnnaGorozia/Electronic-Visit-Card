package com.evc.tasks.companytasks;

import com.evc.MainActivity;
import com.evc.models.Company;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ServerGetCompaniesByUserIdTask extends UserServiceCompaniesTask {

    private static Gson gson = new GsonBuilder().create();
    private String url = MainActivity.url + "UserService";

    private List<Company> companies;

    @Override
    protected void onPostExecute(List<Company> companies) {
        super.onPostExecute(companies);
        networkEventListener.onUserCompanies(companies);
    }

    @Override
    protected List<Company> doInBackground(String... params) {

        url += "/users/companies/" + params[0];

        String body = "";

        companies = sendGetRequest(url, body);

        return companies;
    }

    private  List<Company> sendGetRequest(String url, String body) {
        HttpURLConnection httpURLConnection;
        String data = body;
        String result = null;
        try{
            httpURLConnection = (HttpURLConnection) ((new URL(url).openConnection()));
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();
            httpURLConnection.disconnect();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Company> companies = null;
        if (result.length() != 0) {
            Type listType = new TypeToken<List<Company>>(){}.getType();
            companies = gson.fromJson(result, listType);
        }
        return companies;
    }
}

