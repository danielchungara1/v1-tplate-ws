package com.tplate.layers.persistence.repositories;

import com.tplate.layers.persistence.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getOneById(Long id);

    Boolean existsByName(String name);

    Product getByName(String name);

}
