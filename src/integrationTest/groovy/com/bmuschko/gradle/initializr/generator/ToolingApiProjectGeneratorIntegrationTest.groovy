package com.bmuschko.gradle.initializr.generator

import com.bmuschko.gradle.initializr.model.ProjectRequest
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class ToolingApiProjectGeneratorIntegrationTest extends Specification {

    @Rule
    TemporaryFolder testProjectDir = new TemporaryFolder()

    @Subject
    def toolingApiProjectGenerator = new ToolingApiProjectGenerator()

    @Unroll
    def "can generate '#type' project"() {
        given:
        def projectRequest = new ProjectRequest()
        projectRequest.type = type

        when:
        toolingApiProjectGenerator.generate(testProjectDir.root, projectRequest)

        then:
        assertGeneratedGradleFiles()

        where:
        type << ['basic', 'java-library', 'java-application', 'groovy-application', 'groovy-library', 'scala-library']
    }

    @Unroll
    def "can generate '#type' project with test framework '#testFramework'"() {
        given:
        def projectRequest = new ProjectRequest()
        projectRequest.type = type
        projectRequest.testFramework = testFramework

        when:
        toolingApiProjectGenerator.generate(testProjectDir.root, projectRequest)

        then:
        assertGeneratedGradleFiles()

        where:
        [type, testFramework] << [['java-library', 'java-application'], ['testng', 'spock']].combinations()
    }

    @Unroll
    def "can generate '#type' project even if unsupported test framework is provided"() {
        given:
        def projectRequest = new ProjectRequest()
        projectRequest.type = type
        projectRequest.testFramework = 'spock'

        when:
        toolingApiProjectGenerator.generate(testProjectDir.root, projectRequest)

        then:
        assertGeneratedGradleFiles()

        where:
        type << ['basic', 'groovy-application', 'groovy-library', 'scala-library']
    }

    private void assertGeneratedGradleFiles() {
        assert new File(testProjectDir.root, 'build.gradle').isFile()
        assert new File(testProjectDir.root, 'settings.gradle').isFile()
        assert new File(testProjectDir.root, 'gradle').isDirectory()
    }
}
