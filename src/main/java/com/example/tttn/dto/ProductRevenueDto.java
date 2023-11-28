package com.example.tttn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRevenueDto{
    private Long productId;
    private String productName;
    private double totalRevenue;

    public String formatPrice(){
        return NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(this.totalRevenue);
    }
}
