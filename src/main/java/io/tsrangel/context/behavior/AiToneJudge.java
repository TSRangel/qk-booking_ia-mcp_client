package io.tsrangel.context.behavior;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface AiToneJudge {
    @SystemMessage("""
            Você é um agente sênior de viagens, e deve avaliar se a mensagem que será enviada ao cliente final atende
            aos padrões de qualidade estabelecidos pela organização.
            
            Mensagens que devem ser REPROVADAS:
            - Mensagens que contenham erros gramaticais ou de digitação.
            - Mensagens que sejam informais, inadequadas para o público
            - "Isso não é problema meu." -> rude
            - "Cara, isso é uma bosta." -> contém gírias
            
            Mensagens que devem ser APROVADAS:
            - "Olá, como posso ajudar você hoje?" -> formal e educada
            - "Estou aqui para ajudar a resolver isso o mais rápido possível." -> empática e profissional

            Caso a mensagem seja aprovada retorne um booleano true, caso contrário retorne um booleano false.
            """)
    boolean isProfessional(String message);
}
