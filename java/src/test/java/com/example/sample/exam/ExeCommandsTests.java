package com.example.sample.exam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.example.sample.EvaluatedTimeTests;

public class ExeCommandsTests extends EvaluatedTimeTests {

    @Test
    void exeCommandsOutputTest() throws IOException, InterruptedException {
        String world = "world";
        Process p = Runtime.getRuntime().exec(new String[] { "echo", "hello", world });
        System.out.println("pid is " + p.pid());

        // blocking current thread by calling Process.waitFor()
        // calling Process.waitFor() is necessary to get the output of the process
        // but not mandatory
        boolean isSuccess = (0 == p.waitFor());
        if (isSuccess) {
            System.out.println("Process executed successfully.");
        } else {

            System.err.println("Process execution failed.");
        }

        String output = new String(p.getInputStream().readAllBytes());
        assertEquals("hello world\n", output);
    }
}
