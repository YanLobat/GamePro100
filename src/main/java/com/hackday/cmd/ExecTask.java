package com.hackday.cmd;


import com.hackday.results.TaskResult;
import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class ExecTask {

    public static TaskResult execTask(final String path) {
        // todo: do it it new Thread
        final String name = "Main";
        final TaskResult result = new TaskResult();
        val absolutePath = Paths.get("").toAbsolutePath() + "\\" + path;
        try {
            final List<String> commands = new ArrayList<String>() {{
                add("cd " + absolutePath);
                add("javac -cp " + absolutePath + "\\. " + absolutePath + "\\" + name + ".java");
                add("java -cp " + absolutePath + "\\. " + name);
            }};
            final Process p = Runtime.getRuntime().exec("cmd /c " + String.join(" & ", commands));
            p.waitFor();

            result.text = getFromStream(p.getInputStream());
            result.status = TaskResult.Status.COMPLETED;

            final String stringError = getErrorsFromStream(p.getErrorStream());
            if (stringError.length() > 0) {
                result.text = stringError;
                result.status = TaskResult.Status.ERROR;
            }

            p.destroy();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("error create task");
        }
        return result;
    }


    private static String getFromStream(final InputStream inputStream) throws IOException {

        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream)
        );

        final StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    private static String getErrorsFromStream(final InputStream inputStream) throws IOException {

        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream)
        );
        final StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append("\n");
        }
        return buffer.toString();
    }
}
