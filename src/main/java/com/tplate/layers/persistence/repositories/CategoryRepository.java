package com.tplate.layers.persistence.repositories;

import com.tplate.layers.persistence.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    Category getOneById(Long id);

    Boolean existsByName(String name);

    Category getByName(String name);

}
