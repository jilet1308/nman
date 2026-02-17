package com.jilet.nman.common;

public class ExitUtil {

    public static void exitWithErrorMessage(String formattedMessage, Object... args) {
        System.err.printf(formattedMessage + "%n", args);
        System.exit(1);
    }

}
