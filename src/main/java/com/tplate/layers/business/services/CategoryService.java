package com.tplate.layers.business.services;

import com.tplate.layers.access.dtos.category.CategoryDto;
import com.tplate.layers.business.exceptions.category.CategoryNameExistException;
import com.tplate.layers.business.exceptions.category.CategoryNotExistException;
import com.tplate.layers.persistence.models.Category;
import com.tplate.layers.persistence.repositories.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@Log4j2
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    @Transactional
    public Category getModelById(Long id) throws CategoryNotExistException {

        if (id == null) {
            CategoryNotExistException.throwsException(id);
        }

        return this.repository.findById(id)
                .orElseThrow(() -> new CategoryNotExistException(id));

    }

    @Transactional
    public List<Category> findAll() {
        return this.repository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Category saveModel(CategoryDto dto) throws CategoryNameExistException {

        // Category Name
        if (this.repository.existsByName(dto.getName())) {
            CategoryNameExistException.throwsException(dto.getName());
        }

        Category model = Category.builder().build();
        model.setName(dto.getName());

        Function<Category, Category> saveParentCallback = (Category modelPP) -> {
            if (dto.getParentId() != null) {
                Category parent = getModelById(dto.getParentId());
                parent.getChildren().add(modelPP); // For saving category_parent_id into child
                this.repository.save(parent);
            }
            return modelPP;
        };

        return this.saveOrUpdateModel(model, dto, saveParentCallback);
    }

    @Transactional(rollbackFor = Exception.class)
    public Category updateModel(CategoryDto dto, Long id) throws CategoryNotExistException, CategoryNameExistException {

        Category model = this.getModelById(id);

        // Category name False Positive.
        if (!this.repository.existsByName(dto.getName())) {
            model.setName(dto.getName());
        } else {
            if (model.getName().equals(dto.getName())) {
                // False Positive
            } else {
                CategoryNameExistException.throwsException(dto.getName());
            }
        }

        Function<Category, Category> saveParentCallback = (Category modelPP) -> {
            if (dto.getParentId() != null) {
                Category parent = getModelById(dto.getParentId());
                parent.getChildren().add(modelPP); // For saving category_parent_id into child
                this.repository.save(parent);
            }
            else {
                Long parentId = model.getParentId();
                if (parentId != null) {
                    Category parent = getModelById(model.getParentId());
                    parent.getChildren().remove(modelPP); // The child is converted to root category.
                    this.repository.save(parent);
                }
            }
            return modelPP;
        };

        return this.saveOrUpdateModel(model, dto, saveParentCallback);
    }


    private Category saveOrUpdateModel(Category model, CategoryDto dto, Function<Category, Category> saveParentCallback) {

        // Category Description, Title
        model.setDescription(dto.getDescription());
        model.setTitle(dto.getTitle());

        model = this.repository.save(model);

        return saveParentCallback.apply(model);

    }

    @Transactional
    public void deleteModelById(Long id) throws CategoryNotExistException {

        // Validations
        if (!this.repository.existsById(id)) {
            CategoryNotExistException.throwsException(id);
        }

        this.repository.deleteById(id);

    }

}
