package com.jilet.nman.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Provider {

    // alternateNames[0] is the canon name for the provider
    Anthropic(new String[]{"Anthropic", "claude"}),
    OpenAI(new String[]{"OpenAI", "chatgpt", "gpt"});

    @Getter
    private final String[] alternateNames;

}
