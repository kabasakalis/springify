package com.kabasakalis.springifyapi.security;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;

@Component
public class SecretKeyProvider {
    public byte[] getKey() throws URISyntaxException, IOException {
//        return Files.readAllBytes(Paths.get(this.getClass().getResource("/jwt.key").toURI()));
     return     hexStringToByteArray("thekey");
    }
}
