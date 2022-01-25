package com.example.cqrsdesignpatternjava.core.cq.query.impl.classified;

import com.example.cqrsdesignpatternjava.core.cq.query.QueryHandler;
import com.example.cqrsdesignpatternjava.core.elastic.service.ElasticsearchService;
import com.example.cqrsdesignpatternjava.entity.Classified;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetClassifiedsQueryHandler implements QueryHandler<GetClassifiedsQuery, List<Classified>> {

    private final ElasticsearchService elasticsearchService;

    @Autowired
    public GetClassifiedsQueryHandler(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    @Override
    public List<Classified> handle(GetClassifiedsQuery query) throws Exception {
        return this.elasticsearchService.search("classifieds", Classified.class);
    }
}
