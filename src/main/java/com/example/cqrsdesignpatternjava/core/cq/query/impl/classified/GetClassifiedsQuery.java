package com.example.cqrsdesignpatternjava.core.cq.query.impl.classified;

import com.example.cqrsdesignpatternjava.core.cq.query.Query;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GetClassifiedsQuery implements Query {
    private String price;
    private String categoryId;
}
