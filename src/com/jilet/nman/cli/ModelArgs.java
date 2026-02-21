package com.jilet.nman.cli;

import picocli.CommandLine.Command;

import java.util.Arrays;
import java.util.concurrent.Callable;

@Command(name = "models", description = "Show model providers and their models which are applicable as the <model> argument for the setup command.")
public class ModelArgs implements Callable<Integer> {

    @Override
    public Integer call() {
        System.out.printf("%-20s | %s%n", "Provider", "Models (with alternate names)");
        System.out.printf("%-20s | %s%n", "-".repeat(20), "-".repeat(50));

        for (var provider : com.jilet.nman.type.Provider.values()) {
            String providerName = provider.name();
            String models = Arrays.stream(provider.getModels())
                    .map(model -> String.join(" (", model.getAlternateNames()) + ")")
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            System.out.printf("%-20s | %s%n", providerName, models);
        }

        return 0;
    }

}
