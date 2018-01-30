package com.bmuschko.gradle.initializr.metadata

import spock.lang.Specification

class GradleVersionTest extends Specification {

    def "throws exception for null major or minor version attribute"() {
        when:
        new GradleVersion(major, minor)

        then:
        thrown(AssertionError)

        where:
        major | minor
        1     | null
        null  | 1
    }

    def "can create String representation for new instance"() {
        expect:
        gradleVersion.toString() == stringRepresentation

        where:
        gradleVersion                                        | stringRepresentation
        new GradleVersion(1, 2)                 | '1.2'
        new GradleVersion(1, 2, 3)       | '1.2.3'
        new GradleVersion('1.2')                     | '1.2'
        new GradleVersion('1.2.3')                   | '1.2.3'
        new GradleVersion('4.6-20180129223723+0000') | '4.6-20180129223723+0000'
    }

    def "can access version parts"() {
        expect:
        gradleVersion.major == major
        gradleVersion.minor == minor
        gradleVersion.patch == patch
        gradleVersion.timestamp == timestamp

        where:
        gradleVersion                                                                   | major | minor | patch | timestamp
        new GradleVersion(1, 2)                                            | 1     | 2     | null  | null
        new GradleVersion(1, 2, 3)                                  | 1     | 2     | 3     | null
        new GradleVersion(1, 2, 3, '20180129223723+0000') | 1     | 2     | 3     | '20180129223723+0000'
    }

    def "can verify if two versions are equal"() {
        expect:
        gradleVersion1.equals(gradleVersion2) == result

        where:
        gradleVersion1                                                                  | gradleVersion2                                                                  | result
        new GradleVersion(1, 2)                                            | new GradleVersion(1, 2)                                            | true
        new GradleVersion(1, 2)                                            | new GradleVersion(1, 3)                                            | false
        new GradleVersion(1, 2, 3)                                  | new GradleVersion(1, 2, 3)                                   | true
        new GradleVersion(1, 2, 3)                                  | new GradleVersion(1, 2, 4)                                   | false
        new GradleVersion(1, 2, 3, '20180129223723+0000') | new GradleVersion(1, 2, 3, '20180129223723+0000') | true
        new GradleVersion(1, 2, 3, '20180129235666+0000') | new GradleVersion(1, 2, 3, '20180129223723+0000') | false
    }

    def "can compare two versions"() {
        expect:
        gradleVersion1.compareTo(gradleVersion2) == result

        where:
        gradleVersion1                                 | gradleVersion2                                | result
        new GradleVersion(1, 2)           | new GradleVersion(1, 2)          | 0
        new GradleVersion(1, 2)           | new GradleVersion(2, 3)          | -1
        new GradleVersion(2, 2)           | new GradleVersion(1, 3)          | 1
        new GradleVersion(1, 2)           | new GradleVersion(1, 3)          | -1
        new GradleVersion(1, 3)           | new GradleVersion(1, 2)          | 1
        new GradleVersion(1, 2, 3) | new GradleVersion(1, 2, 3) | 0
        new GradleVersion(1, 2, 3) | new GradleVersion(1, 2, 4) | -1
        new GradleVersion(1, 2, 4) | new GradleVersion(1, 2, 3) | 1
    }
}
