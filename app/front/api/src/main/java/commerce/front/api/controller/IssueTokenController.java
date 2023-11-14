package commerce.front.api.controller;

import commerce.api.security.JwtProvider;
import commerce.front.api.query.IssueToken;
import commerce.front.api.view.AccessTokenCarrier;
import commerce.identity.UserJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issue-token")
public class IssueTokenController {

    private final UserJpaRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public IssueTokenController(
        UserJpaRepository repository,
        PasswordEncoder passwordEncoder,
        JwtProvider jwtProvider
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping
    public ResponseEntity<AccessTokenCarrier> issueToken(
        @RequestBody IssueToken query
    ) {
        return repository
            .findByEmail(query.email())
            .filter(user -> passwordEncoder.matches(
                query.password(),
                user.getPasswordHash()))
            .map(user -> jwtProvider.composeToken(user.getId().toString()))
            .map(token -> ResponseEntity.ok(new AccessTokenCarrier(token)))
            .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
