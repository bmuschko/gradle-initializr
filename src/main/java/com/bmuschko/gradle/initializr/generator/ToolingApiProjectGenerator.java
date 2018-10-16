package com.bmuschko.gradle.initializr.generator;

import com.bmuschko.gradle.initializr.model.ProjectRequest;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        if (requestsExplicitDsl(projectRequest)) {
            tasks.add("--dsl");
            tasks.add(projectRequest.getDsl());
        }

        if (supportsTestFrameworkOption(projectRequest)) {
            tasks.add("--test-framework");
            tasks.add(projectRequest.getTestFramework());
        }

        if (providesProjectName(projectRequest)) {
            tasks.add("--project-name");
            tasks.add(projectRequest.getProjectName());
        }

        if (providesPackageName(projectRequest)) {
            tasks.add("--package");
            tasks.add(projectRequest.getPackageName());
        }

        return tasks.stream().toArray(String[]::new);
    }

    private static boolean requestsExplicitDsl(ProjectRequest projectRequest) {
        return isProvidedValue(projectRequest.getDsl());
    }

    private static boolean supportsTestFrameworkOption(ProjectRequest projectRequest) {
        return projectRequest.isJavaType() && isProvidedValue(projectRequest.getTestFramework());
    }

    private static boolean providesProjectName(ProjectRequest projectRequest) {
        return isProvidedValue(projectRequest.getProjectName());
    }

    private static boolean providesPackageName(ProjectRequest projectRequest) {
        return isProvidedValue(projectRequest.getPackageName());
    }

    private static boolean isProvidedValue(String value) {
        String nullableValue = Optional.ofNullable(value).orElse("");
        return !nullableValue.trim().isEmpty();
    }
}
