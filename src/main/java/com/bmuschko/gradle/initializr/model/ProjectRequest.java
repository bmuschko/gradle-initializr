package com.bmuschko.gradle.initializr.model;

public class ProjectRequest {

    private String type;
    private String testFramework;
    private String gradleVersion;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTestFramework() {
        return testFramework;
    }

    public void setTestFramework(String testFramework) {
        this.testFramework = testFramework;
    }

    public String getGradleVersion() {
        return gradleVersion;
    }

    public void setGradleVersion(String gradleVersion) {
        this.gradleVersion = gradleVersion;
    }
}
