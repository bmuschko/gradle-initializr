package com.bmuschko.gradle.initializr.service;

public class AnnotatedGradleVersion {

    private final String gradleVersion;
    private final String description;

    public AnnotatedGradleVersion(String gradleVersion) {
        this(gradleVersion, null);
    }

    public AnnotatedGradleVersion(String gradleVersion, String description) {
        this.gradleVersion = gradleVersion;
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder annotated = new StringBuilder();
        annotated.append(gradleVersion);

        if (description != null) {
            annotated.append(" (").append(description).append(")");
        }

        return annotated.toString();
    }
}
