package com.evc.tasks.cardtasks;

import com.evc.MainActivity;
import com.evc.models.Card;
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

public class ServerGetAllUserCardsTaskList extends CardListServiceTask {
    private static Gson gson = new GsonBuilder().create();
    private String url = MainActivity.url + "CardService";

    private List<Card> cards;

    @Override
    protected void onPostExecute(List<Card> cards) {
        super.onPostExecute(cards);
        networkEventListener.onUserCardsDownloaded(cards);
    }

    @Override
    protected List<Card> doInBackground(String... params) {

        url += "/cards/user/" + params[0];

        String body = "";

        cards = sendGetRequest(url, body);

        return cards;
    }

    private  List<Card> sendGetRequest(String url, String body) {
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
        List<Card> companies = null;
        if (result.length() != 0) {
            Type listType = new TypeToken<List<Card>>(){}.getType();
            companies = gson.fromJson(result, listType);
        }
        return companies;
    }
}
