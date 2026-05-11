package com.weartherweb.demo.service;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@org.springframework.stereotype.Service
public class GroqService
{
    private String apikey;

    public GroqService()
    {
        Dotenv dotenv = Dotenv.load();
        this.apikey = dotenv.get("GROQ");
    }

    public String getSuggestion(String weatherInfo) throws Exception
    {
        String prompt = "Based on the following weather information, suggest a practical outfit for today. Keep it consice and friendly." + weatherInfo;
        prompt = prompt.replace("\n", " ");
        String url = "https://api.groq.com/openai/v1/chat/completions";
        String requestBody = "{\"model\": \"openai/gpt-oss-120b\", \"messages\":[{\"role\":\"user\", \"content\":\"" + prompt.replace("\"", "\\\"") + "\"}]}"; 

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
        .header("Content-Type", "application/json").header("Authorization", "Bearer " + apikey)
        .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

         HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

         JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
         String suggestion = json.getAsJsonArray("choices").get(0).getAsJsonObject().getAsJsonObject("message").get("content").getAsString();

         return suggestion;
    }

}