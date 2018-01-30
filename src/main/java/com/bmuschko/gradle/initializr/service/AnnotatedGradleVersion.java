package com.bmuschko.gradle.initializr.service;

import com.bmuschko.gradle.initializr.metadata.GradleVersion;

public class AnnotatedGradleVersion {

    private final GradleVersion gradleVersion;
    private final String description;

    public AnnotatedGradleVersion(GradleVersion gradleVersion) {
        this(gradleVersion, null);
    }

    public AnnotatedGradleVersion(GradleVersion gradleVersion, String description) {
        this.gradleVersion = gradleVersion;
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder annotated = new StringBuilder();
        annotated.append(gradleVersion.toString());

        if (description != null) {
            annotated.append(" (").append(description).append(")");
        }

        return annotated.toString();
    }
}
