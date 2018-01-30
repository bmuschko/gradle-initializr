package com.bmuschko.gradle.initializr.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DefaultRemoteGradleVersionResolver implements RemoteGradleVersionResolver {

    public static final String BASE_URL = "https://services.gradle.org/versions/";
    public static final String GRADLE_CURRENT_VERSION_URL = BASE_URL + "current";
    private final Logger logger = LoggerFactory.getLogger(DefaultRemoteGradleVersionResolver.class);
    private final RestTemplate restTemplate;

    public DefaultRemoteGradleVersionResolver(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public String getLatestFinalVersion() {
        if (logger.isDebugEnabled()) {
            logger.debug("Retrieving latest final Gradle version via {}", GRADLE_CURRENT_VERSION_URL);
        }

        return restTemplate.getForObject(GRADLE_CURRENT_VERSION_URL, String.class);
    }
}
