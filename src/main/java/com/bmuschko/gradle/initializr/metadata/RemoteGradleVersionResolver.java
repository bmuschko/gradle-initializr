package com.bmuschko.gradle.initializr.metadata;

public interface RemoteGradleVersionResolver {

    String getLatestFinalVersion();
    String getNightlyVersion();
}
