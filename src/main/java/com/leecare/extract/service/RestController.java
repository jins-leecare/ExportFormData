package com.leecare.extract.service;

import com.leecare.extract.model.ConfigProperties;
import okhttp3.*;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class RestController {
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.MINUTES) // Connection timeout
            .readTimeout(20, TimeUnit.MINUTES)    // Read timeout
            .build();

    public ResponseBody postAndRetrieveData(String url, String jsonBody) {

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, jsonBody);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", getBase64Authorization(ConfigProperties.getUserName(), ConfigProperties.getPassWord()))
                .build();

        return invokeRestService(request);
    }

    public ResponseBody retrieveData(String url) {

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", getBase64Authorization(ConfigProperties.getUserName(), ConfigProperties.getPassWord()))
                .build();

        return invokeRestService(request);
    }

    private static ResponseBody invokeRestService(Request request) {
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
