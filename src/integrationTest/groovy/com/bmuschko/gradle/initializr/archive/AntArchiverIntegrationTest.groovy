package com.bmuschko.gradle.initializr.archive

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.TempDir

import java.util.zip.GZIPInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class AntArchiverIntegrationTest extends Specification {

    @TempDir
    File testProjectDir

    @TempDir
    File targetDir

    @Subject
    def antArchiver = new AntArchiver()

    private static final List<String> ALL_FILES = ['.gitignore',
                                                   'build.gradle',
                                                   'settings.gradle',
                                                   'gradle/wrapper/gradle-wrapper.jar',
                                                   'gradle/wrapper/gradle-wrapper.properties',
                                                   'src/main/java/Main.java']

    def setup() {
        new File(testProjectDir, '.gitignore').createNewFile()
        new File(testProjectDir,'build.gradle').createNewFile()
        new File(testProjectDir,'settings.gradle').createNewFile()
        def wrapperDir = new File(testProjectDir, 'gradle/wrapper')
        wrapperDir.mkdirs()
        new File(wrapperDir, 'gradle-wrapper.jar').createNewFile()
        new File(wrapperDir, 'gradle-wrapper.properties').createNewFile()
        def srcDir = new File(testProjectDir, 'src/main/java')
        srcDir.mkdirs()
        new File(srcDir, 'Main.java').createNewFile()
    }

    def "can generate ZIP file"() {
        given:
        def archiveFile = new File(targetDir, 'starter.zip')

        when:
        antArchiver.zip(testProjectDir, archiveFile)

        then:
        archiveFile.isFile()
        archiveFile.length() > 0
        List<String> allFiles = getFilesInZipArchive(archiveFile)
        allFiles.size() == 6
        allFiles.containsAll(ALL_FILES)
    }

    def "can generate TAR file"() {
        given:
        def archiveFile = new File(targetDir, 'starter.tar.gz')

        when:
        antArchiver.tar(testProjectDir, archiveFile)

        then:
        archiveFile.isFile()
        archiveFile.length() > 0
        List<String> allFiles = getFilesInTarGzArchive(archiveFile)
        allFiles.size() == 6
        allFiles.containsAll(ALL_FILES)
    }

    static List<File> getFilesInZipArchive(File archiveFile) {
        List<String> allFiles = []

        ZipFile zipFile = new ZipFile(archiveFile)
        Enumeration<? extends ZipEntry> entries = zipFile.entries()

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement()
            if (!entry.isDirectory()) {
                allFiles << entry.name
            }
        }

        allFiles
    }

    static List<File> getFilesInTarGzArchive(File archiveFile) {
        List<String> allFiles = []

        FileInputStream fileInputStream = new FileInputStream(archiveFile)
        GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream)
        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(gzipInputStream)
        TarArchiveEntry entry

        while (entry = tarArchiveInputStream.getNextTarEntry()) {
            if (!entry.isDirectory()) {
                allFiles << entry.name
            }
        }

        allFiles
    }
}
