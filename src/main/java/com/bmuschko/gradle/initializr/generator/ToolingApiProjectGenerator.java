package com.bmuschko.gradle.initializr.generator;

import com.bmuschko.gradle.initializr.model.ProjectRequest;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
            connection.newBuild().forTasks(buildTasks(projectRequest)).run();
        } finally {
            connection.close();
        }
    }

    private String[] buildTasks(ProjectRequest projectRequest) {
        List<String> tasks = new ArrayList<>();
        tasks.add("init");
        tasks.add("--type");
        tasks.add(projectRequest.getType());

        if (projectRequest.getTestFramework() != null && !projectRequest.getTestFramework().isEmpty()) {
            tasks.add("--test-framework");
            tasks.add(projectRequest.getTestFramework());
        }

        return tasks.stream().toArray(String[]::new);
    }
}
