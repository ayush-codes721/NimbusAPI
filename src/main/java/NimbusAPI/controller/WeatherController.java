package NimbusAPI.controller;

import NimbusAPI.service.Weather.IWeatherService;
import NimbusAPI.utils.Records.WeatherRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {


    @Autowired
    private IWeatherService weatherService;

    @GetMapping("/weather")
    WeatherRecord data(@RequestParam String city) {

        return weatherService.getWeatherDataByCityName(city);
    }

}
