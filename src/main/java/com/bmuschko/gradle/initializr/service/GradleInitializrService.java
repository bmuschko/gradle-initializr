package com.bmuschko.gradle.initializr.service;

import com.bmuschko.gradle.initializr.metadata.GradleVersion;
import com.bmuschko.gradle.initializr.model.ProjectRequest;

import java.util.List;

public interface GradleInitializrService {

    byte[] createBasicZip(ProjectRequest projectRequest);
    byte[] createBasicTgz(ProjectRequest projectRequest);
    List<GradleVersion> getGradleVersions();
}
