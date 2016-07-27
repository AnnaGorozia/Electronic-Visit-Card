package com.evc.tasks.historytasks;

import com.evc.MainActivity;
import com.evc.models.History;
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

public class ServerAddHistoryTask extends HistoryServiceTask{
    private static Gson gson = new GsonBuilder().create();
    private String url = MainActivity.url + "HistoryService";

    private String message;

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        networkEventListener.onHistoryAdded(message);
    }

    @Override
    protected String doInBackground(String... params) {

        url += "/addHistory";

        History history = new History();
        history.setSenderId(params[0]);
        history.setReceiverId(params[1]);
        history.setCardId(params[2]);

        String body = gson.toJson(history);

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
