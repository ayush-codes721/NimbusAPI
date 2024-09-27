package NimbusAPI.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CityBookMarkDto {

    private Long id;
    private String cityName;
    LocalDateTime createdAt;


}
