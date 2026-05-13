package io.tsrangel.context.security.guardrail;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailResult;
import io.tsrangel.context.security.AiPromptSecurity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AiInputGuardrail implements InputGuardrail {
    @Inject
    AiPromptSecurity aiPromptSecurity;

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        if (aiPromptSecurity.isSecure(userMessage.singleText())) {
            return success();
        }
        return failure("Mensagem enviada pelo cliente foi considerada insegura.");
    }
}
