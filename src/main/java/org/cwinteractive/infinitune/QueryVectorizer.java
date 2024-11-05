package org.cwinteractive.infinitune;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.OnnxEmbeddingModel;
import dev.langchain4j.model.embedding.onnx.PoolingMode;
import dev.langchain4j.model.output.Response;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.logging.Logger;

@ApplicationScoped
public class QueryVectorizer {

    private static Logger LOG = Logger.getLogger(QueryVectorizer.class.getName());
    private static final String modelPath = "/model/model.onnx";
    private static final String tokenizerPath = "/model/tokenizer.json";

    private EmbeddingModel embeddingModel;

    @PostConstruct
    void initialize() {
        LOG.info("Initializing QueryVectorizer ..");
        var fullModelPath = getClass().getResource(modelPath).getPath();
        var fullTokenizerPath = getClass().getResource(tokenizerPath).getPath();

        LOG.info("fullModelPath = " + fullModelPath);
        LOG.info("fullTokenizerPath = " + fullTokenizerPath);

        embeddingModel = new OnnxEmbeddingModel(fullModelPath, fullTokenizerPath, PoolingMode.MEAN);
    }

    public float[] getQueryVector(String query) {
        Response<Embedding> embeddingResponse = embeddingModel.embed(query);
        return embeddingResponse.content().vector();
    }

}
