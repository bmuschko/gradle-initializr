package com.bmuschko.gradle.initializr.metadata;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonGradleVersionReader implements GradleVersionReader {

    private static final String GRADLE_ALL_VERSIONS_URL = "https://services.gradle.org/versions/all";
    private static final String VERSION_ATTRIBUTE = "version";
    private static final String SNAPSHOT_ATTRIBUTE = "snapshot";
    private static final String NIGHTLY_ATTRIBUTE = "nightly";
    private static final String RC_FOR_ATTRIBUTE = "rcFor";
    private static final String MILESTONE_FOR_ATTRIBUTE = "milestoneFor";
    private final RestTemplate restTemplate;

    public JsonGradleVersionReader(@Autowired RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<GradleVersion> getFinalVersionsGreaterEquals(GradleVersion minVersion) {
        List<GradleVersion> allVersions = new ArrayList<>();
        JSONArray versions = new JSONArray(restTemplate.getForObject(GRADLE_ALL_VERSIONS_URL, String.class));

        for (int i = 0; i < versions.length(); i++) {
            JSONObject version = versions.getJSONObject(i);

            if (isFinalVersion(version)) {
                GradleVersion semanticVersion = new GradleVersion(version.getString(VERSION_ATTRIBUTE));

                if (isGreaterEquals(minVersion, semanticVersion)) {
                    allVersions.add(semanticVersion);
                }
            }
        }

        return allVersions;
    }

    private boolean isFinalVersion(JSONObject versionAttributes) {
        boolean snapshot = versionAttributes.getBoolean(SNAPSHOT_ATTRIBUTE);
        boolean nightly = versionAttributes.getBoolean(NIGHTLY_ATTRIBUTE);
        String rcFor = versionAttributes.getString(RC_FOR_ATTRIBUTE);
        String milestoneFor = versionAttributes.getString(MILESTONE_FOR_ATTRIBUTE);
        String version = versionAttributes.getString(VERSION_ATTRIBUTE);
        return !snapshot && !nightly && "".equals(rcFor) && "".equals(milestoneFor) && !version.contains("-");
    }

    private boolean isGreaterEquals(GradleVersion minVersion, GradleVersion version) {
        int versionComparison = version.compareTo(minVersion);
        return versionComparison == 0 || versionComparison == 1;
    }
}
