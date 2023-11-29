package com.example.tttn.entity;

import lombok.Data;

import javax.persistence.*;
import java.text.NumberFormat;
import java.util.Locale;

@Entity
@Table(name = "orderDetail")
@Data
public class OrderDetail extends BaseObject {
    @Column
    private Integer quantity;
    @Column
    private Double price;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orders;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public String formatPrice(Double price) {
        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(price);
    }
}
