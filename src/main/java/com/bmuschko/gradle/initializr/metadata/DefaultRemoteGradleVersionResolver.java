package com.bmuschko.gradle.initializr.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DefaultRemoteGradleVersionResolver implements RemoteGradleVersionResolver {

    private static final String GRADLE_ALL_VERSIONS_URL = "https://services.gradle.org/versions/all";
    private final Logger logger = LoggerFactory.getLogger(DefaultRemoteGradleVersionResolver.class);
    private final RestTemplate restTemplate;

    public DefaultRemoteGradleVersionResolver(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public String getAllVersions() {
        if (logger.isDebugEnabled()) {
            logger.debug("Retrieving Gradle versions from services.gradle.org");
        }

        return restTemplate.getForObject(GRADLE_ALL_VERSIONS_URL, String.class);
    }
}
