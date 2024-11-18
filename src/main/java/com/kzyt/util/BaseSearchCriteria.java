package com.kzyt.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.query.Criteria;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseSearchCriteria {

    private int size = 10;
    private int page = 1;
    private String sortField = "createdTime";
    private String sortDirection = "DESC";

    public abstract Criteria toCriteria();

}
