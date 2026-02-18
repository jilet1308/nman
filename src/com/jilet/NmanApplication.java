package com.jilet;


import com.jilet.nman.cli.BaseArgs;
import com.jilet.nman.service.ConfigurationService;
import com.jilet.nman.service.RendererService;
import picocli.CommandLine;

public class NmanApplication {

    public static void main(String[] args) {
        ConfigurationService.createConfigIfNotPresent();
        ConfigurationService.createDefaultDocumentHomeIfNoneExists();
        RendererService.checkRendererAvailable();

        new CommandLine(new BaseArgs()).execute(args);
    }
}
