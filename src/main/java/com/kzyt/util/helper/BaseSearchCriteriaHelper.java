package com.kzyt.util.helper;

import com.kzyt.util.BaseSearchCriteria;
import org.springframework.web.reactive.function.server.ServerRequest;

public class BaseSearchCriteriaHelper {

    public static <T extends BaseSearchCriteria> void insertBaseSearchCriteria(ServerRequest req, T criteria) {
        req.queryParam("size").ifPresent(size -> criteria.setSize(Integer.parseInt(size)));
        req.queryParam("page").ifPresent(page -> criteria.setPage(Integer.parseInt(page)));
        req.queryParam("sortField").ifPresent(criteria::setSortField);
        req.queryParam("sortDirection").ifPresent(criteria::setSortDirection);
    }

}
