package com.jilet.nman.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Provider {
    Anthropic(new LlmModelDescriptor[]{
        LlmModelDescriptor.OPUS_4_6,
        LlmModelDescriptor.SONNET_4_5,
        LlmModelDescriptor.HAIKU_4_5
    }),
    OpenAI(new LlmModelDescriptor[]{
        LlmModelDescriptor.GPT_5_2,
        LlmModelDescriptor.GPT_MINI_5,
        LlmModelDescriptor.GPT_NANO_5
    });

    @Getter
    private final LlmModelDescriptor[] models;
}
