package com.example.tttn.dto;

import com.example.tttn.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto extends BaseObjectDto<CategoryDto> {
    private String name;
    private String code;

    public CategoryDto(Category entity) {
        if (entity != null) {
            this.setId(entity.getId());
            this.name = entity.getName();
            this.code = entity.getCode();
        }
    }

}
