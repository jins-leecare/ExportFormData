package com.leecare.extract.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.leecare.extract.model.InputParameters;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class RestController {

    private Client client;

    public RestController() {
        SSLContext sslContext = createInsecureSSLContext();
        this.client = ClientBuilder.newBuilder().sslContext(sslContext).build();
        client.register(JacksonJsonProvider.class);

    }

    public Response postAndRetrieveData(InputParameters parameters, String url, String body) {
        WebTarget target = client.target(url);
        Invocation.Builder requestBuilder = target.request(MediaType.APPLICATION_JSON);
        requestBuilder.header("Content-Type", "application/json");
        requestBuilder.header("Authorization", getBase64Authorization(parameters.getConfigProperties().getUserName(), parameters.getConfigProperties().getPassWord()));
        Response response = requestBuilder.post(Entity.entity(body, MediaType.APPLICATION_JSON));
        return response;
    }

    public Response retrieveData(InputParameters parameters, String url) {
        WebTarget target = client.target(url);
        Invocation.Builder requestBuilder = target.request(MediaType.APPLICATION_JSON);
        requestBuilder.header("Content-Type", "application/json");
        requestBuilder.header("Authorization", getBase64Authorization(parameters.getConfigProperties().getUserName(), parameters.getConfigProperties().getPassWord()));
        Response response = requestBuilder.get();
        return response;
    }

    private SSLContext createInsecureSSLContext() {
        try {
            TrustManager[] trustAllCertificates = new TrustManager[]{
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

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

            return sslContext;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SSL context", e);
        }
    }

    private String getBase64Authorization(String userName, String passWord) {
        String credentials = userName + ":" + passWord;
        byte[] credentialsBytes = credentials.getBytes();
        String encodedCredentials = Base64.getEncoder().encodeToString(credentialsBytes);
        return "Basic " + encodedCredentials;
    }
}
