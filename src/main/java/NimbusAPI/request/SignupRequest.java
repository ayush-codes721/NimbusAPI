package NimbusAPI.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignupRequest {



    private String name;
    private String username;
    private String password;
}

