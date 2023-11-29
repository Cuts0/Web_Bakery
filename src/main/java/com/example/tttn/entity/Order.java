package com.example.tttn.entity;

import lombok.Data;

import javax.persistence.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "orders")
@Data
public class Order extends BaseObject {
    @Column
    private String status;
    @Column(name = "total_price")
    private Double totalPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Users customer;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    public String formatPrice(Double price) {
        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(price);
    }
}
