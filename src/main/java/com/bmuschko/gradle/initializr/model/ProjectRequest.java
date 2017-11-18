package com.bmuschko.gradle.initializr.model;

public class ProjectRequest {

    private String type;
    private String testFramework;
    private String gradleVersion;
    private String archive;

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

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }

    public boolean isJavaType() {
        return "java-application".equals(type) || "java-library".equals(type);
    }
}
