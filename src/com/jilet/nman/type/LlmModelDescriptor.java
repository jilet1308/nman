package com.jilet.nman.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LlmModelDescriptor {

    //Anthropic-Claude
    OPUS_4_6(new String[]{"Claude-Opus-4.6", "Opus-4.6"}),
    SONNET_4_5(new String[]{"Claude-Sonnet-4.5", "Sonnet-4.5"}),
    HAIKU_4_5(new String[]{"Claude-Haiku-4.5", "Haiku-4.5"}),

    //OpenAI-ChatGPT
    GPT_5_2(new String[]{"ChatGPT-5.2", "GPT-5.2"}),
    GPT_MINI_5(new String[]{"ChatGPT-Mini-5", "GPT-Mini-5"}),
    GPT_NANO_5(new String[]{"ChatGPT-Nano-5", "GPT-Nano-5"});

    @Getter
    private final String[] alternateNames;

}
