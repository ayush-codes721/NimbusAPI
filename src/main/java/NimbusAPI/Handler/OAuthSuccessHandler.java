package NimbusAPI.Handler;

import NimbusAPI.model.User;
import NimbusAPI.service.JWT.JwtService;
import NimbusAPI.service.User.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final IUserService userService;
    private final HandlerExceptionResolver exceptionResolver;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) auth2AuthenticationToken.getPrincipal();

        // Log email for debugging
        String email = oAuth2User.getAttribute("email");
        User user = userService.OptionalgetUserByUsername(email).orElse(null);
        if (user == null) {
            User newUser = new User();
            newUser.setName(oAuth2User.getName());
            newUser.setUsername(email);
            user = userService.createUser(newUser);
        }

        // Create JWT token
        String accessToken = jwtService.createAccessToken(user);

        // Redirect URL with the generated token
        String redirectUrl = String.format("http://localhost:5173/", accessToken);

        // Redirect to the new URL
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

}

