package kr.osam.phonevar.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FireBaseCloudMessage {
    @Bean
    public String getAccessToken() throws IOException {
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(getResourcePath())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }

    private InputStream getResourcePath() throws IOException {
        ClassPathResource resource = new ClassPathResource("firebase/phonevar-firebase-key.json");
        return resource.getInputStream();
    }
}
