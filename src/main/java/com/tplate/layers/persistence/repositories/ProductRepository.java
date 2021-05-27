package com.tplate.layers.persistence.repositories;

import com.tplate.layers.persistence.models.Brand;
import com.tplate.layers.persistence.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getOneById(Long id);

    Boolean existsByName(String name);

    List<Product> findByBrand(Brand brand);
}
