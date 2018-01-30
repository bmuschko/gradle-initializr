package com.bmuschko.gradle.initializr.metadata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class GradleVersion implements Comparable<GradleVersion> {

    private static final Pattern VERSION_PATTERN = Pattern.compile("((\\d+)(\\.\\d+)+)(-(\\p{Alpha}+)-(\\d+[a-z]?))?(-(SNAPSHOT|\\d{14}([-+]\\d{4})?))?");
    private final Integer major;
    private final Integer minor;
    private final Integer patch;
    private final String timestamp;

    public GradleVersion(Integer major, Integer minor) {
        this(major, minor, null);
    }

    public GradleVersion(Integer major, Integer minor, Integer patch) {
        this(major, minor, patch, null);
    }

    public GradleVersion(Integer major, Integer minor, Integer patch, String timestamp) {
        assert major != null : "major version attribute may not be null";
        assert minor != null : "minor version attribute may not be null";
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.timestamp = timestamp;
    }

    public GradleVersion(String version) {
        Matcher matcher = VERSION_PATTERN.matcher(version);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(format("'%s' is not a valid Gradle version string (examples: '1.0', '1.0-rc-1')", version));
        }

        String[] attributes = matcher.group(1).split("\\.");
        major = Integer.parseInt(attributes[0]);
        minor = Integer.parseInt(attributes[1]);
        patch = (attributes.length == 3) ? Integer.parseInt(attributes[2]) : null;

        String buildTimestamp = matcher.group(8);
        timestamp = buildTimestamp != null ? buildTimestamp : null;
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

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradleVersion that = (GradleVersion) o;

        if (!major.equals(that.major)) return false;
        if (!minor.equals(that.minor)) return false;
        if (patch != null ? !patch.equals(that.patch) : that.patch != null) return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;
    }

    @Override
    public int hashCode() {
        int result = major.hashCode();
        result = 31 * result + minor.hashCode();
        result = 31 * result + (patch != null ? patch.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
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

        if (timestamp != null) {
            version.append("-");
            version.append(timestamp);
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
