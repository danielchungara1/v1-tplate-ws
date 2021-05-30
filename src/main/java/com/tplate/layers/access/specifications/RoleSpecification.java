package com.tplate.layers.access.specifications;

import com.tplate.layers.persistence.models.Permission;
import com.tplate.layers.persistence.models.Role;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoleSpecification implements Specification<Role> {

    private String text;
    private Long permissionId;

    @Override
    public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (this.getText()!= null){
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + this.getText().toLowerCase() + "%"));
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + this.getText().toLowerCase() + "%"));

            Predicate[] array = new Predicate[predicateList.size()];
            predicates.add(criteriaBuilder.or(predicateList.toArray(array)));
        }

        if (this.getPermissionId() != null){
            Join<Role, Permission> join = root.join("permissions", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("id"), this.getPermissionId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));

    }

}
