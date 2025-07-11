package com.example.sample.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class FileBackupUtilTests {

    @Test
    void collectFileTest() {
        var dirs = new ArrayList<Path>();
        dirs.add(Path.of("/home/swvm/src/dev-lang-sample/java"));
        try {
            var files = FileBackupUtil.collectFiles(dirs);
            files.forEach(e -> System.out.println(e.toString()));
        } catch (IOException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    void copyDirectoryTest() {
        Path src = Path.of(System.getProperty(("user.dir")) + "/src/test/java/com/example/sample/exam");
        Path dst = Path.of(System.getProperty(("user.dir")) + "/src/test/java/com/example/sample/exam2");
        try {
            FileBackupUtil.copyDirectory(src, dst);
        } catch (IOException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("copy files complete");
    }


    @Test
    void backupFilesTest() {
        Path srcdir = Path.of(System.getProperty(("user.dir")) + "/src/test/java/com/example/sample/exam");
        Path dst = Path.of(System.getProperty(("user.dir")) + "/src/test/java/com/example/sample/backup");

        var list = new ArrayList<Path>();
        list.add(srcdir);

        try {
            var files = FileBackupUtil.collectFiles(list);
            // make backup from scratch, and remove old exsiting backup dir
            FileBackupUtil.backupFiles(files, dst, true, true);
            // make backup from scratch, and do NOT remove old exsiting backup dir
            FileBackupUtil.backupFiles(files, dst, true, false);
            // make backup overwriting way
            FileBackupUtil.backupFiles(files, dst, false, false);
        } catch (IOException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("backup complete");
    }
}
