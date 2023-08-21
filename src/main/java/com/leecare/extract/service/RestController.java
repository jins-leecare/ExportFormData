package com.leecare.extract.service;

import com.leecare.extract.model.ConfigProperties;
import okhttp3.*;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class RestController {

    public ResponseBody postAndRetrieveData(String url, String jsonBody) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, jsonBody);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", getBase64Authorization(ConfigProperties.getUserName(), ConfigProperties.getPassWord()))
                .build();

        return invokeRestService(client, request);
    }

    public ResponseBody retrieveData(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
                .readTimeout(30, TimeUnit.SECONDS)    // Read timeout
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", getBase64Authorization(ConfigProperties.getUserName(), ConfigProperties.getPassWord()))
                .build();

        return invokeRestService(client, request);
    }

    private static ResponseBody invokeRestService(OkHttpClient client, Request request) {
        ResponseBody responseBody = null;
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                responseBody = response.body();
            } else {
                System.out.println("Request failed: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    private static String getBase64Authorization(String userName, String passWord) {
        String credentials = userName + ":" + passWord;
        byte[] credentialsBytes = credentials.getBytes();
        String encodedCredentials = Base64.getEncoder().encodeToString(credentialsBytes);
        String authorizationHeader = "Basic " + encodedCredentials;
        return authorizationHeader;
    }
}
