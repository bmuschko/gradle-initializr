package com.bmuschko.gradle.initializr.archive;

import java.io.File;

public interface Archiver {

    void zip(File sourceDir, File targetFile);
    void tar(File sourceDir, File targetFile);
}
