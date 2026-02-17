package io.github.dan7arievlis.autoflextest.security;

import io.github.dan7arievlis.autoflextest.model.User;
import io.github.dan7arievlis.autoflextest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtEncoder jwtEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String entryPassword = authentication.getCredentials().toString();

        User userFound = userService.findByLogin(login).orElseThrow(CustomAuthenticationProvider::getUsernameNotFoundException);

//        String hashedPassword = encoder.encode(entryPassword);

        boolean matches = encoder.matches(entryPassword, userFound.getPassword());

        if (matches) {
            return new CustomAuthentication(userFound);
        }

        throw getUsernameNotFoundException();
    }

    private static UsernameNotFoundException getUsernameNotFoundException() {
        return new UsernameNotFoundException("User and/or password incorrect");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

    public String generateToken(CustomAuthentication auth) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("autoflex")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(auth.getName())
                .claim("authorities",
                        auth.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .claim("email", auth.user().getEmail())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
