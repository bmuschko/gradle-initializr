package com.bmuschko.gradle.initializr.archive;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Tar;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.TarFileSet;
import org.apache.tools.ant.types.ZipFileSet;
import org.springframework.stereotype.Component;

import java.io.File;

import static org.apache.tools.ant.taskdefs.Tar.TarCompressionMethod;

@Component
public class AntArchiver implements Archiver {

    private static final String ALL_FILES_RECURSIVELY = "**";
    private static final String HIDDEN_GRADLE_DIR = ".gradle/";

    @Override
    public void zip(File sourceDir, File targetFile) {
        Zip zip = new Zip();
        zip.setProject(new Project());
        zip.setDefaultexcludes(false);
        ZipFileSet set = new ZipFileSet();
        set.setDir(sourceDir);
        set.setIncludes(ALL_FILES_RECURSIVELY);
        set.setExcludes(HIDDEN_GRADLE_DIR);
        zip.addFileset(set);
        zip.setDestFile(targetFile);
        zip.execute();
    }

    @Override
    public void tar(File sourceDir, File targetFile) {
        Tar tar = new Tar();
        tar.setProject(new Project());
        tar.setDefaultexcludes(false);
        TarFileSet set = tar.createTarFileSet();
        set.setDir(sourceDir);
        set.setIncludes(ALL_FILES_RECURSIVELY);
        set.setExcludes(HIDDEN_GRADLE_DIR);
        set.setDefaultexcludes(false);
        tar.setDestFile(targetFile);
        TarCompressionMethod method = new TarCompressionMethod();
        method.setValue("gzip");
        tar.setCompression(method);
        tar.execute();
    }
}
