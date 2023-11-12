package commerce.http;

import commerce.identity.UserEntity;
import commerce.identity.UserJpaRepository;
import commerce.identity.UserRepositoryAdapter;
import commerce.identity.commandmodel.UserRepository;
import commerce.identity.querymodel.UserReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserJpaRepository.class)
@EntityScan(basePackageClasses = UserEntity.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public UserRepository userRepository(UserJpaRepository repository) {
        return new UserRepositoryAdapter(repository);
    }

    @Bean
    public UserReader userReader(UserJpaRepository repository) {
        return new UserRepositoryAdapter(repository);
    }
}
