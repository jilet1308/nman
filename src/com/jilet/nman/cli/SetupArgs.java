package com.jilet.nman.cli;

import com.jilet.nman.common.ExitUtil;
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

    @Option(names = {"--maxTokens", "-t"},
            description = "Max tokens to use when generating documents (default: 8192)",
            defaultValue = "")
    String maxTokens;

    @Override
    public Integer call() {
        if (providerArg.isBlank() && model.isBlank() && apiKey.isBlank() && home.isBlank() && maxTokens.isBlank()) {
            ExitUtil.exitWithErrorMessage("Should provide at least one option to the setup command!");
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
        if(!maxTokens.isBlank()){
            ConfigurationService.setMaxTokens(maxTokens);
        }

        return 0;
    }

}
