package com.bmuschko.gradle.initializr.metadata;

public interface GradleVersionReader {

    String getLatestFinalVersion();
    String getReleaseCandidateVersion();
    String getNightlyVersion();
}
