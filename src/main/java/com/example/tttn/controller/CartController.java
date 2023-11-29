package com.example.tttn.controller;

import com.example.tttn.dto.ProductDto;
import com.example.tttn.service.ProductService;
import com.example.tttn.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.Locale;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/user/cart")
    public String getCart(Model model) {
        model.addAttribute("totalItems", cartService.totalItems());
        model.addAttribute("totalPriceItems", NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(cartService.totalPriceCartItems()));
        return "cart";
    }

    @GetMapping("add-to-cart/{id}")
    public String addToCart(@PathVariable("id") Long productId, HttpSession session) {
        ProductDto product = productService.getById(productId);
        cartService.addToCart(product, session);
        return "redirect:/user/cart";
    }

    @GetMapping("/user/cart/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        cartService.deleteItemsInCart(id);
        return "redirect:/user/cart";
    }

    @PostMapping("/user/cart/update/{id}")
    public String updateItem(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity) {
        cartService.updateCartItem(id, quantity);
        return "redirect:/user/cart";
    }
}
