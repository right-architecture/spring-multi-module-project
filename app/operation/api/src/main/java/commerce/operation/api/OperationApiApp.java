package commerce.operation.api;

import commerce.identity.OperatorEntity;
import commerce.identity.OperatorJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = OperatorJpaRepository.class)
@EntityScan(basePackageClasses = OperatorEntity.class)
public class OperationApiApp {

    public static void main(String[] args) {
        SpringApplication.run(OperationApiApp.class, args);
    }
}
