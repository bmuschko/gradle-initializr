package com.bmuschko.gradle.initializr.archive

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Subject

class AntArchiverIntegrationTest extends Specification {

    @Rule
    TemporaryFolder testProjectDir = new TemporaryFolder()

    @Rule
    TemporaryFolder targetDir = new TemporaryFolder()

    @Subject
    def antArchiver = new AntArchiver()

    def setup() {
        testProjectDir.newFile('build.gradle')
        testProjectDir.newFile('settings.gradle')
        def wrapperDir = testProjectDir.newFolder('gradle', 'wrapper')
        new File(wrapperDir, 'gradle-wrapper.jar').createNewFile()
        new File(wrapperDir, 'gradle-wrapper.properties').createNewFile()
        def srcDir = testProjectDir.newFolder('src', 'main', 'java')
        new File(srcDir, 'Main.java').createNewFile()
    }

    def "can generate ZIP file"() {
        given:
        def archiveFile = new File(targetDir.root, 'starter.zip')

        when:
        antArchiver.zip(testProjectDir.root, archiveFile)

        then:
        archiveFile.isFile()
        archiveFile.length() > 0
    }

    def "can generate TAR file"() {
        given:
        def archiveFile = new File(targetDir.root, 'starter.tar.gz')

        when:
        antArchiver.tar(testProjectDir.root, archiveFile)

        then:
        archiveFile.isFile()
        archiveFile.length() > 0
    }
}
