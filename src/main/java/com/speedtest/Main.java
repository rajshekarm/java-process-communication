package com.speedtest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public final class Main {
    private static final String CHILD_MODE = "child";
    private static final String PING = "hello-from-parent";
    private static final String PONG_PREFIX = "ack:";

    private Main() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && CHILD_MODE.equalsIgnoreCase(args[0])) {
            runChild();
            return;
        }

        runParent();
    }

    private static void runParent() throws Exception {
        Process child = startChildProcess();

        try (
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(child.getOutputStream(), StandardCharsets.UTF_8));
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(child.getInputStream(), StandardCharsets.UTF_8))
        ) {
            writer.write(PING);
            writer.newLine();
            writer.flush();

            String response = reader.readLine();
            if (!PONG_PREFIX.concat(PING).equals(response)) {
                throw new IllegalStateException("unexpected response: " + response);
            }

            System.out.println("Parent received: " + response);
        }

        int exitCode = child.waitFor();
        if (exitCode != 0) {
            throw new IllegalStateException("child process failed with exit code " + exitCode);
        }

        System.out.println("Two-process communication succeeded.");
    }

    private static void runChild() throws IOException {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in, StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(System.out, StandardCharsets.UTF_8))
        ) {
            String message = reader.readLine();
            if (message == null) {
                return;
            }

            writer.write(PONG_PREFIX + message);
            writer.newLine();
            writer.flush();
        }
    }

    private static Process startChildProcess() throws IOException {
        String javaExecutable = resolveJavaExecutable();
        String classpath = System.getProperty("java.class.path");
        String mainClass = Main.class.getName();

        ProcessBuilder builder = new ProcessBuilder(
                javaExecutable,
                "-cp",
                classpath,
                mainClass,
                CHILD_MODE
        );
        return builder.start();
    }

    private static String resolveJavaExecutable() {
        String javaHome = System.getProperty("java.home");
        String executable = isWindows() ? "java.exe" : "java";
        return javaHome + java.io.File.separator + "bin" + java.io.File.separator + executable;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}

