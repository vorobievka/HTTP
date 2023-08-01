package my.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();


        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");

        String result = null;
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = new BufferedReader(new InputStreamReader(instream))
                        .lines().collect(Collectors.joining("\n"));
                ((InputStream) instream).close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Chat> listFromJSON = jsonToList(result);
        List<Chat> upvotesMoreThanZero = new ArrayList<Chat>();
        upvotesMoreThanZero =
                listFromJSON
                        .stream()
                        .filter(value -> value.getUpvotes() > 0)
                        .collect(Collectors.toList());

        upvotesMoreThanZero.forEach((n) -> {
            System.out.println(n);
        });
    }

    private static List<Chat> jsonToList(String jsonNew) {
        List<Chat> chats = new ArrayList<Chat>();
        JSONParser parser = new JSONParser();
        JSONArray message = null;
        try {
            Object obj = parser.parse(jsonNew);
            message = (JSONArray) obj;
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            for (int i = 0; i < message.size(); i++) {
                message.get(i);
                Chat chat = gson.fromJson(String.valueOf(message.get(i)), Chat.class);
                chats.add(chat);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
        return chats;
    }

}