package com.jilet;


import com.jilet.nman.cli.BaseArgs;
import com.jilet.nman.service.ConfigurationService;
import picocli.CommandLine;

public class NmanApplication {

    public static void main(String[] args) {
        ConfigurationService.createConfigIfNotPresent();
        ConfigurationService.createDefaultDocumentHomeIfNoneExists();

        new CommandLine(new BaseArgs()).execute(args);
    }
}
