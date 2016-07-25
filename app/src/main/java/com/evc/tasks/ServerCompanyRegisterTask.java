package com.evc.tasks;

import com.evc.models.Company;
import com.evc.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerCompanyRegisterTask extends CompanyServiceTask {

    private static Gson gson = new GsonBuilder().create();
    private String url = "http://192.168.43.188:8082/CompanyService";

    private String message;

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        networkEventListener.onUserRegistered(message);
    }

    @Override
    protected String doInBackground(String... params) {

        url += "/addCompany";

        Company company = new Company();
        company.setName(params[0]);
        company.setAddress(params[1]);
        company.setEmail(params[2]);
        company.setPhone(params[3]);
        company.setPassword(params[4]);

        String body = gson.toJson(company, Company.class);

        message = sendPostRequest(url, body);

        return message;
    }

    private String sendPostRequest(String url, String body) {
        HttpURLConnection httpURLConnection;
        String data = body;
        String result = null;
        try{
            //Connect
            httpURLConnection = (HttpURLConnection) ((new URL(url).openConnection()));
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();

            //Write
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.close();
            os.close();

            //Read
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

        return result;
    }
}
