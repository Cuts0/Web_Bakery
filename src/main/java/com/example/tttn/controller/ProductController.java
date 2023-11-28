package com.example.tttn.controller;

import com.example.tttn.dto.ProductDto;
import com.example.tttn.dto.ProductRevenueDetail;
import com.example.tttn.dto.ProductRevenueDto;
import com.example.tttn.service.CategoryService;
import com.example.tttn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/products")
    public String getProducts(Model model) {
        List<ProductDto> products = productService.getAll();
        model.addAttribute("products", products);
        model.addAttribute("size", products.size());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String showFormAddProduct(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("categories", categoryService.getAll());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String creat(@ModelAttribute("productDto") ProductDto dto,
                        @RequestParam("productImage") MultipartFile imageProduct,
                        @RequestParam("imgName") String oldImageUpdate,
                        RedirectAttributes redirectAttributes) {
        productService.saveOrUpdate(dto, imageProduct, oldImageUpdate);
        redirectAttributes.addFlashAttribute("success", "Thành công");
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        ProductDto oldProduct = productService.getById(id);
        model.addAttribute("productDto", oldProduct);
        model.addAttribute("categories", categoryService.getAll());
        return "productsAdd";
    }
    @GetMapping("/admin/statistics")
    public String statistics(Model model){
        List<ProductRevenueDto> revenues = productService.getReveneuProduct();
        double totalRevenue = revenues.stream().mapToDouble(ProductRevenueDto::getTotalRevenue).sum();
        model.addAttribute("totalRevenue", NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(totalRevenue));
        model.addAttribute("revenueProduct", revenues);
        model.addAttribute("size", revenues.size());

        return "revenueProduct";
    }
    @GetMapping("/admin/statistics/{id}")
    public String dealProductHistory(@PathVariable("id")Long productId, Model model){
        List<ProductRevenueDetail> list = productService.getRevenueProductDetail(productId);
        model.addAttribute("size", list.size());
        model.addAttribute("list", list);
        return "revenueProductDetail";
    }
}
