package com.example.tttn.service.impl;

import com.example.tttn.dto.CategoryDto;
import com.example.tttn.entity.Category;
import com.example.tttn.repository.CategoryRepository;
import com.example.tttn.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.getAll();
    }

    @Override
    public Object saveOrUpdate(CategoryDto dto) {
        Category category = new Category();
        if(dto.getId() != null){
            category = categoryRepository.findCategoryById(dto.getId());
            category.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        }else {
            category = new Category();
            category.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        category.setName(dto.getName());
        category.setCode(dto.getCode());

        category = categoryRepository.save(category);
        return new CategoryDto(category);
    }

    @Override
    public CategoryDto getById(Long id) {
        return new CategoryDto(categoryRepository.findCategoryById(id));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
