package com.jilet.nman.cli;

import com.jilet.nman.adapter.LlmConnectionClient;
import com.jilet.nman.common.ExitUtil;
import com.jilet.nman.response.ResponseFormat;
import com.jilet.nman.service.ConfigurationService;
import com.jilet.nman.service.FileService;
import com.jilet.nman.service.RendererService;
import com.jilet.nman.type.Lang;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.Callable;

@Command(
        name = "nman",
        version = "nman 0.0.1",
        subcommands = {SetupArgs.class, LangArgs.class, ModelArgs.class},
        mixinStandardHelpOptions = true
)
public class BaseArgs implements Callable<Integer> {

    @Parameters(index = "0",
            arity = "0**1",
            description = "Language/Library to search and store the function. " +
                    "Also will generate the folder with the canon name of this lang/lib. Use \"nman lang\" to" +
                    "get the full list of accepted arguments.")
    String lang;

    @Parameters(index = "1",
            arity = "0**1",
            description = "Function/Method/Macro to search and generate doc for.")
    String func;

    @Option(names = {"--override", "-o"},
            description = "Whether to override the cached document with a new one")
    boolean override;

    @Option(names = {"--prompt-customize", "-p"},
            description = "Extra customization and context for document generation prompt")
    String prompt;


    @Override
    public Integer call() {
        if (lang == null || func == null) {
            new CommandLine(this).usage(System.out);
            return 0;
        }

        validateLang();

        if(override){
            Path generatedDoc = generateDocument();
            RendererService.renderDocument(generatedDoc.toString());
        }
        else{
            handleDefault();
        }

        return 0;
    }


    private void validateLang() {
        if (lang.isBlank()) {
            ExitUtil.exitWithErrorMessage("Lang argument can not be empty!");
        }

        if (Arrays.stream(Lang.values()).noneMatch(l -> Arrays.stream(l.getAlternateNames()).anyMatch(lang::equalsIgnoreCase))) {
            ExitUtil.exitWithErrorMessage("%s is not a valid supported language !", lang);
        }
    }

    private void handleDefault(){
        Path cached = getCachedDocument();
        if(cached != null){
            RendererService.renderDocument(cached.toString());
        }
        else{
            Path generatedDoc = generateDocument();
            RendererService.renderDocument(generatedDoc.toString());
        }
    }

    private Path getCachedDocument(){
        Path cachedDocPath = FileService.getFunctionDocPath(func);
        if(cachedDocPath != null){
            return cachedDocPath;
        }
        return null;
    }

    private Path generateDocument(){
        ResponseFormat formattedResponse = LlmConnectionClient.sendRequest(ConfigurationService.getProvider(), ConfigurationService.getLlmModelDescriptor(), lang, func, prompt);
        if(formattedResponse == null){
            ExitUtil.exitWithErrorMessage("Failed to get a valid response from the LLM. Please try again later.");
        }
        return FileService.createDocument(ConfigurationService.getDocumentHome().toString(), lang, func, formattedResponse);
    }



}
