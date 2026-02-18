package com.jilet.nman.adapter;


import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import com.anthropic.models.messages.StructuredMessageCreateParams;
import com.jilet.nman.common.ExitUtil;
import com.jilet.nman.response.ResponseFormat;
import com.jilet.nman.service.ConfigurationService;
import com.jilet.nman.type.LlmModelDescriptor;
import com.jilet.nman.type.Provider;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.StructuredChatCompletionCreateParams;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class LlmConnectionClient {

    private static final String BASE_PROMPT = "Write like a man page. Write sections in markdown.";

    public static ResponseFormat sendRequest(Provider provider,
                                   LlmModelDescriptor model,
                                   String lang,
                                   String func,
                                   String promptExtras) {

        Object actualModel = LlmModel.MODELS.get(model);

        if (provider == Provider.OpenAI) {
            return parseDotsWhileExecuting(
                    CompletableFuture.supplyAsync(() -> doHandleOpenAI((ChatModel) (actualModel), lang, func, promptExtras)));
        }

        if (provider == Provider.Anthropic) {
            return parseDotsWhileExecuting(
                    CompletableFuture.supplyAsync(() -> doHandleAnthropic((Model) (actualModel), lang, func, promptExtras)));
        }

        return null;
    }


    private static String combinedMessageOf(String lang, String func, String promptExtras) {
        return BASE_PROMPT + "(" + lang + ":" + func + ") <extras>" + promptExtras + "</extras>";
    }

    private static ResponseFormat doHandleOpenAI(ChatModel actualModel, String lang, String func, String promptExtras) {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(ConfigurationService.getApiKey())
                .build();

        StructuredChatCompletionCreateParams<ResponseFormat> params = ChatCompletionCreateParams.builder()
                .maxCompletionTokens(ConfigurationService.getMaxTokens())
                .addUserMessage(combinedMessageOf(lang, func, promptExtras))
                .model(actualModel)
                .responseFormat(ResponseFormat.class)
                .build();

        var response = client.chat().completions().create(params).choices().getFirst().message().content().orElse(null);

        if(response == null){
            ExitUtil.exitWithErrorMessage("Invalid response from the LLM. Please try again later.");
        }

        return response;
    }

    private static ResponseFormat doHandleAnthropic(Model actualModel, String lang, String func, String promptExtras) {
        AnthropicClient client = AnthropicOkHttpClient.builder()
                .apiKey(ConfigurationService.getApiKey())
                .build();

        StructuredMessageCreateParams<ResponseFormat> params = MessageCreateParams.builder()
                .maxTokens(ConfigurationService.getMaxTokens())
                .addUserMessage(combinedMessageOf(lang, func, promptExtras))
                .model(actualModel)
                .outputConfig(ResponseFormat.class)
                .build();

        var response = client.messages().create(params).content().getFirst().text().orElse(null);

        if (response == null || !response.isValid()) {
            ExitUtil.exitWithErrorMessage("Invalid response from the LLM. Please try again later.");
        }

        return response.text();
    }

    private static ResponseFormat parseDotsWhileExecuting(CompletableFuture<ResponseFormat> future){
        System.out.println("Generating document.");
        long start = System.currentTimeMillis();

        while (!future.isDone()){
            long elapsed = (System.currentTimeMillis() - start) / 1000;
            if(elapsed > 1){
                System.out.print(".");
                start = System.currentTimeMillis();
            }
        }

        try{
            return future.get();
        }
        catch (ExecutionException | InterruptedException e){
            ExitUtil.exitWithErrorMessage("Error while generating the document. Please try again later.");
        }

        return null;
    }
}
