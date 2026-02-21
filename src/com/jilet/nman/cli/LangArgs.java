package com.jilet.nman.cli;

import com.jilet.nman.type.Lang;
import picocli.CommandLine.Command;

import java.util.Arrays;
import java.util.concurrent.Callable;


@Command(name = "lang", description = "Show possible language arguments which are acceptable by the <lang> argument of the base command.")
public class LangArgs implements Callable<Integer> {

    @Override
    public Integer call() {

        System.out.printf("%-20s | %s%n", "Language", "Aliases");
        System.out.printf("%-20s | %s%n", "-".repeat(20), "-".repeat(30));

        Arrays.stream(Lang.values()).forEach(lang -> {
            String name = lang.getAlternateNames()[0];
            String aliases = String.join(", ",
                    Arrays.copyOfRange(lang.getAlternateNames(), 1, lang.getAlternateNames().length));
            System.out.printf("%-20s | %s%n", name, aliases);
        });

        return 0;
    }

}
