package commerce.identity.jpa;

import commerce.identity.view.UserView;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    private UUID id;
    private String email;
    private String passwordHash;

    public UserView toView() {
        return new UserView(id, email);
    }
}
