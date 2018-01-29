package com.bmuschko.gradle.initializr.service;

import com.bmuschko.gradle.initializr.archive.Archiver;
import com.bmuschko.gradle.initializr.generator.ProjectGenerator;
import com.bmuschko.gradle.initializr.metadata.GradleVersion;
import com.bmuschko.gradle.initializr.metadata.GradleVersionReader;
import com.bmuschko.gradle.initializr.model.ProjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class DefaultGradleInitializrService implements GradleInitializrService {

    private final Logger logger = LoggerFactory.getLogger(DefaultGradleInitializrService.class);
    private final ProjectGenerator projectGenerator;
    private final Archiver archiver;
    private final GradleVersionReader gradleVersionReader;

    public DefaultGradleInitializrService(ProjectGenerator projectGenerator, Archiver archiver, GradleVersionReader gradleVersionReader) {
        this.projectGenerator = projectGenerator;
        this.archiver = archiver;
        this.gradleVersionReader = gradleVersionReader;
    }

    public byte[] createZip(ProjectRequest projectRequest) {
        return createDownloadFile((tmpDir) -> createZip(tmpDir, projectRequest));
    }

    public byte[] createTgz(ProjectRequest projectRequest) {
        return createDownloadFile((tmpDir) -> createTar(tmpDir, projectRequest));
    }

    private byte[] createDownloadFile(Function<File, File> action) {
        Path tmpDir = null;

        try {
            tmpDir = createTempDir();

            if (logger.isDebugEnabled()) {
                logger.debug("Generating project in temporary directory " + tmpDir.toFile());
            }

            File download = action.apply(tmpDir.toFile());
            return fileToByteArray(download);
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (tmpDir != null) {
                if (!FileSystemUtils.deleteRecursively(tmpDir.toFile())) {
                    logger.warn("Unable to delete temporary directory " + tmpDir.toFile());
                }
            }
        }
    }

    private Path createTempDir() throws IOException {
        return Files.createTempDirectory("gradle-initializr");
    }

    private File createZip(File targetDir, ProjectRequest projectRequest) {
        generateProject(targetDir, projectRequest);
        File file = new File(targetDir, "starter.zip");
        archiver.zip(targetDir, file);
        return file;
    }

    private File createTar(File targetDir, ProjectRequest projectRequest) {
        generateProject(targetDir, projectRequest);
        File file = new File(targetDir, "starter.tar");
        archiver.tar(targetDir, file);
        return file;
    }

    private void generateProject(File targetDir, ProjectRequest projectRequest) {
        projectGenerator.generate(targetDir, projectRequest);
    }

    private byte[] fileToByteArray(File file) throws IOException {
        return StreamUtils.copyToByteArray(new FileInputStream(file));
    }

    @Override
    public List<GradleVersion> getGradleVersions() {
        List<GradleVersion> gradleVersions = new ArrayList<>();
        gradleVersions.add(gradleVersionReader.getLatestFinalVersion());
        return gradleVersions;
    }
}
