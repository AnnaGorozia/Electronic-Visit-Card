package com.evc.tasks;
import com.evc.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServerUserRegisterTask extends UserRegisterTask {
    private static Gson gson = new GsonBuilder().create();
    private String url = "http://192.168.76.236:8082/UserService";

    private String message;
    @Override
    protected String doInBackground(String... params){
        Client client = ClientBuilder.newClient();
        url += "/registrateUser";
        User user = new User();
        user.setFirstName(params[0]);
        user.setLastName(params[1]);
        user.setEmail(params[2]);
        user.setPhone(params[3]);
        user.setPassword(params[4]);

        String body = gson.toJson(user);

        Response simulatorResponse = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(body, MediaType.APPLICATION_JSON));


        this.message = simulatorResponse.toString();

        return message;
    }
}
