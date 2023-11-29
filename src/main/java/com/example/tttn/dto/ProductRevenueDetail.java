package com.example.tttn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRevenueDetail {
    private Date orderDate;
    private String productName;
    private double price;
    private int quantity;
    private String customerName;

    public String formatPrice() {
        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(this.price);
    }

    public String formatDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.orderDate);
    }

}
