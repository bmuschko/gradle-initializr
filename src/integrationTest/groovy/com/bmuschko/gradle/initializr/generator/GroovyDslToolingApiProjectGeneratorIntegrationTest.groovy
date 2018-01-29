package com.bmuschko.gradle.initializr.generator

class GroovyDslToolingApiProjectGeneratorIntegrationTest extends AbstractToolingApiProjectGeneratorIntegrationTest {

    @Override
    String getDsl() {
        'groovy'
    }

    @Override
    void assertGeneratedFiles() {
        assert new File(testProjectDir.root, 'build.gradle').isFile()
        assert new File(testProjectDir.root, 'settings.gradle').isFile()
        assertGradleDirectory()
    }
}
