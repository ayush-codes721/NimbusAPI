package NimbusAPI.service.Weather;

import NimbusAPI.utils.Records.MainData;
import NimbusAPI.utils.Records.WeatherRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class WeatherService implements IWeatherService {
    private final RestClient restClient;

    @Value("${weather.key}")
    private String key;
    @Override
    public WeatherRecord getWeatherDataByCityName(String cityName) {

        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", cityName, key);

      return restClient.get()
                .uri(url)
                .retrieve()
                .body(WeatherRecord.class);
    }

}
