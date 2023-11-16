package commerce.identity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class User {

    private final UUID id;
    private String email;
    private String passwordHash;
}
