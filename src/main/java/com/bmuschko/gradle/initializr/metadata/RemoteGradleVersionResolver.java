package com.bmuschko.gradle.initializr.metadata;

public interface RemoteGradleVersionResolver {

    String getAllVersions();
    String getLatestFinalVersion();
}
