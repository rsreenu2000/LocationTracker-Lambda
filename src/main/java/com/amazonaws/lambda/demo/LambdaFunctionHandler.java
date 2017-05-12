package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class LambdaFunctionHandler implements RequestHandler<RequestClass, Boolean> {

    @Override
    public Boolean handleRequest(RequestClass input, Context context) {
        String latitude = input.getlatitude();
        String longitude = input.getlongitude();
        String accuracy = input.getaccuracy();
        context.getLogger().log("Input: " + String.format("%s %s %s", latitude, longitude, accuracy));
        if (latitude.isEmpty() || longitude.isEmpty() || accuracy.isEmpty()) {
            context.getLogger().log("Invalid input: one or more arguments missing!");
            return true;
        }

        try {
            // Set up the request
            URL url = new URL("http://todofirebaseproject-94c98.appspot.com/places");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Build request parameters
            Map<String, String> nameValuePairs = new HashMap<>();
            nameValuePairs.put("latitude", latitude);
            nameValuePairs.put("longitude", longitude);
            nameValuePairs.put("accuracy", accuracy);
            String postParams = buildPostDataString(nameValuePairs);

            // Execute HTTP Post
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(postParams);
            writer.flush();
            writer.close();
            outputStream.close();
            connection.connect();

            // Parse response
            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                context.getLogger().log("POST response: " + response.toString());
            } else {
                context.getLogger().log("POST error: " + responseCode + " " + response.toString());
            }

        } catch (IOException e) {
            context.getLogger().log("POST exception: " + e.getMessage());
        }

        return true;
    }

    private String buildPostDataString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
