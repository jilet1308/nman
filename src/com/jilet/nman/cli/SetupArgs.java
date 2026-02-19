package com.jilet.nman.cli;

import com.jilet.nman.service.ConfigurationService;
import com.jilet.nman.service.RendererService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "setup",
        mixinStandardHelpOptions = true,
        description = "Alter/Setup/View configurations. Shows current configuration if no options provided.")
public class SetupArgs implements Callable<Integer> {

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

    @Option(names = {"--customRenderer", "-c"},
            description = "Custom command to render the generated documents (default: glow)",
            defaultValue = "")
    String customRenderer;

    @Override
    public Integer call() {
        if (model.isBlank() && apiKey.isBlank() && home.isBlank() && maxTokens.isBlank() && customRenderer.isBlank()) {
            parseCurrentConfig();
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
        if (!maxTokens.isBlank()) {
            ConfigurationService.setMaxTokens(maxTokens);
        }
        if (!customRenderer.isBlank()) {
            ConfigurationService.setCustomRenderer(customRenderer);
        }

        return 0;
    }

    private void parseCurrentConfig() {
        RendererService.parseFileContent(ConfigurationService.CONFIG_FILE_PATH.toFile());
    }

}
