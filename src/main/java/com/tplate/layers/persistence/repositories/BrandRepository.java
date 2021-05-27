package com.tplate.layers.persistence.repositories;

import com.tplate.layers.persistence.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Boolean existsByName(String name);

}
