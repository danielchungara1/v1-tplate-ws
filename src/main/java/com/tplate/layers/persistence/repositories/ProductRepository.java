package com.tplate.layers.persistence.repositories;

import com.tplate.layers.persistence.models.Brand;
import com.tplate.layers.persistence.models.Category;
import com.tplate.layers.persistence.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Product getOneById(Long id);

    Boolean existsByName(String name);

    List<Product> findByBrand(Brand brand);

    List<Product> findByCategory(Category category);
}
