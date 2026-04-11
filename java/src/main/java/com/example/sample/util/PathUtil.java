package com.example.sample.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathUtil {
    private static final Logger log = LoggerFactory.getLogger(PathUtil.class);

    /**
     * read file from process working dir(command launching dir when execute java -jar or run in IDE)
     */
    public static Path processWorkingDirectory() {
        Path p = Paths.get(System.getProperty("user.dir"));

        log.info("process working dir:\n{}", p.toString());

        return p;
    }
}
