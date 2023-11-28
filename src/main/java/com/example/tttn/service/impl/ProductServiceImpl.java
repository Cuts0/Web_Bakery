package com.example.tttn.service.impl;

import com.example.tttn.dto.ProductDto;
import com.example.tttn.dto.ProductRevenueDetail;
import com.example.tttn.dto.ProductRevenueDto;
import com.example.tttn.entity.Category;
import com.example.tttn.entity.Product;
import com.example.tttn.repository.CategoryRepository;
import com.example.tttn.repository.ProductRepository;
import com.example.tttn.service.ProductService;
import com.example.tttn.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageUpload imageUpload;

    @Override
    public List<ProductDto> getAll() {
        return productRepository.getAllProducts();
    }

    @Override
    public Page<ProductDto> pageProducts(int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 5);
        return productRepository.getListPage(pageable);
    }

    @Override
    public Page<ProductDto> pageProductsInCategory(int pageIndex, String categoryCode) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 5);
        return productRepository.getListPageProductsInCategory(pageable, categoryCode);
    }

    @Override
    public Page<ProductDto> pageSearchProduct(int pageIndex, String key) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 5);
        return productRepository.getListPageSearchProductByName(pageable, key);
    }

    @Override
    public ProductDto getById(Long id) {
        return new ProductDto(productRepository.findProductById(id));
    }

    @Override
    public Object saveOrUpdate(ProductDto dto, MultipartFile imageFile, String oldImageUpdate) {
        Product product = new Product();
        Category category = categoryRepository.findCategoryById(dto.getCategoryId());
        if (dto.getId() != null) {
            product = productRepository.findProductById(dto.getId());
            product.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        } else {
            product = new Product();
            product.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        if (imageFile.isEmpty()) {
            product.setImage(oldImageUpdate);
        } else {
            try {
                imageUpload.uploadImage(imageFile);
                product.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        product.setCode(dto.getCode());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setDescription(dto.getDescription());
        product.setCategory(category);
        product = productRepository.save(product);
        return new ProductDto(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductRevenueDto> getReveneuProduct() {
        return productRepository.getRevenueProduct();
    }

    @Override
    public List<ProductRevenueDetail> getRevenueProductDetail(Long productId) {
        return productRepository.getRevenueProductDetail(productId);
    }

}
