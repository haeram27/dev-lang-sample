package com.example.sample.exam;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.example.sample.EvaluatedTimeTests;

public class PathTests extends EvaluatedTimeTests {

    /**
     * Path is object to handle logical path, this supports manipulation of file path
     * File is object to handle physical path, this supports actions in file system.
     * three type of path string:
     *   Absolute path: this is path expression start from root('/')
     *   Canonical path: this is absolute path does NOT contain dot expressions(. or ..)
     *   Relative path: this is path expression does NOT start from root('/')
     */

    String cwd = System.getProperty(("user.dir"));
    String linAbsolutePathStr = "/usr/bin/ls";
    String winAbsolutePathStr = "C:/Program Files/Microsoft";
    String relativePathStr = "hello/open/.././world";
    Path pCwd = Path.of(cwd);
    Path pLinAbsolute = Path.of(linAbsolutePathStr);
    Path pWinAbsolute = Path.of(winAbsolutePathStr);
    Path pRelative = Path.of(relativePathStr);

    @Test
    void toAbsoluteTest() {
        /**
         * toAbsolutePath() retuns absolute path
         * if Path is absolute path returns as it is
         * if Path is Relative path then add prefix cwd from root to head of path value
         */
        System.out.println("### toAbsolute() =============================================");
        System.out.println("cwd raw: " + System.getProperty(("user.dir")));
        System.out.println("cwd: " + pCwd.toAbsolutePath());
        System.out.println("linAbsolutePathStr: " + pLinAbsolute.toAbsolutePath());
        System.out.println("winAbsolutePathStr: " + pWinAbsolute.toAbsolutePath());
        System.out.println("RelativePathStr: " + pRelative.toAbsolutePath());
        /*
            ### toAbsolute() =============================================
            cwd: /home/swvm/src/dev-lang-sample/java
            linAbsolutePathStr: /usr/bin/ls
            winAbsolutePathStr: /home/swvm/src/dev-lang-sample/java/C:/Program Files/Microsoft
            RelativePathStr: /home/swvm/src/dev-lang-sample/java/hello/open/.././world
        */
    }

    @Test
    void normalizeTest() {
        /**
         * normalize()
         * remove dot expression( . or .. ) in path
         * !!!!!! toAbsolutePath().normalize() makes 'CANONICAL' path
         */
        System.out.println("### normalize() =============================================");
        System.out.println("cwd: " + pCwd.toAbsolutePath().normalize());
        System.out.println("linAbsolutePathStr: " + pLinAbsolute.toAbsolutePath().normalize());
        System.out.println("winAbsolutePathStr: " + pWinAbsolute.toAbsolutePath().normalize());
        System.out.println("RelativePathStr: " + pRelative.toAbsolutePath().normalize());
    }

    @Test
    void getRootTest() {
        /**
         * getRoot() returns '/' char if Path has it as first char, if NOT returns null
         * getRoot() is valid only for LINUX OS, do NOT use with WINDOWS ABSOLUTE Path
         */
        System.out.println("### getRoot() =============================================");
        System.out.println("cwd: " + pCwd.getRoot());
        System.out.println("linAbsolutePathStr: " + pLinAbsolute.getRoot());
        System.out.println("winAbsolutePathStr: " + pWinAbsolute.getRoot());
        System.out.println("RelativePathStr: " + pRelative.getRoot());
    }

    @Test
    void relativizeTest() {
        /**
         * relativize() resolve relative path between TWO path
         * !! Strongly recommned, use this between TWO Absolute Path
         * As a two argument, use only "ABSOLUTE" path, operation to get relative path can be resolved nomally
         * For "RELATIVE" path, use "toAbsolutePath()"" before use relativeze()
         */
        System.out.println("### getRoot() =============================================");
        System.out.println("cwd: " + pCwd.getRoot().relativize(pCwd));
        System.out.println("linAbsolutePathStr: " + pLinAbsolute.getRoot().relativize(pLinAbsolute));
        System.out.println(
                "RelativePathStr: " + pRelative.toAbsolutePath().getRoot().relativize(pRelative.toAbsolutePath()));
        // System.out.println("winAbsolutePathStr: " + pWinAbsolute.toAbsolutePath().getRoot().relativize(pWinAbsolute)); // Error
    }


    @Test
    void resolveTest() {
        /**
         * resolve() add path as prefix against base Path object
         */
        System.out.println("### getRoot() =============================================");
        System.out.println("cwd: " + pCwd.toString());                     // /home/swvm/src/dev-lang-sample/java
        System.out.println("cwd: " + pCwd.resolve("resolve/test"));  // /home/swvm/src/dev-lang-sample/java/resolve/test
        System.out.println("cwd: " + pCwd.resolve("/resolve/test")); // /resolve/test
        System.out.println("cwd: " + pCwd.resolveSibling("/test"));  // /test
        System.out.println("cwd: " + pCwd.resolveSibling("test"));   // /home/swvm/src/dev-lang-sample/test
    }

}
