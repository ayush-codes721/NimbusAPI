package NimbusAPI.DTO;

import NimbusAPI.model.CityBookmark;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;


    private String name;
    private String username;
    private String password;

    private Set<CityBookMarkDto> cityBookmarks;

}
