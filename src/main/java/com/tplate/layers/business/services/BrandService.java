package com.tplate.layers.business.services;

import com.tplate.layers.access.dtos.brand.BrandDto;
import com.tplate.layers.business.exceptions.brand.BrandNameExistException;
import com.tplate.layers.business.exceptions.brand.BrandNotExistException;
import com.tplate.layers.persistence.models.Brand;
import com.tplate.layers.persistence.repositories.BrandRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class BrandService {

    @Autowired
    BrandRepository repository;


    @Transactional
    public Brand getModelById(Long id) throws BrandNotExistException {

        if (id == null) { BrandNotExistException.throwsException(id); }

        return this.repository.findById(id)
                .orElseThrow(() -> new BrandNotExistException(id));

    }

    @Transactional
    public List<Brand> findAll() {
        return this.repository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Brand saveModel(BrandDto dto) throws BrandNameExistException {

        // Brand Name
        if (this.repository.existsByName(dto.getName())) {
            BrandNameExistException.throwsException(dto.getName());
        }

        Brand brand = Brand.builder().build();
        brand.setName(dto.getName());

        return this.saveOrUpdateModel(brand, dto);
    }

    @Transactional(rollbackFor = Exception.class)
    public Brand updateModel(BrandDto dto, Long id) throws BrandNotExistException, BrandNameExistException {

        Brand model = this.getModelById(id);

        // Name False Positive.
        if ( !this.repository.existsByName(dto.getName()) ) {
            model.setName(dto.getName());
        } else {
            if (model.getName().equals(dto.getName())) {
                // False Positive
            } else {
                BrandNameExistException.throwsException(dto.getName());
            }
        }

        return this.saveOrUpdateModel(model, dto);
    }


    private Brand saveOrUpdateModel(Brand brand, BrandDto dto) {

        // Brand Description
        brand.setDescription(dto.getDescription());

        // Brand Title
        brand.setTitle(dto.getTitle());

        return this.repository.save(brand);

    }

    @Transactional
    public void deleteModelById(Long id) throws BrandNotExistException {

        // Validations
        if (!this.repository.existsById(id)) {
            BrandNotExistException.throwsException(id);
        }

        this.repository.deleteById(id);

    }

}
