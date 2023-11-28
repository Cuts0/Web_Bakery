package com.example.tttn.service;

import com.example.tttn.dto.ProductDto;
import com.example.tttn.dto.ProductRevenueDetail;
import com.example.tttn.dto.ProductRevenueDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {
    List<ProductDto> getAll();

    Page<ProductDto> pageProducts(int pageIndex);

    Page<ProductDto> pageProductsInCategory(int pageIndex, String code);
    Page<ProductDto> pageSearchProduct(int pageIndex, String key);

    ProductDto getById(Long id);

    Object saveOrUpdate(ProductDto dto, MultipartFile file, String imageUpdate);

    void deleteById(Long id);
    List<ProductRevenueDto> getReveneuProduct();
    List<ProductRevenueDetail> getRevenueProductDetail(Long productId);
}
