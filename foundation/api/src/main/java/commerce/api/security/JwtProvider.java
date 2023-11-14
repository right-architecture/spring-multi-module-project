package commerce.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;

public class JwtProvider implements
    Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> {

    private final SecretKeySpec key;
    private final NimbusJwtDecoder decoder;

    private JwtProvider(SecretKeySpec key, NimbusJwtDecoder decoder) {
        this.key = key;
        this.decoder = decoder;
    }

    public static JwtProvider create(String jwtSecret) {
        String algorithm = SignatureAlgorithm.HS256.getJcaName();
        var key = new SecretKeySpec(jwtSecret.getBytes(), algorithm);
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(key).build();
        return new JwtProvider(key, decoder);
    }

    public String composeToken(String subject) {
        return Jwts.builder().signWith(key).setSubject(subject).compact();
    }

    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> oauth2) {
        oauth2.jwt(jwt -> jwt.decoder(decoder));
    }
}
