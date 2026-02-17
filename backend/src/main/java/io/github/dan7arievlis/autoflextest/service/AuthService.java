package io.github.dan7arievlis.autoflextest.service;

import io.github.dan7arievlis.autoflextest.controller.dto.user.UserLoginRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.user.UserLoginResponseDTO;
import io.github.dan7arievlis.autoflextest.security.CustomAuthentication;
import io.github.dan7arievlis.autoflextest.security.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomAuthenticationProvider authProvider;

    public UserLoginResponseDTO login(UserLoginRequestDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.login(), dto.password())
        );

        CustomAuthentication customAuth = (CustomAuthentication) authentication;
        String token = authProvider.generateToken(customAuth);

        List<String> roles = customAuth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new UserLoginResponseDTO(token, roles, customAuth.getName(), customAuth.user().getEmail());
    }
}
