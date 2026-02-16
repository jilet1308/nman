package com.jilet.nman.service;

import com.jilet.nman.common.ExitUtil;
import com.jilet.nman.response.ResponseFormat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileService {

    public static final String MARKDOWN_SUFFIX = ".md";

    public static Optional<Path> getAbsolutePathOf(String name) {
        try {
            return Files.walk(ConfigurationService.getDocumentHome())
                    .filter(p -> p.getFileName().toString().equals(name))
                    .findFirst()
                    .map(Path::toAbsolutePath);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static void createDirIfNotExists(String base, String folderName) {
        var path = Path.of(base, folderName);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            ExitUtil.exitWithErrorMessage("Could not create directory: %s", path);
        }
    }

    public static Path createDocument(String base, String lang, String function, ResponseFormat response) {
        createDirIfNotExists(base, lang);

        var path = Path.of(base, lang, function + MARKDOWN_SUFFIX);
        try {
            var content = String.join("\n",
                    response.getName(),
                    response.getLibrary(),
                    response.getSynopsis(),
                    response.getDescription(),
                    response.getReturnValue(),
                    response.getNotes(),
                    response.getSeeAlso(),
                    response.getExample()
            );
            Files.writeString(path, content);
        } catch (IOException e) {
            ExitUtil.exitWithErrorMessage("Could not create the document for function: %s", function);
        }

        return path;
    }

}
