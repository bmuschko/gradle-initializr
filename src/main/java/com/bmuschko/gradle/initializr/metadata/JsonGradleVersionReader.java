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
    public GradleVersion getLatestFinalVersion() {
        JSONObject version = new JSONObject(remoteGradleVersionResolver.getLatestFinalVersion());
        return new GradleVersion(version.getString(VERSION_ATTRIBUTE));
    }
}
