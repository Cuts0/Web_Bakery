package com.example.tttn.service.impl;

import com.example.tttn.entity.Order;
import com.example.tttn.entity.OrderDetail;
import com.example.tttn.entity.Users;
import com.example.tttn.repository.OrderDetailRepository;
import com.example.tttn.repository.OrderRepository;
import com.example.tttn.repository.UsersRepository;
import com.example.tttn.service.OrderService;
import com.example.tttn.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ShoppingCartService cartService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public Order placeOrder(Object cart, HttpSession session) {
        Map<Long, OrderDetail> map = (Map<Long, OrderDetail>) cart;
        Order order = new Order();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users customer = usersRepository.findByUsername(username);
        order.setCustomer(customer);
        order.setStatus("Chờ xác nhận");
        order.setTotalPrice(cartService.totalPriceCartItems());

        orderRepository.save(order);
        for (Map.Entry<Long, OrderDetail> entry : map.entrySet()) {
            OrderDetail item = entry.getValue();
            item.setOrders(order);
            orderDetailRepository.save(item);
        }
        return order;
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.getOrderById(id);
    }
}
