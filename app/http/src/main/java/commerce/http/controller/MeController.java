package commerce.http.controller;

import commerce.identity.UserEntity;
import commerce.identity.UserJpaRepository;
import commerce.identity.view.UserView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/me")
public class MeController {

    private final UserJpaRepository repository;

    public MeController(UserJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<UserView> get(Principal principal) {
        UserView user = repository
            .findById(UUID.fromString(principal.getName()))
            .map(UserEntity::toView)
            .orElseThrow(RuntimeException::new);
        return ResponseEntity.ok(user);
    }
}
