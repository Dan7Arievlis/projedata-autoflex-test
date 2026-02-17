package io.github.dan7arievlis.autoflextest.security;

import io.github.dan7arievlis.autoflextest.model.User;
import io.github.dan7arievlis.autoflextest.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SocialLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Value("${spring.frontend-url}")
    private String frontendUrl;

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final CustomAuthenticationProvider authProvider;

    private static final String DEFAULT_PASSWORD = "password";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = token.getPrincipal();
        String email = oauth2User.getAttribute("email");

        Optional<User> optUser = userService.findByEmail(email);
        User user = optUser.orElseGet(() -> registerUser(oauth2User));

        CustomAuthentication customAuth = new CustomAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(customAuth);

        String jwt = authProvider.generateToken(customAuth);

        response.sendRedirect(frontendUrl + "/auth/callback?token=" + jwt);
//        super.onAuthenticationSuccess(request, response, authentication);
    }

    private User registerUser(OAuth2User oauth) {
        User user;
        user = new User();
        user.setEmail(oauth.getAttribute("email"));
        user.setLogin(oauth.getAttribute("name"));
        user.setPassword(encoder.encode(DEFAULT_PASSWORD));
        user.setRoles(List.of("USER"));
        userService.create(user);
        return user;
    }
}
