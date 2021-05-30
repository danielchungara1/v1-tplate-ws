package com.tplate.layers.access.specifications;

import com.tplate.layers.persistence.models.Brand;
import com.tplate.layers.persistence.models.Category;
import com.tplate.layers.persistence.models.Product;
import com.tplate.layers.persistence.models.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductSpecification implements Specification<Product> {

    private String text;

    private Long brandId;
    private Long categoryId;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (this.getText()!= null){
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + this.getText().toLowerCase() + "%"));
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + this.getText().toLowerCase() + "%"));
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + this.getText().toLowerCase() + "%"));

            Predicate[] array = new Predicate[predicateList.size()];
            predicates.add(criteriaBuilder.or(predicateList.toArray(array)));
        }

        if (this.getBrandId() != null){
            Join<User, Brand> join = root.join("brand", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("id"), this.getBrandId()));
        }

        if (this.getCategoryId() != null){
            Join<User, Category> join = root.join("category", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("id"), this.getCategoryId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));

    }

}
