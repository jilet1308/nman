package com.jilet.nman.service;


import com.jilet.nman.common.ExitUtil;
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
    private static final Long DEFAULT_MAX_TOKENS = 8192L;
    private static final String DEFAULT_DOCUMENT_HOME_FOLDER_NAME = "nmanlib";
    private static final String CONFIG_FILE_NAME = ".nmancfg";
    private static final String CONFIG_KEY_VALUE_SEPARATOR = ":";
    private static final String PROVIDER_KEY = "PROVIDER";
    private static final String MODEL_KEY = "MODEL";
    private static final String API_KEY_KEY = "KEY";
    private static final String DOCUMENT_HOME_KEY = "DOC_HOME";
    private static final String MAX_TOKENS_KEY = "MAX_TOKENS";
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
            ExitUtil.exitWithErrorMessage("Config file could not be created.");
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
                ExitUtil.exitWithErrorMessage("Could not create default directory for documents. Check permissions.");
            }
        }
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
            ExitUtil.exitWithErrorMessage("Could not read configuration key: %s from config file: %s", key, cfgPath);
        }
        return null;
    }

    public static Provider getProvider() {
        String providerName = getValue(CONFIG_FILE_PATH, PROVIDER_KEY);
        if (providerName == null) {
            ExitUtil.exitWithErrorMessage("Provider name is invalid. Please set it up correctly again with the setup command.");
        }
        return Provider.valueOf(providerName);
    }

    public static LlmModelDescriptor getLlmModelDescriptor() {
        String modelName = getValue(CONFIG_FILE_PATH, MODEL_KEY);
        if (modelName == null) {
            ExitUtil.exitWithErrorMessage("Model name is invalid. Please set it up correctly again with the setup command.");
        }
        return LlmModelDescriptor.valueOf(modelName);
    }

    public static Long getMaxTokens() {
        String maxTokensStr = getValue(CONFIG_FILE_PATH, MAX_TOKENS_KEY);
        if (maxTokensStr == null) {
            return DEFAULT_MAX_TOKENS; // default value
        }
        try {
            return Long.parseLong(maxTokensStr);
        } catch (NumberFormatException e) {
            ExitUtil.exitWithErrorMessage("Max tokens value is invalid. Please set it up correctly again with the setup command.");
            return null;
        }
    }

    public static Path getDocumentHome() {
        String documentHome = getValue(CONFIG_FILE_PATH, DOCUMENT_HOME_KEY);
        if (documentHome == null) {
            ExitUtil.exitWithErrorMessage("Document home not set. Please set it up correctly again with the setup command.");
        }
        Path documentHomePath = Path.of(documentHome);
        if (!Files.exists(documentHomePath) || !Files.isDirectory(documentHomePath)) {
            ExitUtil.exitWithErrorMessage("Document home path is invalid. Please set it up correctly as a folder with the setup command");
        }
        return documentHomePath;
    }

    public static String getApiKey() {
        String apiKey = getValue(CONFIG_FILE_PATH, API_KEY_KEY);
        if (apiKey == null) {
            ExitUtil.exitWithErrorMessage("API Key is invalid. Please set it up correctly again with the setup command.");
        }
        return CryptoUtils.decrypt(apiKey);
    }

    private static void setProvider(LlmModelDescriptor childModel) {
        Provider provider = Arrays.stream(Provider.values())
                .filter(p -> Arrays.asList(p.getModels()).contains(childModel))
                .findFirst()
                .orElse(null);

        if(provider == null){
            ExitUtil.exitWithErrorMessage("Could not infer provider from model. Please set up the model correctly again with the setup command.");
        }

        try {
            replaceOrAddValue(CONFIG_FILE_PATH, PROVIDER_KEY, provider.name());
        } catch (IOException e) {
            ExitUtil.exitWithErrorMessage("Failed to set provider.");
        }
    }

    public static void setModel(String modelArg) {
        LlmModelDescriptor descriptor = Arrays.stream(LlmModelDescriptor.values())
                .filter(desc -> Arrays.stream(desc.getAlternateNames()).anyMatch(alternateName -> alternateName.equalsIgnoreCase(modelArg)))
                .findFirst()
                .orElse(null);

        if (descriptor == null) {
            ExitUtil.exitWithErrorMessage("Could not set the model. Are you sure this is a valid model? \"%s\"%n", modelArg);
        }

        try {
            replaceOrAddValue(CONFIG_FILE_PATH, MODEL_KEY, descriptor.name());
            setProvider(descriptor);
        } catch (IOException e) {
            ExitUtil.exitWithErrorMessage("Failed to set model.");
        }
    }

    public static void setApiKey(String apiKey) {
        if (apiKey == null) {
            ExitUtil.exitWithErrorMessage("API key can not be empty!");
        }

        String encrypted = CryptoUtils.encrypt(apiKey);
        try {
            replaceOrAddValue(CONFIG_FILE_PATH, API_KEY_KEY, encrypted);
        } catch (IOException e) {
            ExitUtil.exitWithErrorMessage("Could not set up the API key.");
        }
    }

    public static void setDocumentHome(String documentHomeArg) {
        try {
            replaceOrAddValue(CONFIG_FILE_PATH, DOCUMENT_HOME_KEY, documentHomeArg);
        } catch (IOException e) {
            ExitUtil.exitWithErrorMessage("Could not set up document home.");
        }
    }

    public static void setMaxTokens(String maxTokensArg){
        try{
            replaceOrAddValue(CONFIG_FILE_PATH, MAX_TOKENS_KEY, maxTokensArg);
        }
        catch (IOException e){
            ExitUtil.exitWithErrorMessage("Could not set up max tokens.");
        }
    }


}
