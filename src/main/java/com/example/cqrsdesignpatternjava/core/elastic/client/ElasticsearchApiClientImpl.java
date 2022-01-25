package com.example.cqrsdesignpatternjava.core.elastic.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchApiClientImpl implements ElasticsearchApiClient {

    private final RestHighLevelClient restHighLevelClient;

    public ElasticsearchApiClientImpl() {
        restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));
    }

    @Override
    public RestHighLevelClient getRestHighLevelClient() {
        return this.restHighLevelClient;
    }
}

