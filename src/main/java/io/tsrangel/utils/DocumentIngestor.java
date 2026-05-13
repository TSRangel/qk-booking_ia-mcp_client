package io.tsrangel.utils;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.nio.file.Paths;

@ApplicationScoped
public class DocumentIngestor {
    @Inject
    EmbeddingModel embeddingModel;
    @Inject
    EmbeddingStore<TextSegment> store;

    public void onStart(@Observes StartupEvent event) {
        Document document = FileSystemDocumentLoader.loadDocument(
                Paths.get("src/main/resources/rag/pacotes-viagem.md")
        );

        document.metadata().put("type", "packages");

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(200, 20))
                .embeddingModel(embeddingModel)
                .embeddingStore(store)
                .build();

        ingestor.ingest(document);
    }
}
