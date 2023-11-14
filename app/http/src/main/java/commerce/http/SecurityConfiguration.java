package commerce.http;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.util.function.Supplier;

@Configuration
public class SecurityConfiguration {

    private final String jwtSecret;
    private final String passwordEncoderSecret;

    public SecurityConfiguration(
        @Value("${security.jwt.secret}") String jwtSecret,
        @Value("${security.password.encoder.secret}") String passwordEncoderSecret
    ) {
        this.jwtSecret = jwtSecret;
        this.passwordEncoderSecret = passwordEncoderSecret;
    }

    @Bean
    public Supplier<JwtBuilder> jwtBuilderFactory() {
        var key = new SecretKeySpec(
            jwtSecret.getBytes(),
            SignatureAlgorithm.HS256.getJcaName());
        return () -> Jwts.builder().signWith(key);
    }

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .oauth2ResourceServer(oauth2 -> {
                var key = new SecretKeySpec(
                    jwtSecret.getBytes(),
                    SignatureAlgorithm.HS256.getJcaName());
                NimbusJwtDecoder decoder = NimbusJwtDecoder
                    .withSecretKey(key)
                    .build();
                oauth2.jwt(jwt -> jwt.decoder(decoder));
            })
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/signup").permitAll()
                .requestMatchers("/api/issue-token").permitAll()
                .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(
            passwordEncoderSecret,
            100,
            128,
            SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
    }
}
