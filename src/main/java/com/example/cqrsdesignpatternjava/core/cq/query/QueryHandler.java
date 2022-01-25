package com.example.cqrsdesignpatternjava.core.cq.query;

public interface QueryHandler<TQuery extends Query, TResult> {
    TResult handle(TQuery query) throws Exception;
}