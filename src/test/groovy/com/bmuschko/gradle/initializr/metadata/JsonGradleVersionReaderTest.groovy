package com.bmuschko.gradle.initializr.metadata

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class JsonGradleVersionReaderTest extends Specification {

    private static final GradleVersion GRADLE_4_4 = new GradleVersion(4, 4)
    private static final GradleVersion GRADLE_4_5 = new GradleVersion(4, 5)
    private static final GradleVersion GRADLE_4_6 = new GradleVersion(4, 6)
    def remoteGradleVersionResolver = Mock(RemoteGradleVersionResolver)

    @Subject
    def jsonGradleVersionReader = new JsonGradleVersionReader(remoteGradleVersionResolver)

    @Unroll
    def "can filter Gradle versions starting from minimum given version #minVersion"() {
        when:
        def gradleVersions = jsonGradleVersionReader.getFinalVersionsGreaterEquals(minVersion)

        then:
        remoteGradleVersionResolver.allVersions >> getNoFinalVersionJson()
        gradleVersions == expectedVersions

        where:
        minVersion | expectedVersions
        GRADLE_4_4 | [GRADLE_4_5]
        GRADLE_4_5 | [GRADLE_4_5]
        GRADLE_4_6 | []
    }

    static String getNoFinalVersionJson() {
        """
            [
               {
                  version:"4.5",
                  buildTime:"20171125235933+0000",
                  current:false,
                  snapshot:false,
                  nightly:false,
                  activeRc:false,
                  rcFor:"",
                  milestoneFor:"",
                  broken:false,
                  downloadUrl:"https://services.gradle.org/distributions-snapshots/gradle-4.5-bin.zip",
                  checksumUrl:"https://services.gradle.org/distributions-snapshots/gradle-4.5-bin.zip.sha256"
               },
               {
                  version:"4.4-rc-2",
                  buildTime:"20171124103528+0000",
                  current:false,
                  snapshot:false,
                  nightly:false,
                  activeRc:true,
                  rcFor:"4.4",
                  milestoneFor:"",
                  broken:false,
                  downloadUrl:"https://services.gradle.org/distributions/gradle-4.4-rc-2-bin.zip",
                  checksumUrl:"https://services.gradle.org/distributions/gradle-4.4-rc-2-bin.zip.sha256"
               }
            ]
        """
    }
}
