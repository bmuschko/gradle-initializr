package com.bmuschko.gradle.initializr.generator

import com.bmuschko.gradle.initializr.model.ProjectRequest
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

abstract class AbstractToolingApiProjectGeneratorIntegrationTest extends Specification {

    @Rule
    TemporaryFolder testProjectDir = new TemporaryFolder()

    @Subject
    def toolingApiProjectGenerator = new ToolingApiProjectGenerator()

    @Unroll
    def "can generate '#type' project"() {
        given:
        def projectRequest = new ProjectRequest()
        projectRequest.type = type
        projectRequest.dsl = getDsl()

        when:
        toolingApiProjectGenerator.generate(testProjectDir.root, projectRequest)

        then:
        assertGeneratedFiles()

        where:
        type << ['basic', 'java-library', 'java-application', 'groovy-application', 'groovy-library', 'scala-library']
    }

    @Unroll
    def "can generate '#type' project with test framework '#testFramework'"() {
        given:
        def projectRequest = new ProjectRequest()
        projectRequest.type = type
        projectRequest.testFramework = testFramework
        projectRequest.dsl = getDsl()

        when:
        toolingApiProjectGenerator.generate(testProjectDir.root, projectRequest)

        then:
        assertGeneratedFiles()

        where:
        [type, testFramework] << [['java-library', 'java-application'], ['testng', 'spock']].combinations()
    }

    @Unroll
    def "can generate '#type' project even if unsupported test framework is provided"() {
        given:
        def projectRequest = new ProjectRequest()
        projectRequest.type = type
        projectRequest.testFramework = 'spock'
        projectRequest.dsl = getDsl()

        when:
        toolingApiProjectGenerator.generate(testProjectDir.root, projectRequest)

        then:
        assertGeneratedFiles()

        where:
        type << ['basic', 'groovy-application', 'groovy-library', 'scala-library']
    }

    abstract String getDsl()
    abstract void assertGeneratedFiles()

    void assertGradleDirectory() {
        assert new File(testProjectDir.root, 'gradle').isDirectory()
    }
}
