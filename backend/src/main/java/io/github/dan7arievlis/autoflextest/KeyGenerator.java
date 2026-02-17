package io.github.dan7arievlis.autoflextest;

import java.io.FileWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class KeyGenerator {

    public static void main(String[] args) throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();

        String publicKey = Base64.getMimeEncoder(64, "\n".getBytes())
                .encodeToString(keyPair.getPublic().getEncoded());

        String privateKey = Base64.getMimeEncoder(64, "\n".getBytes())
                .encodeToString(keyPair.getPrivate().getEncoded());

        String publicPem =
                "-----BEGIN PUBLIC KEY-----\n" +
                        publicKey +
                        "\n-----END PUBLIC KEY-----\n";

        String privatePem =
                "-----BEGIN PRIVATE KEY-----\n" +
                        privateKey +
                        "\n-----END PRIVATE KEY-----\n";

        try (FileWriter pubWriter = new FileWriter("src/main/resources/keys/public.pem")) {
            pubWriter.write(publicPem);
        }

        try (FileWriter privWriter = new FileWriter("src/main/resources/keys/private.pem")) {
            privWriter.write(privatePem);
        }

        System.out.println("Chaves geradas:");
        System.out.println("public.pem");
        System.out.println("private.pem");
    }
}
