package com.example.tttn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart extends BaseObjectDto<ShoppingCart> {
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String note;
    private int status;
    private Double totalPrice;
}
