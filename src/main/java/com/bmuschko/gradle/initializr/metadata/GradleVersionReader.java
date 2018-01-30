package com.bmuschko.gradle.initializr.metadata;

public interface GradleVersionReader {

    GradleVersion getLatestFinalVersion();
    GradleVersion getNightlyVersion();
}
