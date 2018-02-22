package com.bmuschko.gradle.initializr.metadata;

import org.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class JsonGradleVersionReader implements GradleVersionReader {

    private static final String VERSION_ATTRIBUTE = "version";
    private final RemoteGradleVersionResolver remoteGradleVersionResolver;

    public JsonGradleVersionReader(RemoteGradleVersionResolver remoteGradleVersionResolver) {
        this.remoteGradleVersionResolver = remoteGradleVersionResolver;
    }

    @Override
    @Cacheable(cacheNames = "latestFinalGradleVersion", sync = true)
    public String getLatestFinalVersion() {
        return parseGradleVersion(remoteGradleVersionResolver.getLatestFinalVersion());
    }

    @Override
    @Cacheable(cacheNames = "releaseCandidateGradleVersion", sync = true)
    public String getReleaseCandidateVersion() {
        return parseGradleVersion(remoteGradleVersionResolver.getReleaseCandidateVersion());
    }

    @Override
    @Cacheable(cacheNames = "nightlyGradleVersion", sync = true)
    public String getNightlyVersion() {
        return parseGradleVersion(remoteGradleVersionResolver.getNightlyVersion());
    }

    private String parseGradleVersion(String json) {
        JSONObject version = new JSONObject(json);

        if (version.has(VERSION_ATTRIBUTE)) {
            return version.getString(VERSION_ATTRIBUTE);
        }

        return null;
    }
}
