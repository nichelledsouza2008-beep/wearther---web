package com.weartherweb.demo.controller;

import com.weartherweb.demo.service.GroqService;
import com.weartherweb.demo.service.WeatherService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Outfitcontroller
{
    private final WeatherService weatherService;
    private final GroqService groqService;

    public Outfitcontroller(WeatherService weatherService, GroqService groqService)
    {
        this.weatherService = weatherService;
        this.groqService = groqService;
    }

    @GetMapping("/outfit")
    public OutfitResponse getOutfit(@RequestParam String city) throws Exception
    {
        String weatherInfo = weatherService.getWeather(city);
        String suggestion = groqService.getSuggestion(weatherInfo);
        return new OutfitResponse(city, suggestion);
    }
     record OutfitResponse(String city, String suggestion) {}
}

