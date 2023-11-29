package com.example.tttn.controller;

import com.example.tttn.dto.CategoryDto;
import com.example.tttn.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/categories")
    public String getCategories(Model model) {
        List<CategoryDto> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String showFormAddCategory(Model model) {
        model.addAttribute("categoryNew", new CategoryDto());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String create(@ModelAttribute("categoryNew") CategoryDto category, RedirectAttributes attributes) {
        try {
            categoryService.saveOrUpdate(category);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/admin/categories";

    }

    @GetMapping("/admin/categories/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        CategoryDto oldCategory = categoryService.getById(id);
        model.addAttribute("categoryNew", oldCategory);
        return "categoriesAdd";
    }
}
