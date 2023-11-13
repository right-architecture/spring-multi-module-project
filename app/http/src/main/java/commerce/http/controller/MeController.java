package commerce.http.controller;

import commerce.identity.querymodel.UserReader;
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

    private final UserReader reader;

    public MeController(UserReader userReader) {
        reader = userReader;
    }

    @GetMapping
    public ResponseEntity<UserView> get(Principal principal) {
        UserView user = reader
            .findById(UUID.fromString(principal.getName()))
            .orElseThrow(RuntimeException::new);
        return ResponseEntity.ok(user);
    }
}
