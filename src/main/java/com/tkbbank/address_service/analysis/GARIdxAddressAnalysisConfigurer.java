package com.tkbbank.address_service.analysis;

import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;

public class GARIdxAddressAnalysisConfigurer implements LuceneAnalysisConfigurer {
    @Override
    public void configure(LuceneAnalysisConfigurationContext context) {
        context.analyzer("whitespace_edgeNGramMin3Max30_analyzer").custom()
                .tokenizer("whitespace")
                .charFilter("mapping").param("mapping", "search/charfilter-mapping.txt")
                .tokenFilter("wordDelimiter").param("preserveOriginal", "1").param("splitOnNumerics", "0").param("splitOnCaseChange", "0")
//                .tokenFilter("stop").param("ignoreCase", "true").param("words", "search/stopwords.txt").param("format", "wordset")
                .tokenFilter("edgeNGram").param("minGramSize", "3").param("maxGramSize", "30").param("preserveOriginal", "true")
                .tokenFilter("lowercase");

        context.analyzer("keyword_analyzer").custom()
                .tokenizer("keyword")
                .charFilter("mapping").param("mapping", "search/charfilter-mapping.txt")
                .tokenFilter("lowercase");

        context.analyzer("keyword_edgeNGramMin3Max30_analyzer").custom()
                .tokenizer("keyword")
                .charFilter("mapping").param("mapping", "search/charfilter-mapping.txt")
                .tokenFilter("edgeNGram").param("minGramSize", "3").param("maxGramSize", "30").param("preserveOriginal", "true")
                .tokenFilter("lowercase");
    }
}