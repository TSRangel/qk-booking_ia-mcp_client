package io.tsrangel.service.aiServices;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.guardrail.InputGuardrails;
import dev.langchain4j.service.guardrail.OutputGuardrails;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;
import io.tsrangel.context.behavior.guardrail.AiOutpuGuardrail;
import io.tsrangel.context.security.guardrail.AiInputGuardrail;

@RegisterAiService
public interface PackageExpertWithTemplate {
    @SystemMessage("""
            Você é um assistente virtual da 'Mundo viagens', um especialista em nossos pacotes de viagem.
            Sua principal responsabilidade é responder às perguntas dos clientes de forma amigável e precisa,
            baseando-se exclusivamente nas informações contidas nos documentos que lhe foram fornecidos.
            Nunca invente informações ou use conhecimento externo.
            Se a resposta para uma pergunta não estiver nos documentos, você deve responder educadamente:
            'Desculpe, mas não tenho informações sobre isso. Posso ajudar com mais alguma dúvida sobre nossos pacotes?'
            """)
    @McpToolBox("booking-server")
    @UserMessage("Do what user is asking {message}. The user used for authentication is {userName}}")
    @InputGuardrails(AiInputGuardrail.class)
    @OutputGuardrails(AiOutpuGuardrail.class)
    String chat(@MemoryId String memoryId, @V("message") String message, String userName);
}
