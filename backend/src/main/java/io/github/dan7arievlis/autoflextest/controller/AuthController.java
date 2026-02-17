package io.github.dan7arievlis.autoflextest.controller;

import io.github.dan7arievlis.autoflextest.controller.dto.user.UserLoginRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.user.UserLoginResponseDTO;
import io.github.dan7arievlis.autoflextest.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestDTO dto) {
        UserLoginResponseDTO response = service.login(dto);

        return ResponseEntity.ok(response);
    }


}
