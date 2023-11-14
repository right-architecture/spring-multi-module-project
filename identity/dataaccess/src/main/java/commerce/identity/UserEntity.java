package commerce.identity;

import commerce.identity.view.UserView;
import jakarta.persistence.Column;
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

    @Column(length = 1000)
    private String passwordHash;

    public UserView toView() {
        return new UserView(id, email);
    }
}
