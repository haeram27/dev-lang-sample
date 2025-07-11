package com.example.sample.utils;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileBackupUtil {

    /**
     * validate path string format
     *
     * @param pathStr           path string
     * @param isAbsolutePath    validate absolute path pattern
     * @param isWritable        validate path is writable, true if path SHOULD be writable
     * @throws IllegalArgumentException pathStr has invalid format
     */
    public static void validatePathString(String pathStr, boolean isAbsolutePath, boolean isWritable)
            throws IllegalArgumentException {
        if (pathStr == null || pathStr.isBlank()) {
            throw new IllegalArgumentException("path string is empty");
        }

        // Windows OS Only, check illegal characters 
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            String illegalChars = "*?<>\"|";
            for (char c : illegalChars.toCharArray()) {
                if (pathStr.indexOf(c) >= 0) {
                    throw new IllegalArgumentException("path includes illegal characters: " + c);
                }
            }
        }

        Path path;
        try {
            path = Path.of(pathStr).toAbsolutePath().normalize();
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("invalid path format: " + pathStr);
        }

        if (isAbsolutePath == true) {
            if (!path.isAbsolute()) {
                throw new IllegalArgumentException("path string is not absolute: " + pathStr);
            }
        }

        if (isWritable == true) {
            if (Files.exists(path)) {
                if (!Files.isWritable(path)) {
                    throw new IllegalArgumentException("path parent is NOT writable: " + path);
                }
            } else {
                // if path is NOT exist, check parent is writable
                Path parent = path.getParent();
                if (parent == null || !Files.exists(parent) || !Files.isWritable(parent)) {
                    throw new IllegalArgumentException("path is NOT exist and path parent is NOT writable: " + parent);
                }
            }
        }
    }

    /**
     * backup files to directory
     * if there is already existing backup directory, it will be renamed and preserved.
     * 
     * @param sourceFiles        files, absolute path is only allowed
     * @param backupDir          directory path to backup files, absolute path is only allowed
     * @param resetbackupDir     re-build backup directory from scratch,
     *                           if true, existing backup directory will be preserved and renamed with time seconds
     *                           if false, files will be copied to exsiting backup directory with overwriting way
     * @param removeOldBackup    remove old backup directoty after backup complete,
     *                           this option is valid when only "resetbackupDir" is true
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public static void backupFiles(List<Path> sourceFiles,
                                    Path backupDir,
                                    boolean resetbackupDir,
                                    boolean removeOldBackup) throws IllegalArgumentException, IOException {

        if (!backupDir.isAbsolute()) {
            throw new IllegalArgumentException("Error: only absolute path is allowed: " + backupDir);
        }

        sourceFiles.stream().forEach(e -> { 
            if (!e.isAbsolute()) {
                throw new IllegalArgumentException("Error: only absolute path is allowed: " + sourceFiles);
            }
        });

        backupDir = backupDir.normalize();
        Path renamedOldBackupRoot = null;

        // rename old backup directory
        if (Files.exists(backupDir)) {
            if (resetbackupDir) {
                renamedOldBackupRoot = getUniqueBackupNameWithTimeSeconds(backupDir, "", "");
                Files.move(backupDir, renamedOldBackupRoot, StandardCopyOption.ATOMIC_MOVE);
            }
        }

        // create backup
        Files.createDirectories(backupDir);

        for (Path sourceFile : sourceFiles) {
            // make canonical path
            Path source = sourceFile.normalize();

            try {
                // get relative path based on root('/')
                // ex: etc/nginx/nginx.conf
                Path relativePath = source.getRoot().relativize(source);

                // get target path: add prefix relative path behind of source path
                Path target = backupDir.resolve(relativePath);

                Files.createDirectories(target.getParent());
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                // ("Error: faild to copy file: " + source + e.getMessage());
                continue;
            }
        }

        // remove old backup directory
        if ((resetbackupDir == true)
            && (removeOldBackup == true)
            && (renamedOldBackupRoot != null)
            && (Files.exists(renamedOldBackupRoot))) {
            deleteDirectoryRecursively(renamedOldBackupRoot);
        }
    }

    /**
     * copy files recursively under source directory to target directory
     * allowed following symbolic links
     * 
     * @param source           source directory
     * @param target           target directory
     * @throws IOException
     */
    public static void copyDirectory(Path source, Path target) throws IOException {
        copyDirectory(source, target, true); // followSymLinks true
    }

    /**
     * copy files recursively under source directory to target directory 
     *
     * @param source           source directory
     * @param target           target directory
     * @param followSymLinks   flag to follow symbolic link
     * @throws IOException
     */
    public static void copyDirectory(Path source, Path target, boolean followSymLinks) throws IOException {
        if (!Files.isDirectory(source)) {
            throw new IllegalArgumentException("invalid directory path: " + source);
        }

        EnumSet<FileVisitOption> options = followSymLinks
                ? EnumSet.of(FileVisitOption.FOLLOW_LINKS)
                : EnumSet.noneOf(FileVisitOption.class);

        Files.walkFileTree(
                source,
                options,
                Integer.MAX_VALUE,
                new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                            throws IOException {
                        // get relative path or "dir" by "source" path 
                        Path relative = source.relativize(dir);
                        // add relative path to "target" directory
                        Path targetDir = target.resolve(relative);
                        Files.createDirectories(targetDir);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException {
                        Path relative = source.relativize(file);
                        Path targetFile = target.resolve(relative);
                        Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc)
                            throws IOException {
                        System.err.println("failed to copy file: " + file + " - " + exc.getMessage());
                        return FileVisitResult.CONTINUE;
                    }
                });
    }

    /**
     * collect regular files from given directories, directories are traversed recursively.
     * allowed following symbolic links
     * 
     * @param dirs list of absolute path of directory
     * @return regular file list removed redundancy
     * @throws IOException
     */
    public static List<Path> collectFiles(List<Path> dirStrs) throws IOException {
        return collectFiles(dirStrs, true); // followSymLinks true
    }

    /**
     * collect regular files from given directories, directories are traversed recursively.
     *
     * @param dirs list of absolute path of directory
     * @param followSymLinks   flag to follow symbolic link
     * @return regular file list removed redundancy
     * @throws IOException
     */
    public static List<Path> collectFiles(List<Path> dirs, boolean followSymLinks) throws IllegalArgumentException, IOException {
        Set<Path> result = new HashSet<>(); // remove redundancy

        EnumSet<FileVisitOption> options = followSymLinks
                ? EnumSet.of(FileVisitOption.FOLLOW_LINKS)
                : EnumSet.noneOf(FileVisitOption.class);

        dirs.stream().forEach(dir -> { 
            if (!dir.isAbsolute()) {
                throw new IllegalArgumentException("Error: only absolute path is allowed: " + dir);
            }
        });

        for (Path dir : dirs) {
            dir = dir.toAbsolutePath().normalize();

            if (!Files.isDirectory(dir)) {
                // ("path is NOT a directory: " + dirPath);
                continue;
            }

            Files.walkFileTree(dir,
                    options,
                    Integer.MAX_VALUE,
                    new SimpleFileVisitor<>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                            if (attrs.isRegularFile()) {
                                result.add(file.toAbsolutePath().normalize());
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) {
                            // ("access denied: " + file + " - " + exc.getMessage());
                            // keep progress over this fail
                            return FileVisitResult.CONTINUE;
                        }
                    });
        }

        return result.stream().sorted().collect(Collectors.toList());
    }

    private static Path getUniqueBackupNameWithTimeSeconds(Path original, String prefix, String suffix) throws IllegalArgumentException, SecurityException {
        Path candidate;

        if (!original.isAbsolute()) {
            throw new IllegalArgumentException("absolute path is required: " + original);
        }

        if (!Files.isDirectory(original)) {
            throw new IllegalArgumentException("invalid directory path: " + original);
        }

        if (prefix == null || prefix.isEmpty()) prefix = "";
        if (suffix == null || suffix.isEmpty()) suffix = "";

        do {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            candidate = original.resolveSibling(prefix + time + suffix);
        } while (Files.exists(candidate));

        return candidate;
    }

    private static Path getUniqueBackupNameWithCount(Path original, String prefix, String suffix) throws IllegalArgumentException, SecurityException {
        int counter = 0;
        Path candidate;

        if (!original.isAbsolute()) {
            throw new IllegalArgumentException("absolute path is required: " + original);
        }

        if (!Files.isDirectory(original)) {
            throw new IllegalArgumentException("invalid directory path: " + original);
        }

        if (prefix == null || prefix.isEmpty()) prefix = "";
        if (suffix == null || suffix.isEmpty()) suffix = "";

        do {
            candidate = original.resolveSibling(prefix + counter + suffix);
            counter++;
        } while (Files.exists(candidate));

        return candidate;
    }

    private static void deleteDirectoryRecursively(Path directory) throws IOException {
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                    throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
