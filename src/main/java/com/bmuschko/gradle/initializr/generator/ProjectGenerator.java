package com.bmuschko.gradle.initializr.generator;

import com.bmuschko.gradle.initializr.model.ProjectRequest;

import java.io.File;

public interface ProjectGenerator {

    void generate(File targetDir, ProjectRequest projectRequest);
}
