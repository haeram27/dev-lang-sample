package com.example.sample.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Base64;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FileUtil {

    private FileUtil() {
    }

    public enum FileMetaType {
        IMAGE, COMPRESS, TXT, XML
    }

    /**
     * readFile
     * 
     * @param file file
     * @return
     * @throws IOException
     */
    public static byte[] readFile(String file) throws IOException {

        byte[] readFile = null;
        try (FileInputStream inStream = new FileInputStream(file);
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int read;
            while ((read = inStream.read(buffer)) != -1) {
                byteOut.write(buffer, 0, read);
            }
            byteOut.flush();

            readFile = byteOut.toByteArray();
        }

        return readFile;
    }

    /**
     * 
     * @param file
     * @param off
     * @param len
     * @return
     * @throws IOException
     */
    public static byte[] readFile(String file, int off, int len) throws IOException {

        byte[] readFile = null;
        try (FileInputStream inStream = new FileInputStream(file);
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {

            readFile = new byte[len];

            inStream.read(readFile, off, len);
        }

        return readFile;
    }

    /**
     * readFileBase64Encode
     * 
     * @param fileFullPath fileFullPath
     * @return
     * @throws IOException IOException
     */
    public static String readFileBase64Encode(String fileFullPath) throws IOException {
        return Base64.getEncoder().encodeToString(readFile(fileFullPath));
    }

    /**
     * readFileBase64Encode
     * 
     * @param file file
     * @param off off
     * @param len len
     * @return
     * @throws IOException
     */
    public static String readFileBase64Encode(String file, int off, int len) throws IOException {
        return Base64.getEncoder().encodeToString(readFile(file, off, len));
    }

    /**
     * 파일복사
     * 
     * @param inputFileName 복사할 대상파일이름
     * @param outputFileName 복사될 파일이름
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copyIo(String inputFileName, String outputFileName)
            throws FileNotFoundException, IOException {
        try (
                BufferedInputStream bufferInStream = new BufferedInputStream(new FileInputStream(inputFileName));
                BufferedOutputStream bufferOutStream = new BufferedOutputStream(new FileOutputStream(outputFileName))) {
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = bufferInStream.read(buffer, 0, 1024)) != -1) {
                bufferOutStream.write(buffer, 0, read);
            }

        }
    }

    /**
     * createDirectory 디렉토리 생성.
     * 
     * @param directory 디렉토리경로
     */
    public static void createDirectory(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 
     * @param outputFileName
     * @param output
     * @throws IOException
     */
    public static void writeFile(String outputFileName, byte[] output) throws IOException {
        try (BufferedOutputStream bufOut = new BufferedOutputStream(new FileOutputStream(outputFileName))) {
            bufOut.write(output);
            bufOut.flush();
        }
    }

    /**
     * 
     * @param srcFile
     * @param tarFile
     * @return
     */
    public static boolean moveFile(String srcFile, String tarFile) {
        File src = new File(srcFile);
        if (src.isFile()) {
            File tar = new File(tarFile);

            File parent = new File(tar.getParent());
            if (!parent.isDirectory()) {
                log.info("Make directory : " + parent.getAbsolutePath());
                parent.mkdirs();
            }

            return src.renameTo(tar);
        }
        return false;
    }

    /**
     * 파일 타입 조회
     * 
     * @param filePath filePath
     * @return String
     * @throws IOException
     */
    public static String getFileType(String filePath) throws IOException {
        return new Tika().detect(new File(filePath));
    }

    /**
     * 파일타입 체크
     * 
     * @param filePath filePath
     * @param fileMetaType fileMetaType
     * @return boolean
     * @throws IOException
     */
    public static boolean checkFileType(String filePath, FileMetaType fileMetaType)
            throws IOException {
        boolean isCheckFileType = false;
        String fileType = getFileType(filePath);
        switch (fileMetaType) {
            case IMAGE:
                if (fileType.indexOf("image") > -1) {
                    isCheckFileType = true;
                }
                break;
            case COMPRESS:
                if (fileType.indexOf("zip") > -1) {
                    isCheckFileType = true;
                }
                break;
            case TXT:
                if (fileType.indexOf("plain") > -1) {
                    isCheckFileType = true;
                }
                break;
            case XML:
                if (fileType.indexOf("xml") > -1) {
                    isCheckFileType = true;
                }
                break;
            default:
                break;
        }
        log.debug("fileType[" + fileType + "], fileMetaType[" + fileMetaType + "], Check["
                + isCheckFileType + "]");
        return isCheckFileType;
    }

    /**
     * deleteFiles
     * 
     * @param filePath filePath
     */
    public static void deleteFiles(String filePath) {
        deleteFiles(new File(filePath));
    }

    /**
     * deleteFiles
     * 
     * @param targetDir targetDir
     */
    public static void deleteFiles(File targetDir) {
        File[] deleteFiles = targetDir.listFiles();
        if (deleteFiles.length > 0) {
            for (int i = 0; i < deleteFiles.length; i++) {
                if (deleteFiles[i].isFile()) {
                    deleteFiles[i].delete();
                } else if (deleteFiles[i].isDirectory()) {
                    deleteFiles(deleteFiles[i].getPath());
                }
            }
            targetDir.delete();
        }
    }

    public static void deleteDirectory(String rootDir) throws Exception {
        deleteDirectory(new File(rootDir));
    }

    public static void deleteDirectory(File rootDir) throws Exception {
        deleteDirectory(rootDir.toPath());
    }

    private static void deleteDirectory(Path rootDir) throws Exception {
        if (Files.notExists(rootDir)) {
            return;
        }

        Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void transferTo(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[4 * 1024];
        int nread = 0;

        // transferTo
        do {
            nread = is.read(buffer);
            if (nread >= 0) {
                os.write(buffer, 0, nread);
            } else {
                break;
            }
        } while (nread > 0);
    }

    public static void compressZip(File dest, File... source) throws Exception {
        if (CollectionUtil.isEmpty(source)) {
            return;
        }

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dest))) {
            for (File s : source) {
                try (FileInputStream fis = new FileInputStream(s)) {
                    zos.putNextEntry(new ZipEntry(s.getName()));

                    int len = 0;
                    byte[] buf = new byte[1024];

                    while ((len = fis.read(buf)) > 0) {
                        zos.write(buf, 0, len);
                    }

                    zos.closeEntry();
                }
            }
        }
    }

    public static void decompressZip(String dest, File... source) throws Exception {
        if (CollectionUtil.isEmpty(source)) {
            return;
        }

        for (File f : source) {
            if (!checkFileType(f.getAbsolutePath(), FileMetaType.COMPRESS)) {
                continue;
            }

            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(f))) {
                ZipEntry entry = null;
                while ((entry = zis.getNextEntry()) != null) {
                    Path target = Paths.get(dest, entry.getName());

                    if (entry.isDirectory()) {
                        Files.createDirectories(target);
                    } else {
                        try (FileOutputStream fos = new FileOutputStream(target.toFile())) {
                            int len = 0;
                            byte[] buf = new byte[1024];

                            while ((len = zis.read(buf)) > 0) {
                                fos.write(buf, 0, len);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * tar.gz 압축하기
     * 
     * @param source
     * @param dest
     * @throws Exception
     */
    public static void compressTarGzip(File source, File dest) throws Exception {
        if (!source.isDirectory()) {
            throw new IllegalStateException("source must be directory");
        }

        compressTarGzip(source.toPath(), dest);
    }

    private static void compressTarGzip(Path source, File dest) throws Exception {
        try (GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(new FileOutputStream(dest));
                TarArchiveOutputStream tarOut = new TarArchiveOutputStream(gzOut)) {

            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (attrs.isSymbolicLink()) {
                        return FileVisitResult.CONTINUE;
                    }

                    Path targetFile = source.relativize(file);
                    try {

                        TarArchiveEntry entry = new TarArchiveEntry(file.toFile(), targetFile.toString());
                        entry.setSize(file.toFile().length());

                        tarOut.putArchiveEntry(entry);

                        Files.copy(file, tarOut);

                        tarOut.closeArchiveEntry();
                    } catch (Exception ee) {

                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    log.error("## fail compress tar.gz file : {} / error : {}", file, exc);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * tar.gz 파일 압축해제
     * @param source
     * @param targetPath
     */
    public static void decompressTarGzip(File source, Path targetPath) {
        try (FileInputStream fis = new FileInputStream(source);
                BufferedInputStream bis = new BufferedInputStream(fis);
                GzipCompressorInputStream gcis = new GzipCompressorInputStream(bis);
                TarArchiveInputStream tais = new TarArchiveInputStream(gcis);) {

            TarArchiveEntry entry = null;

            while ((entry = (TarArchiveEntry) tais.getNextEntry()) != null) {
                if (false == tais.canReadEntryData(entry)) {
                    continue;
                }

                File out = new File(targetPath.toFile(), entry.getName());
                File directory = out.getParentFile();

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                try (FileOutputStream os = new FileOutputStream(out)) {
                    IOUtils.copy(tais, os);
                }
            }
        } catch (Exception e) {
            log.error("## fail decompress tar.gz file : {} / error : {}", source.getAbsolutePath(), e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void decompressTarGzip(File source, File dest) {
        if (source.isDirectory()) {
            throw new IllegalStateException("not allow directory for source");
        }

        if (!dest.exists()) {
            dest.mkdirs();
        }

        decompressTarGzip(source, dest.toPath());
    }

    /**
     * 디렉터리 내 파일들을 복사함
     * @param sourceDirectory : 원본 디렉터리
     * @param targetDirectory : 복사 대상 디렉터리
     */
    public static void copyFiles(String sourceDirectory, String targetDirectory) {
        try (Stream<Path> sourcePaths = Files.walk(Paths.get(sourceDirectory))) {
            sourcePaths.forEach(sourcePath -> {
                Path targetPath = Paths.get(targetDirectory, sourcePath.toString().substring(sourceDirectory.length()));
                try {
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    log.error("## fail copy each file : from {} to {}", sourcePath, targetPath);
                }
            });
        } catch (Exception e) {
            log.error("## fail copy files : from {} to {}", sourceDirectory, targetDirectory);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 파일 확장자명 가져오기
     * 
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return null;
        }

        int idx = fileName.lastIndexOf(".");

        if (idx < 0) {
            return "";
        }

        return fileName.substring(idx + 1);
    }
}