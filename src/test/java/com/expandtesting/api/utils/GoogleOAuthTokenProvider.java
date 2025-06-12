package com.expandtesting.api.utils;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class GoogleOAuthTokenProvider {

    public static String getAccessToken() throws IOException {
        InputStream stream = GoogleOAuthTokenProvider.class
                .getClassLoader()
                .getResourceAsStream("service-account.json");

        if (stream == null) {
            throw new FileNotFoundException("service-account.json not found in classpath.");
        }

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(stream)
                .createScoped(List.of(
                        "https://www.googleapis.com/auth/userinfo.email",
                        "https://www.googleapis.com/auth/drive",
                        "https://www.googleapis.com/auth/cloud-platform"
                ));

        credentials.refreshIfExpired();
        return credentials.getAccessToken().getTokenValue();
    }
}
