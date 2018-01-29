package com.bmuschko.gradle.initializr.metadata;

import java.util.List;

public interface GradleVersionReader {

    List<GradleVersion> getFinalVersionsGreaterEquals(GradleVersion minVersion);
    GradleVersion getLatestFinalVersion();
}
