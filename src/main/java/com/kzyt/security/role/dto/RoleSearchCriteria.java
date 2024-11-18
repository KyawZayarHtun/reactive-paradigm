package com.kzyt.security.role.dto;

import com.kzyt.util.BaseSearchCriteria;
import com.kzyt.util.helper.BaseSearchCriteriaHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.reactive.function.server.ServerRequest;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleSearchCriteria extends BaseSearchCriteria {

    private String name;

    public static RoleSearchCriteria from(ServerRequest req) {
        RoleSearchCriteria criteria = new RoleSearchCriteria();
        BaseSearchCriteriaHelper.insertBaseSearchCriteria(req, criteria);
        req.queryParam("name").ifPresent(criteria::setName);
        return criteria;
    }

    @Override
    public Criteria toCriteria() {
        Criteria criteria = new Criteria();
        if (name != null && !name.isBlank()) {
            criteria.and("name").regex(".*" + name + ".*", "i");
        }
        return criteria;
    }
}
