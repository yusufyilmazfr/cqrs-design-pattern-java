package com.example.cqrsdesignpatternjava.core.elastic.service;

import com.example.cqrsdesignpatternjava.core.elastic.client.ElasticsearchApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final ObjectMapper objectMapper;
    private final RestHighLevelClient restHighLevelClient;


    @Autowired
    public ElasticsearchServiceImpl(ObjectMapper objectMapper, ElasticsearchApiClient elasticsearchApiClient) {
        this.objectMapper = objectMapper;
        this.restHighLevelClient = elasticsearchApiClient.getRestHighLevelClient();
    }

    @Override
    public <TSource> void insertDocument(String indexName, String documentUniqueId, TSource source) throws IOException {
        String jsonData = objectMapper.writeValueAsString(source);

        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.id(documentUniqueId);
        indexRequest.source(jsonData, XContentType.JSON);

        this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    @Override
    public <TSource> List<TSource> search(String indexName, Class<TSource> clazz) throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(1000);

        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        return Stream
                .of(searchResponse.getHits().getHits())
                .map(hit -> convert(hit.getSourceAsString(), clazz))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private <T> T convert(String json, Class<T> clazz) {
        return this.objectMapper.readValue(json, clazz);
    }
}
