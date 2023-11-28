package com.example.tttn.controller;

import com.example.tttn.dto.CustomerDto;
import com.example.tttn.entity.Order;
import com.example.tttn.entity.OrderDetail;
import com.example.tttn.entity.Users;
import com.example.tttn.service.CustomerService;
import com.example.tttn.service.OrderService;
import com.example.tttn.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/user/checkout")
    public String checkout(Model model) {
        CustomerDto customer = customerService.findCustomer(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("customer", customer);
        model.addAttribute("totalPriceItems", NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(cartService.totalPriceCartItems()));
        return "checkout";
    }

    @PostMapping("/user/checkout/place-order")
    public String placeOrder(HttpSession session) {
        Object cart = session.getAttribute("cart");
        if (cart != null) {
            orderService.placeOrder(cart, session);
            session.removeAttribute("cart");
            return "redirect:/user/order-history";
        } else {
            return "redirect:/user/shop/1";
        }
    }
    @GetMapping("/user/order-history")
    public String getOrder(Model model){
        List<Order> orders = orderService.getAllOrder();
        model.addAttribute("orders", orders);
        model.addAttribute("size", orders.size());
        return "orderHistory";
    }
    @GetMapping("/user/order/detail/{id}")
    public String getDetailOrder(@PathVariable("id")Long id, Model model){
        Order order = orderService.getOrder(id);
        model.addAttribute("order", order);
        return "orderDetail";
    }
    @GetMapping("/user/order/delete/{id}")
    public String cancelOrder(@PathVariable("id")Long orderId){
        orderService.deleteById(orderId);
        return "redirect:/user/order-history";
    }
}
