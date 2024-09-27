package NimbusAPI.controller;

import NimbusAPI.request.LoginRequest;
import NimbusAPI.request.SignupRequest;
import NimbusAPI.response.ApiResponse;
import NimbusAPI.response.LoginResponse;
import NimbusAPI.response.SignupResponse;
import NimbusAPI.service.Auth.IAuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody SignupRequest signupRequest) {

        SignupResponse signupResponse = authService.signup(signupRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .success(true)
                .message("signup success")
                .data(signupResponse)
                .build();

        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {


        LoginResponse loginResponse = authService.login(loginRequest);
        authService.createCookie(loginResponse.getRefreshToken());
        ApiResponse apiResponse = ApiResponse.builder()
                .success(true)
                .message("login success")
                .data(loginResponse)
                .build();

        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/oauth-success")
    ResponseEntity<ApiResponse> oAuthLogin(@RequestParam String accessToken, HttpServletResponse response) {

        LoginResponse loginResponse = authService.oauthLogin(accessToken);
        Cookie cookie = authService.createCookie(loginResponse.getRefreshToken());
        response.addCookie(cookie);

        ApiResponse apiResponse=ApiResponse.builder()
                .success(true)
                .message("login success")
                .data(loginResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }


}
