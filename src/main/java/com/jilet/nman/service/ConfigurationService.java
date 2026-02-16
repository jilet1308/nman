package com.jilet.nman.service;


import com.jilet.nman.crypto.CryptoUtils;
import com.jilet.nman.type.LlmModelDescriptor;
import com.jilet.nman.type.Provider;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigurationService {
    private static final String HOME_PROPERTY = "user.home";
    private static final String DEFAULT_DOCUMENT_HOME_FOLDER_NAME = "nmanlib";
    private static final String CONFIG_FILE_NAME = ".nmancfg";
    private static final String CONFIG_KEY_VALUE_SEPARATOR = ":";
    private static final String PROVIDER_KEY = "PROVIDER";
    private static final String MODEL_KEY = "MODEL";
    private static final String API_KEY_KEY = "KEY";
    private static final String DOCUMENT_HOME_KEY = "DOC_HOME";
    public static final Path CONFIG_FILE_PATH = buildConfigDirectory();

    private static Path buildConfigDirectory() {
        String configDirectory = System.getProperty(HOME_PROPERTY);
        return Paths.get(configDirectory, CONFIG_FILE_NAME);
    }

    private static boolean configFileExists() {
        return Files.exists(CONFIG_FILE_PATH);
    }

    private static void createConfigFile() {
        try {
            Files.createFile(CONFIG_FILE_PATH);
        } catch (FileAlreadyExistsException e) {
            //Do nothing
        } catch (IOException e) {
            System.out.println("Config file could not be created.");
            System.exit(1);
        }
    }

    public static void createConfigIfNotPresent() {
        if (!configFileExists()) {
            createConfigFile();
        }
    }

    public static void createDefaultDocumentHomeIfNoneExists() {
        if (getValue(CONFIG_FILE_PATH, DOCUMENT_HOME_KEY) == null) {
            String configDirectory = System.getProperty(HOME_PROPERTY);
            try {
                Path docHome = Path.of(configDirectory, DEFAULT_DOCUMENT_HOME_FOLDER_NAME);
                Files.createDirectories(docHome);
                setDocumentHome(String.valueOf(docHome));
            } catch (IOException e) {
                System.out.println("Could not create default directory for documents. Check permissions.");
                System.exit(1);
            }
        }
    }

    public static boolean validateDocumentHomeAvailable() {
        getDocumentHome();
        return true;
    }


    private static void replaceOrAddValue(Path cfgPath, String key, String newValue) throws IOException {
        List<String> lines = Files.readAllLines(cfgPath);
        boolean found = false;

        List<String> updated = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith(key + CONFIG_KEY_VALUE_SEPARATOR)) {
                updated.add(key + CONFIG_KEY_VALUE_SEPARATOR + newValue);
                found = true;
            } else {
                updated.add(line);
            }
        }

        if (!found) {
            updated.add(key + CONFIG_KEY_VALUE_SEPARATOR + newValue);
        }

        Files.write(cfgPath, updated);
    }

    private static String getValue(Path cfgPath, String key) {
        try {
            return Files.readAllLines(cfgPath).stream()
                    .filter(line -> line.startsWith(key + CONFIG_KEY_VALUE_SEPARATOR))
                    .map(line -> line.substring(key.length() + 1))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            System.out.printf("Could not read configuration key: %s from config file: %s%n", key, cfgPath);
        }
        return null;
    }

    public static Provider getProvider() {
        String providerName = getValue(CONFIG_FILE_PATH, PROVIDER_KEY);
        if (providerName == null) {
            System.out.println("Provider name is invalid. Please set it up correctly again with the setup command.");
            System.exit(1);
        }
        return Provider.valueOf(providerName);
    }

    public static LlmModelDescriptor getLlmModelDescriptor() {
        String modelName = getValue(CONFIG_FILE_PATH, MODEL_KEY);
        if (modelName == null) {
            System.out.println("Model name is invalid. Please set it up correctly again with the setup command.");
            System.exit(1);
        }
        return LlmModelDescriptor.valueOf(modelName);
    }

    public static Path getDocumentHome() {
        String documentHome = getValue(CONFIG_FILE_PATH, DOCUMENT_HOME_KEY);
        if (documentHome == null) {
            System.out.println("Document home not set. Please set it up correctly again with the setup command.");
            System.exit(1);
        }
        Path documentHomePath = Path.of(documentHome);
        if (!Files.exists(documentHomePath) || !Files.isDirectory(documentHomePath)) {
            System.out.println("Document home path is invalid. Please set it up correctly as a folder with the setup command");
            System.exit(1);
        }
        return documentHomePath;
    }

    public static String getApiKey() {
        String apiKey = getValue(CONFIG_FILE_PATH, API_KEY_KEY);
        if (apiKey == null) {
            System.out.println("API Key is invalid. Please set it up correctly again with the setup command.");
            System.exit(1);
        }
        return CryptoUtils.decrypt(apiKey);
    }


    public static void setProvider(String providerArg) {
        Provider provider = Arrays.stream(Provider.values())
                .filter(prov -> Arrays.stream(prov.getAlternateNames()).anyMatch(alternateName -> alternateName.equalsIgnoreCase(providerArg)))
                .findFirst()
                .orElse(null);

        if (provider == null) {
            System.out.printf("Could not set the provider. Are you sure this is a valid provider? \"%s\"%n", providerArg);
            System.exit(1);
        }

        try {
            replaceOrAddValue(CONFIG_FILE_PATH, PROVIDER_KEY, provider.name());
        } catch (IOException e) {
            System.out.println("Failed to set provider.");
            System.exit(1);
        }
    }

    public static void setModel(String modelArg) {
        LlmModelDescriptor descriptor = Arrays.stream(LlmModelDescriptor.values())
                .filter(desc -> Arrays.stream(desc.getAlternateNames()).anyMatch(alternateName -> alternateName.equalsIgnoreCase(modelArg)))
                .findFirst()
                .orElse(null);

        if (descriptor == null) {
            System.out.printf("Could not set the model. Are you sure this is a valid model? \"%s\"%n", modelArg);
            System.exit(1);
        }

        try {
            replaceOrAddValue(CONFIG_FILE_PATH, MODEL_KEY, descriptor.name());
        } catch (IOException e) {
            System.out.println("Failed to set model.");
            System.exit(1);
        }
    }

    public static void setApiKey(String apiKey) {
        if (apiKey == null) {
            System.out.println("API key can not be empty!");
            System.exit(1);
        }

        String encrypted = CryptoUtils.encrypt(apiKey);
        try {
            replaceOrAddValue(CONFIG_FILE_PATH, API_KEY_KEY, encrypted);
        } catch (IOException e) {
            System.out.println("Could not set up the API key.");
            System.exit(1);
        }
    }

    public static void setDocumentHome(String documentHomeArg) {
        try {
            replaceOrAddValue(CONFIG_FILE_PATH, DOCUMENT_HOME_KEY, documentHomeArg);
        } catch (IOException e) {
            System.out.println("Could not set up document home.");
            System.exit(1);
        }
    }


}
