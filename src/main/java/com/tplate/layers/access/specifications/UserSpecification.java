package com.tplate.layers.access.specifications;

import com.tplate.layers.persistence.models.Permission;
import com.tplate.layers.persistence.models.Role;
import com.tplate.layers.persistence.models.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserSpecification implements Specification<User> {

    private String text;
    private Long roleId;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (this.getText()!= null){
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + this.getText().toLowerCase() + "%"));
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")), "%" + this.getText().toLowerCase() + "%"));
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + this.getText().toLowerCase() + "%"));
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + this.getText().toLowerCase() + "%"));
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + this.getText().toLowerCase() + "%"));

            Predicate[] array = new Predicate[predicateList.size()];
            predicates.add(criteriaBuilder.or(predicateList.toArray(array)));
        }

        if (this.getRoleId() != null){
            Join<User, Role> join = root.join("role", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("id"), this.getRoleId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));

    }

}
