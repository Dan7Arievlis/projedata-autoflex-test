package io.github.dan7arievlis.autoflextest.service;

import io.github.dan7arievlis.autoflextest.controller.dto.user.ChangePasswordRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.user.UserLoginRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.user.UserLoginResponseDTO;
import io.github.dan7arievlis.autoflextest.exceptions.InvalidFieldException;
import io.github.dan7arievlis.autoflextest.model.User;
import io.github.dan7arievlis.autoflextest.repository.UserRepository;
import io.github.dan7arievlis.autoflextest.security.CustomAuthentication;
import io.github.dan7arievlis.autoflextest.security.SecurityService;
import io.github.dan7arievlis.autoflextest.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserValidator validator;
    private final SecurityService securityService;

    @Transactional
    public void save(User user) {
        validator.validate(user);
        user.setLastModifiedBy(securityService.getLoggedUser());
        repository.save(user);
    }

    @Transactional
    public void create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        save(user);
    }

    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void delete(User user) {
        user.delete();
        save(user);
    }

    @Transactional
    public void restore(User user) {
        user.restore();
        save(user);
    }

    @Transactional
    public void changePassword(User user, ChangePasswordRequestDTO request) {
        if (!encoder.matches(request.currentPassword(), user.getPassword()))
            throw new InvalidFieldException("currentPassword", "Current password don't match");

        if (encoder.matches(request.newPassword(), user.getPassword()))
            throw new InvalidFieldException("newPassword", "New password is equal to current password");

        matchConfirmPassword(request.newPassword(), request.confirmPassword());

        user.setPassword(encoder.encode(request.newPassword()));
        save(user);
    }

    public void matchConfirmPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword))
            throw new InvalidFieldException("confirmPassword", "Confirm password don't match");
    }
}
