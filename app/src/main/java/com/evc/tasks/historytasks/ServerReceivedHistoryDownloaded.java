package com.evc.tasks.historytasks;

import com.evc.MainActivity;
import com.evc.models.HistoryEntry;
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

public class ServerReceivedHistoryDownloaded extends HistoryListService{
    private static Gson gson = new GsonBuilder().create();
    private String url = MainActivity.url + "HistoryService";

    private List<HistoryEntry> historyEntries;

    @Override
    protected void onPostExecute(List<HistoryEntry> historyEntries) {
        super.onPostExecute(historyEntries);
        networkEventListener.onReceivedHistoryDownloaded(historyEntries);
    }

    @Override
    protected List<HistoryEntry> doInBackground(String... params) {

        url += "/histories/received/user/" + params[0];

        String body = "";

        historyEntries = sendGetRequest(url, body);

        return historyEntries;
    }

    private  List<HistoryEntry> sendGetRequest(String url, String body) {
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
        List<HistoryEntry> historyEntries = null;
        if (result.length() != 0) {
            Type listType = new TypeToken<List<HistoryEntry>>(){}.getType();
            historyEntries = gson.fromJson(result, listType);
        }
        return historyEntries;
    }
}
