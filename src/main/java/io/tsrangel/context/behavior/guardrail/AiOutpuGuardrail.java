package io.tsrangel.context.behavior.guardrail;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailResult;
import io.tsrangel.context.behavior.AiToneJudge;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AiOutpuGuardrail implements OutputGuardrail {
    @Inject
    AiToneJudge aiToneJudge;

    @Override
    public OutputGuardrailResult validate(AiMessage aiMessage) {
        if (aiToneJudge.isProfessional(aiMessage.text())) {
            return success();
        }
        return reprompt(aiMessage.text(), "Resposta do AI não está de acordo com o tom profissional esperado. Por favor, reformule a resposta mantendo um tom profissional.");
    }
}
