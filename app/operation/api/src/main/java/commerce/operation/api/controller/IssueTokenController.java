package commerce.operation.api.controller;

import commerce.identity.OperatorJpaRepository;
import commerce.operation.api.query.IssueToken;
import commerce.operation.api.view.AccessTokenCarrier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issue-token")
public class IssueTokenController {

    private final OperatorJpaRepository repository;

    public IssueTokenController(OperatorJpaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<AccessTokenCarrier> issueToken(
        @RequestBody IssueToken query
    ) {
        return repository
            .findByUsername(query.username())
            .filter(x -> x.getPasswordHash().equals(query.password()))
            .map(x -> ResponseEntity.ok(new AccessTokenCarrier("token")))
            .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
