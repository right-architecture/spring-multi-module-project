package commerce.operation.api.controller;

import commerce.api.security.JwtProvider;
import commerce.identity.OperatorJpaRepository;
import commerce.operation.api.query.IssueToken;
import commerce.operation.api.view.AccessTokenCarrier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issue-token")
public class IssueTokenController {

    private final OperatorJpaRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public IssueTokenController(
        OperatorJpaRepository repository,
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
            .findByUsername(query.username())
            .filter(operator -> passwordEncoder.matches(
                query.password(),
                operator.getPasswordHash()))
            .map(operator -> operator.getId().toString())
            .map(jwtProvider::composeToken)
            .map(token -> ResponseEntity.ok(new AccessTokenCarrier(token)))
            .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
