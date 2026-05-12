package com.weartherweb.demo.service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.github.cdimascio.dotenv.Dotenv;

@org.springframework.stereotype.Service
public class WeatherService
{
    private String apikey;
    public WeatherService()
    {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.apikey = dotenv.get("OPENWEATHER_API_KEY");
    }
    public String getWeather(String city) throws Exception
    {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apikey + "&units=metric";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        String condition = json.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        double temperature = json.getAsJsonObject("main").get("temp").getAsDouble();
        int humidity = json.getAsJsonObject("main").get("humidity").getAsInt();

        return "\nCity:" + city + "\nCondition:" + condition + "\nTemperature:" + temperature + "°C\nHumidity:" + humidity + "%";
    }
}

