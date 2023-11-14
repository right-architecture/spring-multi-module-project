package commerce.http;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.util.function.Supplier;

@Configuration
public class SecurityConfiguration {

    private final SecretKeySpec jwtSecretKey;
    private final JwtDecoder jwtDecoder;

    public SecurityConfiguration(
        @Value("${security.jwt.secret}") String jwtSecret
    ) {
        jwtSecretKey = new SecretKeySpec(
            jwtSecret.getBytes(),
            SignatureAlgorithm.HS256.getJcaName());
        jwtDecoder = NimbusJwtDecoder.withSecretKey(jwtSecretKey).build();
    }

    @Bean
    public Supplier<JwtBuilder> jwtBuilderFactory() {
        return () -> Jwts.builder().signWith(jwtSecretKey);
    }

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.decoder(jwtDecoder)))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/signup").permitAll()
                .requestMatchers("/api/issue-token").permitAll()
                .anyRequest().authenticated());
        return http.build();
    }
}
