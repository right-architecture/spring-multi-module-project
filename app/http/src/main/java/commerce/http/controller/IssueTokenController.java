package commerce.http.controller;

import commerce.http.query.IssueToken;
import commerce.http.view.AccessTokenCarrier;
import commerce.identity.jpa.UserEntity;
import commerce.identity.jpa.UserJpaRepository;
import commerce.identity.view.UserView;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/issue-token")
public class IssueTokenController {

    private final UserJpaRepository repository;
    private final Supplier<JwtBuilder> jwtBuilderFactory;

    public IssueTokenController(
        UserJpaRepository repository,
        Supplier<JwtBuilder> jwtBuilderFactory
    ) {
        this.repository = repository;
        this.jwtBuilderFactory = jwtBuilderFactory;
    }

    @PostMapping
    public ResponseEntity<AccessTokenCarrier> issueToken(
        @RequestBody IssueToken query
    ) {
        Optional<UserView> queryResult = repository
            .findByEmail(query.email())
            .filter(user -> user.getPasswordHash().equals(query.password()))
            .map(UserEntity::toView);

        return queryResult
            .map(user -> {
                String token = jwtBuilderFactory
                    .get()
                    .setSubject(user.id().toString())
                    .compact();
                return ResponseEntity.ok(new AccessTokenCarrier(token));
            })
            .orElse(ResponseEntity.badRequest().build());
    }
}
