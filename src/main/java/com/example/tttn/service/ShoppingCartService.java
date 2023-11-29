package com.example.tttn.service;

import com.example.tttn.dto.ProductDto;
import com.example.tttn.entity.OrderDetail;

import javax.servlet.http.HttpSession;

public interface ShoppingCartService {
    void addToCart(ProductDto dto, HttpSession session);

    //    Collection<CartItem> getAllItems();
    int totalItems();

    void clearCart();

    OrderDetail updateCartItem(Long productId, int quantity);

    double totalPriceCartItems();

    void deleteItemsInCart(Long id);

    OrderDetail getItem(Long id);
//    Order getOrCreatCart(HttpSession session);
}
