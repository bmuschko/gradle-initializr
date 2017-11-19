package com.bmuschko.gradle.initializr.metadata;

public class GradleVersion implements Comparable<GradleVersion> {

    private final Integer major;
    private final Integer minor;
    private final Integer patch;

    public GradleVersion(Integer major, Integer minor) {
        this.major = major;
        this.minor = minor;
        this.patch = null;
    }

    public GradleVersion(Integer major, Integer minor, Integer patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public GradleVersion(String version) {
        String[] attributes = version.split("\\.");
        major = Integer.parseInt(attributes[0]);
        minor = Integer.parseInt(attributes[1]);

        if (attributes.length == 3) {
            patch = Integer.parseInt(attributes[2]);
        } else {
            patch = null;
        }
    }

    public Integer getMajor() {
        return major;
    }

    public Integer getMinor() {
        return minor;
    }

    public Integer getPatch() {
        return patch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradleVersion that = (GradleVersion) o;

        if (major != null ? !major.equals(that.major) : that.major != null) return false;
        if (minor != null ? !minor.equals(that.minor) : that.minor != null) return false;
        return patch != null ? patch.equals(that.patch) : that.patch == null;
    }

    @Override
    public int hashCode() {
        int result = major != null ? major.hashCode() : 0;
        result = 31 * result + (minor != null ? minor.hashCode() : 0);
        result = 31 * result + (patch != null ? patch.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder version = new StringBuilder();
        version.append(major);
        version.append(".");
        version.append(minor);

        if (patch != null) {
            version.append(".");
            version.append(patch);
        }

        return version.toString();
    }

    @Override
    public int compareTo(GradleVersion o) {
        if (this.major > o.major) {
            return 1;
        }
        if (o.major > this.major) {
            return -1;
        }

        if (this.minor > o.minor) {
            return 1;
        }
        if (o.minor > this.minor) {
            return -1;
        }

        if (o.patch != null && this.patch != null) {
            if (this.patch > o.patch) {
                return 1;
            }
            if (o.patch > this.patch) {
                return -1;
            }
        }

        return 0;
    }
}
