package NimbusAPI.controller;

import NimbusAPI.DTO.CityBookMarkDto;
import NimbusAPI.response.ApiResponse;
import NimbusAPI.service.CityBookmark.ICityService;
import NimbusAPI.utils.Records.WeatherRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookMarkController {
    private final ICityService cityService;

    @GetMapping
    ResponseEntity<ApiResponse> getAllCities() {

        List<CityBookMarkDto> cityBookMarkDtos = cityService.getAllCityBookmark();

        ApiResponse apiResponse = ApiResponse.builder()
                .success(true)
                .message("bookmarks")
                .data(cityBookMarkDtos)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    ResponseEntity<ApiResponse> addCityToBookmark(@RequestBody CityBookMarkDto cityBookMarkDto) {

        CityBookMarkDto cityBookMarkDto1 = cityService.addCityToBookMark(cityBookMarkDto);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .success(true)
                .data(cityBookMarkDto1)
                .message("City added")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/report")
    ResponseEntity<ApiResponse> getWeatherRecord(@RequestParam String cityName) {

        ApiResponse apiResponse = ApiResponse.builder()
                .success(true)
                .message("weather Report")
                .data(cityService.getWeatherByCityName(cityName))
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse> deleteBookMark(@PathVariable Long id) {

        cityService.deleteBookMark(id);

        ApiResponse apiResponse = ApiResponse.builder()
                .success(true)
                .message("bookmark deleted")
                .build();

        return ResponseEntity.ok(apiResponse);
    }


}
