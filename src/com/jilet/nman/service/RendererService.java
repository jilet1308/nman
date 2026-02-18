package com.jilet.nman.service;

import com.jilet.nman.common.ExitUtil;

import java.io.IOException;

public class RendererService {

    //Amazing cli application btw
    private static final String RENDERER_COMMAND = "glow";

    public static void checkRendererAvailable() {
        try {
            int process = new ProcessBuilder(RENDERER_COMMAND, "--version")
                    .inheritIO()
                    .start()
                    .waitFor();

            if(process != 0) {
                ExitUtil.exitWithErrorMessage("Renderer %s is not available. Please install it and try again.", RendererService.RENDERER_COMMAND);
            }
        } catch (IOException | InterruptedException e) {
            ExitUtil.exitWithErrorMessage("Renderer %s is not available. Please install it and try again.", RendererService.RENDERER_COMMAND);
        }
    }

    public static void renderDocument(String name) {
        try {
            new ProcessBuilder(RENDERER_COMMAND, name)
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            ExitUtil.exitWithErrorMessage("Error trying to render the file: %s", name);
        }
    }

}
