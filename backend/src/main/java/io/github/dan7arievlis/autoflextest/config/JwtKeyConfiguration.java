package io.github.dan7arievlis.autoflextest.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.*;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtKeyConfiguration {

    @Value("${JWT_PUBLIC_KEY}")
    private String publicKeyValue;

    @Value("${JWT_PRIVATE_KEY}")
    private String privateKeyValue;

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
        RSAPublicKey publicKey = getPublicKey();
        RSAPrivateKey privateKey = getPrivateKey();

        return new NimbusJwtEncoder(
                new ImmutableJWKSet<>(
                        new JWKSet(
                                new RSAKey.Builder(publicKey)
                                        .privateKey(privateKey)
                                        .build()
                        )
                )
        );
    }

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        return NimbusJwtDecoder.withPublicKey(getPublicKey()).build();
    }

    private RSAPublicKey getPublicKey() throws Exception {
        byte[] publicBytes = Base64.getDecoder().decode(publicKeyValue);
        return (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(publicBytes));
    }

    private RSAPrivateKey getPrivateKey() throws Exception {
        byte[] privateBytes = Base64.getDecoder().decode(privateKeyValue);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
    }
}
