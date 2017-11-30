package com.bmuschko.gradle.initializr.web;

import com.bmuschko.gradle.initializr.model.ProjectRequest;
import com.bmuschko.gradle.initializr.service.GradleInitializrService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class GradleInitializrController {

    private static final String ZIP_CONTENT_TYPE = "application/zip";
    private static final String LZW_CONTENT_TYPE = "application/x-compress";
    private final GradleInitializrService gradleInitializrService;

    public GradleInitializrController(GradleInitializrService gradleInitializrService) {
        this.gradleInitializrService = gradleInitializrService;
    }

    @RequestMapping("/")
    public String home(Map<String, Object> model) {
        model.put("gradleVersions", gradleInitializrService.getGradleVersions());
        return "home";
    }

    @RequestMapping("/starter")
    public String starter(ProjectRequest projectRequest) {
        switch (projectRequest.getArchive()) {
            case "zip": return "forward:/starter.zip";
            case "tgz": return "forward:/starter.tgz";
            default: return "redirect:/error";
        }
    }

    @RequestMapping("/starter.zip")
    @ResponseBody
    public ResponseEntity<byte[]> starterZip(ProjectRequest projectRequest) {
        byte[] bytes = gradleInitializrService.createZip(projectRequest);
        return createResponseEntity(bytes, ZIP_CONTENT_TYPE, "starter.zip");
    }

    @RequestMapping(value = "/starter.tgz", produces = LZW_CONTENT_TYPE)
    @ResponseBody
    public ResponseEntity<byte[]> starterTgz(ProjectRequest projectRequest) {
        byte[] bytes = gradleInitializrService.createTgz(projectRequest);
        return createResponseEntity(bytes, LZW_CONTENT_TYPE, "starter.tar.gz");
    }

    private ResponseEntity<byte[]> createResponseEntity(byte[] content, String contentType, String fileName) {
        String contentDispositionValue = "attachment; filename=\"" + fileName + "\"";
        return ResponseEntity.ok().header("Content-Type", contentType).header("Content-Disposition", contentDispositionValue).body(content);
    }
}
