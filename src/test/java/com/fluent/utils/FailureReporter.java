package com.fluent.utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FailureReporter implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        // Delete recording for passed tests — only failures are worth keeping
        String testName = context.getDisplayName().replaceAll("[^a-zA-Z0-9_-]", "_");
        String workspace = System.getenv("GITHUB_WORKSPACE");
        Path recording = workspace != null
            ? Paths.get(workspace, "evidence", testName + ".mp4")
            : Paths.get("evidence", testName + ".mp4");
        try {
            Files.deleteIfExists(recording);
        } catch (IOException e) {
            System.err.println("[FailureReporter] Could not delete recording: " + e.getMessage());
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName().replaceAll("[^a-zA-Z0-9_-]", "_");
        String workspace = System.getenv("GITHUB_WORKSPACE");
        Path failureDir = workspace != null
            ? Paths.get(workspace, "evidence", "failures")
            : Paths.get("evidence", "failures");

        try {
            Files.createDirectories(failureDir);

            String excType    = cause != null ? cause.getClass().getName() : "Unknown";
            String message    = cause != null && cause.getMessage() != null ? cause.getMessage() : "No message";
            String stackTrace = buildStackTrace(cause);

            String json = "{\n"
                + "  \"testName\": \"" + escapeJson(testName) + "\",\n"
                + "  \"displayName\": \"" + escapeJson(context.getDisplayName()) + "\",\n"
                + "  \"className\": \"" + escapeJson(context.getTestClass().map(Class::getSimpleName).orElse("")) + "\",\n"
                + "  \"exceptionType\": \"" + escapeJson(excType) + "\",\n"
                + "  \"message\": \"" + escapeJson(message) + "\",\n"
                + "  \"stackTrace\": \"" + escapeJson(stackTrace) + "\"\n"
                + "}";

            Files.write(failureDir.resolve(testName + ".json"), json.getBytes());
        } catch (IOException e) {
            System.err.println("[FailureReporter] Could not write failure report: " + e.getMessage());
        }
    }

    private String buildStackTrace(Throwable cause) {
        if (cause == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(cause.getClass().getName()).append(": ").append(cause.getMessage()).append("\n");
        for (StackTraceElement el : cause.getStackTrace()) {
            sb.append("  at ").append(el).append("\n");
            if (sb.length() > 4000) {
                sb.append("  ... (truncated)");
                break;
            }
        }
        return sb.toString();
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}
