package com.example.tttn.service;

import com.example.tttn.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategoryService {
    List<CategoryDto> getAll();
    Object saveOrUpdate(CategoryDto category);
    CategoryDto getById(Long id);
    void deleteById(Long id);
}
