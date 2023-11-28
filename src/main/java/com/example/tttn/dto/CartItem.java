package com.example.tttn.dto;

import com.example.tttn.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends BaseObjectDto<CartItem>{
    private Long productId;
    private String productName;
    private Double price;
    private Double totalPrice;
    private String image;
    private Integer quantity;

    public CartItem(OrderDetail entity){
        if (entity != null) {
            this.setId(entity.getId());
            this.productId= entity.getProduct().getId();
            this.productName = entity.getProduct().getName();
            this.price = entity.getPrice();
            this.totalPrice = entity.getPrice() * entity.getQuantity();
            this.image = entity.getProduct().getImage();
            this.quantity = entity.getQuantity();
        }
    }
    public String formatTotalPrice(){
        return NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(this.totalPrice);
    }
    public String formatPrice(){
        return NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(this.price);
    }
}
