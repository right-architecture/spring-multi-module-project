package commerce.front.api;

import commerce.api.security.JwtProvider;
import commerce.api.security.PasswordHasher;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

import java.util.function.Supplier;

@Configuration
public class SecurityConfiguration {

    private final PasswordHasher passwordEncoder;
    private final JwtProvider jwtProvider;

    public SecurityConfiguration(
        @Value("${security.password.encoder.secret}") String passwordEncoderSecret,
        @Value("${security.jwt.secret}") String jwtSecret
    ) {
        passwordEncoder = new PasswordHasher(passwordEncoderSecret);
        jwtProvider = JwtProvider.create(jwtSecret);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Bean
    public Supplier<JwtBuilder> jwtBuilderFactory() {
        return jwtProvider;
    }

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .oauth2ResourceServer(jwtProvider)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/signup").permitAll()
                .requestMatchers("/api/issue-token").permitAll()
                .anyRequest().authenticated())
            .build();
    }
}
