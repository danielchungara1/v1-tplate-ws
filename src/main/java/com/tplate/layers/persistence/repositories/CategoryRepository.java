package com.tplate.layers.persistence.repositories;

import com.tplate.layers.persistence.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category getOneById(Long id);

    Boolean existsByName(String name);

    Category getByName(String name);

    // Get fk from native query
    // Add parentId property in Category
    // Add parentCategory property in Category
//    Optional<Category> getParentFromChildId(Long id);
}
