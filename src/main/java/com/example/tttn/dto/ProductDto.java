package com.example.tttn.dto;

import com.example.tttn.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends BaseObjectDto<ProductDto>{
    private String code;
    private String name;
    private Long categoryId;
    private String categoryName;
    private Double price;
    private String thumbnail;
    private String description;
    private Integer quantity;

    public String formatPrice(){
        return NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(this.price);
    }

    public ProductDto(Product product){
        if (product != null){
            this.setId(product.getId());
            this.name = product.getName();
            this.code = product.getCode();
            this.price = product.getPrice();
            this.thumbnail = product.getImage();
            this.description = product.getDescription();
            this.quantity = product.getQuantity();
            this.categoryId = product.getCategory().getId();
            this.categoryName = product.getCategory().getName();
        }
    }

}
