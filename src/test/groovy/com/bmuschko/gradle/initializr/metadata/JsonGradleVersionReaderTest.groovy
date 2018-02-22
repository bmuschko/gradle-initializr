package com.bmuschko.gradle.initializr.metadata

import spock.lang.Specification
import spock.lang.Subject

class JsonGradleVersionReaderTest extends Specification {

    private static final String GRADLE_4_5 = '4.5'
    def remoteGradleVersionResolver = Mock(RemoteGradleVersionResolver)

    @Subject
    def jsonGradleVersionReader = new JsonGradleVersionReader(remoteGradleVersionResolver)

    def "can get latest final Gradle version"() {
        when:
        def latestFinalVersion = jsonGradleVersionReader.getLatestFinalVersion()

        then:
        remoteGradleVersionResolver.latestFinalVersion >> getLatestVersionJson()
        latestFinalVersion == GRADLE_4_5
    }

    static String getLatestVersionJson() {
        """
            {
              "version" : "4.5",
              "buildTime" : "20180124170452+0000",
              "current" : true,
              "snapshot" : false,
              "nightly" : false,
              "activeRc" : false,
              "rcFor" : "",
              "milestoneFor" : "",
              "broken" : false,
              "downloadUrl" : "https://services.gradle.org/distributions/gradle-4.5-bin.zip",
              "checksumUrl" : "https://services.gradle.org/distributions/gradle-4.5-bin.zip.sha256"
            }
        """
    }
}
