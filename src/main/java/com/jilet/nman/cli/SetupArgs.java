package com.jilet.nman.cli;

import com.jilet.nman.service.ConfigurationService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "setup",
        mixinStandardHelpOptions = true,
        description = "Alter/Setup configurations")
public class SetupArgs implements Callable<Integer> {

    @Option(names = {"--provider", "-p"},
            description = "LLM provider to generate pages nman pages. Case insensitive. " +
                    "\"Round\" arguments are also accepted. (e.g. claude, Claude, cLauDe, anthropic, AnThropic",
            defaultValue = "")
    String providerArg;

    @Option(names = {"--model", "-m"},
            description = "Model to use from the given provider",
            defaultValue = "")
    String model;

    @Option(names = {"--apiKey", "--key", "-k"},
            description = "API Key",
            defaultValue = "")
    String apiKey;

    @Option(names = {"--home", "-e"},
            description = "Where to store the generated documents",
            defaultValue = "")
    String home;

    @Override
    public Integer call() {
        if (providerArg.isBlank() && model.isBlank() && apiKey.isBlank() && home.isBlank()) {
            System.out.println("Should provide at least one option to the setup command!");
            System.exit(1);
        }

        if (!providerArg.isBlank()) {
            ConfigurationService.setProvider(providerArg);
        }
        if (!model.isBlank()) {
            ConfigurationService.setModel(model);
        }
        if (!apiKey.isBlank()) {
            ConfigurationService.setApiKey(apiKey);
        }
        if (!home.isBlank()) {
            ConfigurationService.setDocumentHome(home);
        }

        return 0;
    }

}
