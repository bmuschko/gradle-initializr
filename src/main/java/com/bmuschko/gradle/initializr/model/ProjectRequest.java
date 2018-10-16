package com.bmuschko.gradle.initializr.model;

import java.util.Objects;

public class ProjectRequest {

    private String type;
    private String dsl;
    private String testFramework;
    private String gradleVersion;
    private String archive;
    private String projectName;
    private String packageName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDsl() {
        return dsl;
    }

    public void setDsl(String dsl) {
        this.dsl = dsl;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isJavaType() {
        return "java-application".equals(type) || "java-library".equals(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectRequest)) return false;
        ProjectRequest that = (ProjectRequest) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(dsl, that.dsl) &&
                Objects.equals(testFramework, that.testFramework) &&
                Objects.equals(gradleVersion, that.gradleVersion) &&
                Objects.equals(archive, that.archive) &&
                Objects.equals(projectName, that.projectName) &&
                Objects.equals(packageName, that.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, dsl, testFramework, gradleVersion, archive, projectName, packageName);
    }
}
