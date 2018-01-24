package com.kabasakalis.springifyapi.security;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;

@Component
public class SecretKeyProvider {
    public byte[] getKey() throws URISyntaxException, IOException {

        String jwtkey = "/jwt.key";
        URI uri = getClass().getResource(jwtkey).toURI();
        Path path;
        FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
        try {
            if (uri.getScheme().equals("jar")) {
                path = fileSystem.getPath("/BOOT-INF/classes" + jwtkey);
            } else {
                // Not running in a jar, so just use a regular filesystem path
                path = Paths.get(uri);
            }

            return Files.readAllBytes(path);
        } finally {
            fileSystem.close();
        }
    }
}
