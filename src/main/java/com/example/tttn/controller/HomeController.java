package com.example.tttn.controller;

import com.example.tttn.dto.CategoryDto;
import com.example.tttn.dto.CustomerDto;
import com.example.tttn.dto.ProductDto;
import com.example.tttn.repository.UsersRepository;
import com.example.tttn.service.CategoryService;
import com.example.tttn.service.CustomerService;
import com.example.tttn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = {"/user/home", "/"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session) {
        if (principal != null) {
            session.setAttribute("username", principal.getName());
        } else {
            session.removeAttribute("username");
        }
        return "index";
    }

    @GetMapping("/user/shop/{pageIndex}")
    public String getProducts(@PathVariable(name = "pageIndex") Integer pageIndex, Model model) {
        List<CategoryDto> categories = categoryService.getAll();
        Page<ProductDto> products = productService.pageProducts(pageIndex);
        model.addAttribute("title", "Shop");
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("size", products.getSize());
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", products.getTotalPages());
        return "shop";
    }

    @GetMapping("/user/shop/{code}/{pageIndex}")
    public String getProductInCategory(@PathVariable("code") String categoryCode,
                                       @PathVariable("pageIndex") Integer pageIndex,
                                       Model model) {
        List<CategoryDto> categories = categoryService.getAll();
        Page<ProductDto> products = productService.pageProductsInCategory(pageIndex, categoryCode);
        model.addAttribute("title", "Shop");
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("size", products.getSize());
        model.addAttribute("categoryCode", categoryCode);
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", products.getTotalPages());
        return "productsInCategory";
    }

    @GetMapping("/user/shop/product-detail/{id}")
    public String getProductDetail(@PathVariable("id") Long productId, Model model) {
        ProductDto product = productService.getById(productId);
        model.addAttribute("title", "Thông tin sản phẩm");
        model.addAttribute("product", product);
        return "viewProduct";
    }

    @GetMapping("/user/shop/search-product/{pageIndex}")
    public String searchProduct(@RequestParam("key") String key,
                                @PathVariable("pageIndex") int pageIndex, Model model) {
        List<CategoryDto> categories = categoryService.getAll();
        Page<ProductDto> products = productService.pageSearchProduct(pageIndex, key);

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("size", products.getSize());
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("key", key);
        return "searchViewProduct";
    }

    @GetMapping("/user/my-account")
    public String updateInfor(Model model, Principal principal) {
        CustomerDto customerDto = new CustomerDto(usersRepository.findByUsername(principal.getName()));
        model.addAttribute("customerDto", customerDto);
        return "myAccount";
    }

    @RequestMapping(value = "/user/update-my-account", method = {RequestMethod.GET, RequestMethod.PUT})
    public String updateInfor(@ModelAttribute("customerDto") CustomerDto customerDto, RedirectAttributes redirectAttributes) {
        customerService.saveOrUpdate(customerDto);
        redirectAttributes.addFlashAttribute("success", "Update success!!!");
        return "redirect:/user/my-account";
    }
}
