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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectRequest that = (ProjectRequest) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (testFramework != null ? !testFramework.equals(that.testFramework) : that.testFramework != null)
            return false;
        if (gradleVersion != null ? !gradleVersion.equals(that.gradleVersion) : that.gradleVersion != null)
            return false;
        return archive != null ? archive.equals(that.archive) : that.archive == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (testFramework != null ? testFramework.hashCode() : 0);
        result = 31 * result + (gradleVersion != null ? gradleVersion.hashCode() : 0);
        result = 31 * result + (archive != null ? archive.hashCode() : 0);
        return result;
    }
}
