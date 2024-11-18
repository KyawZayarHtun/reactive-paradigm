package com.kzyt.util.helper;

import com.kzyt.util.BaseSearchCriteria;
import com.kzyt.util.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public record ReactiveMongoHelper(
        ReactiveMongoTemplate mongoTemplate
) {

    public <T, R> Mono<Page<R>> getPagedResult(BaseSearchCriteria criteria, Class<T> entityClass, Function<T, R> mapper) {
        Criteria filterCriteria = criteria.toCriteria();
        Mono<Long> totalCount = mongoTemplate.count(new Query(), entityClass);
        Mono<Long> filterCount = mongoTemplate.count(new Query().addCriteria(filterCriteria), entityClass);

        Sort sort = Sort.by(Sort.Direction.fromString(criteria.getSortDirection()), criteria.getSortField());
        Query query = new Query()
                .addCriteria(filterCriteria)
                .with(sort)
                .skip((long) (criteria.getPage() - 1) * criteria.getSize())
                .limit(criteria.getSize());
        System.out.println("query = " + query);

        Flux<R> results = mongoTemplate.find(query, entityClass).map(mapper);

        return totalCount.zipWith(filterCount)
                .flatMap(tuple -> results.collectList()
                        .map(resultList -> new Page<>(resultList, criteria.getPage(), criteria.getSize(), tuple.getT1(), tuple.getT2())));

    }

}
