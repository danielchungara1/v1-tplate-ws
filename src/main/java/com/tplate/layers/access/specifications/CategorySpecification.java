package com.tplate.layers.access.specifications;

import com.tplate.layers.persistence.models.Category;
import com.tplate.layers.persistence.models.Permission;
import com.tplate.layers.persistence.models.Role;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class CategorySpecification implements Specification<Category> {

    private String text;
    private Long parentId;

    @Override
    public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (this.getText()!= null){
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + this.getText().toLowerCase() + "%"));
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + this.getText().toLowerCase() + "%"));
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + this.getText().toLowerCase() + "%"));

            Predicate[] array = new Predicate[predicateList.size()];
            predicates.add(criteriaBuilder.or(predicateList.toArray(array)));
        }

        if (this.getParentId() != null){
            predicates.add(criteriaBuilder.equal(root.get("parentId"), this.getParentId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));

    }

}
