package com.example.cqrsdesignpatternjava.core.elastic.client;

import org.elasticsearch.client.RestHighLevelClient;

public interface ElasticsearchApiClient {

    RestHighLevelClient getRestHighLevelClient();
}