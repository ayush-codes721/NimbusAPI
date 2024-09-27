package NimbusAPI.service.CityBookmark;

import NimbusAPI.DTO.CityBookMarkDto;
import NimbusAPI.model.CityBookmark;
import NimbusAPI.utils.Records.WeatherRecord;

import java.util.List;

public interface ICityService {


    List<CityBookMarkDto> getAllCityBookmark();

    CityBookMarkDto addCityToBookMark(CityBookMarkDto cityBookMarkDto);

    WeatherRecord getWeatherByCityName(String citName);

    void deleteBookMark(Long id);
}
