package com.example.tttn.controller;

import com.example.tttn.dto.CustomerDto;
import com.example.tttn.repository.UsersRepository;
import com.example.tttn.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/home")
    public String home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN"))) {
            return "redirect:/admin/home";
        } else if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("CUSTOMER"))) {
            return "redirect:/user/home";
        } else
            return "404";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("customerDto", customerDto);
            return "register";
        }
        if (usersRepository.existsByUsername(customerDto.getUsername())) {
            model.addAttribute("customerDto", customerDto);
            model.addAttribute("failed", "Tài khoản đã được đăng ký!!!");
            return "register";
        }
        customerService.saveOrUpdate(customerDto);
        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công");
        return "redirect:/login";
    }
}
