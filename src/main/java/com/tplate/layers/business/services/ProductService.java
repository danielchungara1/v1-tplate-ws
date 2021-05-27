package com.tplate.layers.business.services;

import com.tplate.layers.access.dtos.product.ProductDto;
import com.tplate.layers.business.exceptions.brand.BrandNotExistException;
import com.tplate.layers.business.exceptions.category.CategoryNotExistException;
import com.tplate.layers.business.exceptions.product.ProductNameExistException;
import com.tplate.layers.business.exceptions.product.ProductNotExistException;
import com.tplate.layers.persistence.models.Brand;
import com.tplate.layers.persistence.models.Category;
import com.tplate.layers.persistence.models.Product;
import com.tplate.layers.persistence.repositories.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@Log4j2
public class ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Transactional
    public Product getModelById(Long id) throws ProductNotExistException {

        if (id == null) {
            ProductNotExistException.throwsException(id);
        }

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

        Function<Product, Product> saveBrandCallback = (modelPP) -> {
            if (dto.getBrandId() != null) {
                modelPP.setBrand(this.brandService.getModelById(dto.getBrandId()));
                return this.repository.save(modelPP);
            } else {
                return modelPP;
            }
        };

        Function<Product, Product> saveCategoryCallback = (modelPP) -> {
            if (dto.getCategoryId() != null) {
                modelPP.setCategory(this.categoryService.getModelById(dto.getCategoryId()));
                return this.repository.save(modelPP);
            } else {
                return modelPP;
            }
        };

        return this.saveOrUpdateModel(model, dto, saveBrandCallback, saveCategoryCallback);
    }

    @Transactional(rollbackFor = Exception.class)
    public Product updateModel(ProductDto dto, Long id) throws ProductNotExistException, ProductNameExistException {

        Product model = this.getModelById(id);

        // Product name False Positive.
        if (!this.repository.existsByName(dto.getName())) {
            model.setName(dto.getName());
        } else {
            if (model.getName().equals(dto.getName())) {
                // False Positive
            } else {
                ProductNameExistException.throwsException(dto.getName());
            }
        }

        Function<Product, Product> saveBrandCallback = (modelPP) -> {
            if (dto.getBrandId() != null) {
                modelPP.setBrand(this.brandService.getModelById(dto.getBrandId()));
                return this.repository.save(modelPP);
            } else {
                modelPP.setBrand(null);
                return this.repository.save(modelPP);
            }
        };

        Function<Product, Product> saveCategoryCallback = (modelPP) -> {
            if (dto.getCategoryId() != null) {
                modelPP.setCategory(this.categoryService.getModelById(dto.getCategoryId()));
                return this.repository.save(modelPP);
            } else {
                modelPP.setCategory(null);
                return this.repository.save(modelPP);
            }
        };

        return this.saveOrUpdateModel(model, dto, saveBrandCallback, saveCategoryCallback);
    }


    private Product saveOrUpdateModel(Product model, ProductDto dto, Function<Product, Product> saveBrandCallback, Function<Product, Product> saveCategoryCallback) throws BrandNotExistException {

        // Product Description, Title
        model.setDescription(dto.getDescription());
        model.setTitle(dto.getTitle());

        model = this.repository.save(model);

        saveBrandCallback.apply(model);
        saveCategoryCallback.apply(model);

        return model;

    }

    @Transactional
    public void deleteModelById(Long id) throws ProductNotExistException {

        // Validations
        if (!this.repository.existsById(id)) {
            ProductNotExistException.throwsException(id);
        }

        this.repository.deleteById(id);

    }

    public List<Product> getModelsBy(Brand brand) {

        if (brand == null) {
            BrandNotExistException.throwsException(null);
        }

        return this.repository.findByBrand(brand);

    }

    public List<Product> getModelsBy(Category category) {

        if (category == null) {
            CategoryNotExistException.throwsException(null);
        }

        return this.repository.findByCategory(category);

    }

    public void makeBrandNullForAllProductsBy(Brand brand) {

        List<Product> products = this.getModelsBy(brand);

        if (products != null) {
            products.parallelStream().forEach(product -> {
                product.setBrand(null);
                this.repository.save(product);
            });
        }

    }

    public void makeCategoryNullForAllProductsBy(Category category) {

        List<Product> products = this.getModelsBy(category);

        if (products != null) {
            products.parallelStream().forEach(product -> {
                product.setCategory(null);
                this.repository.save(product);
            });
        }

    }

}
