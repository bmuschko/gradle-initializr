package com.bmuschko.gradle.initializr.service;

import com.bmuschko.gradle.initializr.model.ProjectRequest;

import java.util.List;

public interface GradleInitializrService {

    byte[] createZip(ProjectRequest projectRequest);
    byte[] createTgz(ProjectRequest projectRequest);
    List<AnnotatedGradleVersion> getGradleVersions();
}
