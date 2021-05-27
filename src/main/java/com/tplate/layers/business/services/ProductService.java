package com.tplate.layers.business.services;

import com.tplate.layers.access.dtos.product.ProductDto;
import com.tplate.layers.business.exceptions.product.ProductNameExistException;
import com.tplate.layers.business.exceptions.product.ProductNotExistException;
import com.tplate.layers.persistence.models.Product;
import com.tplate.layers.persistence.repositories.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class ProductService {

    @Autowired
    ProductRepository repository;

    @Transactional
    public Product getModelById(Long id) throws ProductNotExistException {

        if (id == null) { ProductNotExistException.throwsException(id); }

        return this.repository.findById(id)
                .orElseThrow(() -> new ProductNotExistException(id));

    }

    @Transactional
    public List<Product> findAll() {
        return this.repository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Product saveModel(ProductDto dto) throws ProductNameExistException {

        // Product Name
        if (this.repository.existsByName(dto.getName())) {
            ProductNameExistException.throwsException(dto.getName());
        }

        Product model = Product.builder().build();
        model.setName(dto.getName());

        return this.saveOrUpdateModel(model, dto);
    }

    @Transactional(rollbackFor = Exception.class)
    public Product updateModel(ProductDto dto, Long id) throws ProductNotExistException, ProductNameExistException {

        Product model = this.getModelById(id);

        // Product name False Positive.
        if ( !this.repository.existsByName(dto.getName()) ) {
            model.setName(dto.getName());
        } else {
            if (model.getName().equals(dto.getName())) {
                // False Positive
            } else {
                ProductNameExistException.throwsException(dto.getName());
            }
        }

        return this.saveOrUpdateModel(model, dto);
    }


    private Product saveOrUpdateModel(Product model, ProductDto dto) {

        // Product Description, Title
        model.setDescription(dto.getDescription());
        model.setTitle(dto.getTitle());
        
        return this.repository.save(model);

    }

    @Transactional
    public void deleteModelById(Long id) throws ProductNotExistException {

        // Validations
        if (!this.repository.existsById(id)) {
            ProductNotExistException.throwsException(id);
        }
        
        this.repository.deleteById(id);

    }
    
}
