package com.bmuschko.gradle.initializr.generator

class KotlinDslToolingApiProjectGeneratorIntegrationTest extends AbstractToolingApiProjectGeneratorIntegrationTest {

    @Override
    String getDsl() {
        'kotlin'
    }

    @Override
    void assertGeneratedFiles() {
        assert new File(testProjectDir.root, 'build.gradle.kts').isFile()
        assert new File(testProjectDir.root, 'settings.gradle.kts').isFile()
        assertGradleDirectory()
    }
}