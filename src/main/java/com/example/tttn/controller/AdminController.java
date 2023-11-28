package com.example.tttn.controller;

import com.example.tttn.dto.UserDto;
import com.example.tttn.entity.Order;
import com.example.tttn.entity.OrderDetail;
import com.example.tttn.entity.Product;
import com.example.tttn.repository.OrderRepository;
import com.example.tttn.repository.ProductRepository;
import com.example.tttn.repository.RoleRepository;
import com.example.tttn.repository.UsersRepository;
import com.example.tttn.service.AdminService;
import com.example.tttn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Controller
public class AdminController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public String homeForm(HttpSession session, Principal principal) {
        if (principal != null) {
            session.setAttribute("username", principal.getName());
        }
        return "adminHome";
    }

    @GetMapping("/admin/users")
    public String accountForm(Model model) {
        List<UserDto> users = usersRepository.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/admin/users/add")
    public String addUser(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", roleRepository.findAll());
        return "usersAdd";
    }

    @PostMapping("/new-user")
    public String addNewUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "usersAdd";
        }
        if (usersRepository.existsByUsername(userDto.getUsername())) {
            model.addAttribute("userDto", userDto);
            redirectAttributes.addFlashAttribute("failed", "Username have been exist!");
            return "usersAdd";
        }
        adminService.saveOrUpdate(userDto);
        model.addAttribute("success", "Thêm thành công");
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/orders")
    public String orderManage(Model model) {
        List<Order> orders = orderService.getAllOrder();
        model.addAttribute("size", orders.size());
        model.addAttribute("orders", orders);
        return "orderManage";
    }

    @GetMapping("/admin/order/detail/{id}")
    public String getDetailOrder(@PathVariable("id") Long id, Model model) {
        Order order = orderService.getOrder(id);
        model.addAttribute("order", order);
        model.addAttribute("totalItem", order.getOrderDetails().size());
        return "orderManageDetail";
    }

    @PostMapping("/admin/order/update/{id}")
    public String updateOrderStatus(@PathVariable("id") Long id, @RequestParam("status") String status) {
        Order order = orderService.getOrder(id);
        List<OrderDetail> orderDetails = order.getOrderDetails();
        order.setStatus(status);
        if (Objects.equals(status, "Đã xác nhận")) {
            for (OrderDetail item : orderDetails) {
                Product product = item.getProduct();
                product.setQuantity(product.getQuantity() - item.getQuantity());
                System.out.println(product.getQuantity());
                productRepository.save(product);
            }
        }
        orderRepository.save(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String cancelOrder(@PathVariable("id") Long orderId) {
        orderService.deleteById(orderId);
        return "redirect:/admin/orders";
    }
}
