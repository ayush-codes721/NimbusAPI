package NimbusAPI.service.CityBookmark;

import NimbusAPI.DTO.CityBookMarkDto;
import NimbusAPI.exception.ResourceAlreadyExistsException;
import NimbusAPI.exception.ResourceNotFoundException;
import NimbusAPI.model.CityBookmark;
import NimbusAPI.model.User;
import NimbusAPI.repo.CityRepo;
import NimbusAPI.service.Weather.IWeatherService;
import NimbusAPI.utils.Records.WeatherRecord;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService implements ICityService {

    private final CityRepo cityRepo;
    private final ModelMapper modelMapper;
    private final IWeatherService weatherService;

    @Override
    public List<CityBookMarkDto> getAllCityBookmark() {

        User user = getUser();


        List<CityBookMarkDto> bookMarkDtos = cityRepo.findByUser(user)
                .stream()
                .map(cityBookmark -> modelMapper.map(cityBookmark, CityBookMarkDto.class))
                .toList();
        return bookMarkDtos;
    }

    @Transactional
    @Override
    public CityBookMarkDto addCityToBookMark(CityBookMarkDto cityBookMarkDto) {
        User user = getUser();
        List<CityBookmark> existingBookmarks = cityRepo.findByUser(user);

        if (!existingBookmarks.isEmpty()){
            boolean alreadyBookmarked = existingBookmarks.stream()
                    .anyMatch(bookmark -> bookmark.getCityName().equalsIgnoreCase(cityBookMarkDto.getCityName()));


            if (alreadyBookmarked) {
                throw new ResourceAlreadyExistsException("City already bookmarked");
            }
        }
        CityBookmark cityBookmark = modelMapper.map(cityBookMarkDto, CityBookmark.class);
        cityBookmark.setUser(user);
        CityBookmark savedCity = cityRepo.save(cityBookmark);


        return modelMapper.map(savedCity, CityBookMarkDto.class);
    }

    @Override
    public WeatherRecord getWeatherByCityName(String citName) {
        return weatherService.getWeatherDataByCityName(citName);
    }

    @Override
    public void deleteBookMark(Long id) {

        User user = getUser();

        cityRepo.findById(id).ifPresentOrElse((cityBookmark) -> {
            if (!cityBookmark.getUser().getId().equals(user.getId())) {
                throw new UsernameNotFoundException("unauthorized access");
            }
            cityRepo.delete(cityBookmark);

        }, () -> {
            throw new ResourceNotFoundException("city not found");
        });

    }

    private User getUser() {

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
