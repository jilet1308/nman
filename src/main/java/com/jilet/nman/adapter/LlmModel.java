package com.jilet.nman.adapter;

import com.anthropic.models.messages.Model;
import com.jilet.nman.type.LlmModelDescriptor;
import com.openai.models.ChatModel;

import java.util.Map;

public interface LlmModel {
    Map<LlmModelDescriptor, Object> MODELS = Map.of(
            LlmModelDescriptor.SONNET_4_5, Model.CLAUDE_SONNET_4_5,
            LlmModelDescriptor.HAIKU_4_5, Model.CLAUDE_HAIKU_4_5,
            LlmModelDescriptor.GPT_5_2, ChatModel.GPT_5_2,
            LlmModelDescriptor.GPT_NANO_5, ChatModel.GPT_5_NANO,
            LlmModelDescriptor.GPT_MINI_5, ChatModel.GPT_5_MINI
    );
}
