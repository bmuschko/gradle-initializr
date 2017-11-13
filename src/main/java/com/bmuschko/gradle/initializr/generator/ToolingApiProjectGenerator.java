package com.bmuschko.gradle.initializr.generator;

import com.bmuschko.gradle.initializr.model.ProjectRequest;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ToolingApiProjectGenerator implements ProjectGenerator {

    @Override
    public void generate(File targetDir, ProjectRequest projectRequest) {
        GradleConnector gradleConnector = GradleConnector.newConnector().forProjectDirectory(targetDir);

        if (projectRequest.getGradleVersion() != null) {
            gradleConnector.useGradleVersion(projectRequest.getGradleVersion());
        }

        ProjectConnection connection = gradleConnector.connect();

        try {
            connection.newBuild().forTasks("init", "--type", projectRequest.getType()).run();
        } finally {
            connection.close();
        }
    }
}
