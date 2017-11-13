package com.bmuschko.gradle.initializr.service;

import com.bmuschko.gradle.initializr.model.ProjectRequest;

public interface GradleInitializrService {

    byte[] createBasicZip(ProjectRequest projectRequest);
    byte[] createBasicTgz(ProjectRequest projectRequest);
}
