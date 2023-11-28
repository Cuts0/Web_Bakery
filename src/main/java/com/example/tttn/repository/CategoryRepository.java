package com.example.tttn.repository;

import com.example.tttn.dto.CategoryDto;
import com.example.tttn.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryById(Long id);

    @Query("select new com.example.tttn.dto.CategoryDto(ed) from Category ed")
    List<CategoryDto> getAll();
}
