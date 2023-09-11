package com.leecare.extract.service;

import com.leecare.extract.model.InputParameters;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class RestController {

    private static SSLContext sslContext;
    private static TrustManager[] trustAllCertificates;
    private static OkHttpClient client;

    RestController() {

        try {
            trustAllCertificates = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };
            // Create an SSL context with the custom TrustManager
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
            client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCertificates[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .connectTimeout(20, TimeUnit.MINUTES) // Connection timeout
                    .readTimeout(20, TimeUnit.MINUTES)    // Read timeout
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResponseBody postAndRetrieveData(InputParameters parameters, String url, String body) {

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, body);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", getBase64Authorization(parameters.getConfigProperties().getUserName(), parameters.getConfigProperties().getPassWord()))
                .build();

        return invokeRestService(request);
    }

    public ResponseBody retrieveData(InputParameters parameters, String url) {

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", getBase64Authorization(parameters.getConfigProperties().getUserName(), parameters.getConfigProperties().getPassWord()))
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
