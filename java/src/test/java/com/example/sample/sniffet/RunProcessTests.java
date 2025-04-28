package com.example.sample.sniffet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunProcessTests {

    @Test
    public void run() {
        runProcess(false, "");
    }

    public int runProcess(boolean async, String... args) {
        int exitcode = 255;
        String command = "echo hello world";
        log.info("## command=[{}]", command);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            Process process = processBuilder.start();

            if (async) {
                exitcode = 0;
            } else {
                // read stdout
                BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = outputReader.readLine()) != null) {
                    log.info("STDOUT: " + line);
                }

                // read stderr
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((line = errorReader.readLine()) != null) {
                    log.info("STDERR: " + line);
                }

                // wait until process is exited
                exitcode = process.waitFor();
                log.info("exitcode="+exitcode);
        
                if (exitcode != 0) {
                    log.error("Error: failed to run process");
                }        
            }
        } catch (Exception e) {
            log.error("Exception: failed to run process", e);
        }

        return exitcode;
    }
}