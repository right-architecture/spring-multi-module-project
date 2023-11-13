package commerce.identity.querymodel;

import commerce.identity.view.UserView;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public interface UserReader {

    Optional<UserView> findById(UUID id);

    Optional<UserView> findByCredentials(
        String email,
        Function<String, Boolean> passwordVerifier);
}
