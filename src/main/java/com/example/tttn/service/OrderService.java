package com.example.tttn.service;

import com.example.tttn.entity.Order;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public interface OrderService {
    Order placeOrder(Object cart, HttpSession session);

    List<Order> getAllOrder();

    void deleteById(Long id);

    Order getOrder(Long id);
}
