package NimbusAPI.service.Weather;

import NimbusAPI.utils.Records.WeatherRecord;

public interface IWeatherService {

     WeatherRecord getWeatherDataByCityName(String cityName);

}
