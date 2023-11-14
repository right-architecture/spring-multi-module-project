package commerce.http;

import commerce.identity.UserEntity;
import commerce.identity.UserJpaRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.util.function.Supplier;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserJpaRepository.class)
@EntityScan(basePackageClasses = UserEntity.class)
@EnableWebSecurity
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Supplier<JwtBuilder> jwtBuilderFactory() {
        var key = new SecretKeySpec(
            "c599da5d-ed2f-41b8-a79c-e1c77e5850e1".getBytes(),
            SignatureAlgorithm.HS256.getJcaName());
        return () -> Jwts.builder().signWith(key);
    }

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.decoder(NimbusJwtDecoder.withSecretKey(
                    new SecretKeySpec(
                        "c599da5d-ed2f-41b8-a79c-e1c77e5850e1".getBytes(),
                        SignatureAlgorithm.HS256.getJcaName()
                    )
                ).build())))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/signup").permitAll()
                .requestMatchers("/api/issue-token").permitAll()
                .anyRequest().authenticated());
        return http.build();
    }
}
