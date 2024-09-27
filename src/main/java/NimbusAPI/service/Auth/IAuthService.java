package NimbusAPI.service.Auth;

import NimbusAPI.request.LoginRequest;
import NimbusAPI.request.SignupRequest;
import NimbusAPI.response.LoginResponse;
import NimbusAPI.response.SignupResponse;
import jakarta.servlet.http.Cookie;

public interface IAuthService {

    SignupResponse signup(SignupRequest signupRequest);
    LoginResponse login(LoginRequest loginRequest);
    Cookie createCookie(String refreshToken);
    LoginResponse oauthLogin(String accessToken);
}
