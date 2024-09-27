package NimbusAPI.service.Auth;

import NimbusAPI.model.User;
import NimbusAPI.request.LoginRequest;
import NimbusAPI.request.SignupRequest;
import NimbusAPI.response.LoginResponse;
import NimbusAPI.response.SignupResponse;
import NimbusAPI.service.JWT.JwtService;
import NimbusAPI.service.User.IUserService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final IUserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignupResponse signup(SignupRequest signupRequest) {

        boolean isPresent = userService.OptionalgetUserByUsername(signupRequest.getUsername()).isPresent();
        if (isPresent) {

            throw new BadCredentialsException("user already exist");
        }
        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        User savedUser = userService.createUser(user);
        return modelMapper.map(savedUser, SignupResponse.class);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        User user = (User) authentication.getPrincipal();


        String accessToken = jwtService.createAccessToken(user);
        String refreshToken = jwtService.createRefreshToken(user);


        return LoginResponse.builder()
                .accessToken(accessToken)
                .userId(user.getId())
                .refreshToken(refreshToken)
                .build();
    }


    @Override
    public Cookie createCookie(String refreshToken) {
        Cookie cookie = new Cookie("token", refreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        // Set the cookie to expire in 6 months
        int sixMonthsInSeconds = 6 * 30 * 24 * 60 * 60; // Approximation: 6 months * 30 days * 24 hours * 60 minutes * 60 seconds
        cookie.setMaxAge(sixMonthsInSeconds);

        return cookie;
    }

    @Override
    public LoginResponse oauthLogin(String accessToken) {

        Long id = jwtService.getIdFromToken(accessToken);
        User user = userService.getUserById(id);
        String refreshToken = jwtService.createRefreshToken(user);
        return LoginResponse.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


}
