package com.jilet.nman.service;

import com.jilet.nman.common.ExitUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RendererService {

    //Amazing cli application btw
    private static final String DEFAULT_RENDERER_COMMAND = "glow";

    public static void checkRendererAvailable() {
        String command = ConfigurationService.getCustomRenderer().orElse(DEFAULT_RENDERER_COMMAND);
        try {
            ProcessBuilder pb = new ProcessBuilder(command, "--version");
            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);

            int process = pb.start().waitFor();

            if (process != 0) {
                ExitUtil.exitWithErrorMessage("Renderer %s is not available. Please install it and try again.", command);
            }
        } catch (IOException | InterruptedException e) {
            ExitUtil.exitWithErrorMessage("Renderer %s is not available. Please install it and try again.", command);
        }
    }

    public static void renderDocument(String name) {
        String command = ConfigurationService.getCustomRenderer().orElse(DEFAULT_RENDERER_COMMAND);
        try {
            new ProcessBuilder(command, name)
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            ExitUtil.exitWithErrorMessage("Error trying to render the file: %s", name);
        }
    }

    public static void parseFileContent(File file) {
        if (!file.canRead()) {
            ExitUtil.exitWithErrorMessage("File is not readable: %s", file.getAbsolutePath());
        }


        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            ExitUtil.exitWithErrorMessage("Error while reading: %s", file.getAbsolutePath());
        }


    }

}
