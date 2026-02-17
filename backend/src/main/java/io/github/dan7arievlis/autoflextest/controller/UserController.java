package io.github.dan7arievlis.autoflextest.controller;

import io.github.dan7arievlis.autoflextest.controller.dto.user.ChangePasswordRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.user.UserCreateRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.mapper.UserMapper;
import io.github.dan7arievlis.autoflextest.controller.dto.user.UserResponseDTO;
import io.github.dan7arievlis.autoflextest.model.User;
import io.github.dan7arievlis.autoflextest.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService service;
    private final UserMapper mapper;

    // Create POST /
    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserCreateRequestDTO request) {
        service.matchConfirmPassword(request.password(), request.confirmPassword());
        var user = mapper.RequestToEntity(request);
        service.create(user);
        URI uri = generateHeaderLocation(user.getId());
        return ResponseEntity.created(uri).build();
    }

    // Details GET /id
    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable String id) {
        return service.findById(UUID.fromString(id))
                .map(user -> ResponseEntity.ok(mapper.entityToResponse(user)))
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    // Delete DELETE /id
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return service.findById(UUID.fromString(id))
                .map(user -> {
                    service.delete(user);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow( () -> new EntityNotFoundException("User not found."));
    }

    // Restore PATCH /id/restore
    @PatchMapping("{id}/restore")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> restore(@PathVariable String id) {
        return service.findById(UUID.fromString(id))
                .map(user -> {
                    service.restore(user);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow( () -> new EntityNotFoundException("User not found."));
    }

    // Change password PATCH /id/password
    @PatchMapping("{id}/password")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateFields(@PathVariable String id, @RequestBody @Valid ChangePasswordRequestDTO request) {
        return service.findById(UUID.fromString(id))
                .map(user -> {
                    service.changePassword(user, request);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow( () -> new EntityNotFoundException("User not found."));
    }
}
