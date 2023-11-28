package com.example.tttn.service.impl;

import com.example.tttn.dto.CartItem;
import com.example.tttn.dto.ProductDto;
import com.example.tttn.entity.OrderDetail;
import com.example.tttn.entity.Product;
import com.example.tttn.repository.OrderDetailRepository;
import com.example.tttn.repository.ProductRepository;
import com.example.tttn.service.ShoppingCartService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.DoubleStream;

@Service
public class ShoppingCartsServiceImpl implements ShoppingCartService {

    private static Map<Long, OrderDetail> map = new HashMap<>();
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void addToCart(ProductDto dto, HttpSession session) {
        Product product = productRepository.findProductById(dto.getId());
        if (product != null) {
            Object cart = session.getAttribute("cart");
            if (cart == null) {
                map = new HashMap<>();
                OrderDetail item = new OrderDetail();
                item.setProduct(product);
                item.setQuantity(1);
                item.setPrice(product.getPrice());
                map.put(dto.getId(), item);
                session.setAttribute("cart", map);
            } else {
                map = (Map<Long, OrderDetail>) cart;
                OrderDetail item = map.get(dto.getId());
                if (item == null){
                    item = new OrderDetail();
                    item.setProduct(product);
                    item.setQuantity(1);
                    item.setPrice(product.getPrice());
                    map.put(dto.getId(), item);
                }else {
                    item.setQuantity(item.getQuantity() + 1);
                }
                session.setAttribute("cart", map);
            }
        }
    }


    public int totalItems() {
        return map.values().size();
    }

    @Override
    public void clearCart() {
        map.clear();
    }

    @Override
    public OrderDetail updateCartItem(Long productId, int quantity) {
        OrderDetail item = map.get(productId);
        item.setQuantity(quantity);
        return item;
    }

    @Override
    public double totalPriceCartItems() {
        return map.values().stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
    }

    @Override
    public void deleteItemsInCart(Long id) {
        map.remove(id);
    }

    @Override
    public OrderDetail getItem(Long id) {
        return orderDetailRepository.findOrderDetailById(id);
    }

}
