package com.example.tttn.repository;

import com.example.tttn.dto.ProductDto;
import com.example.tttn.dto.ProductRevenueDetail;
import com.example.tttn.dto.ProductRevenueDto;
import com.example.tttn.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long id);

    @Query("select new com.example.tttn.dto.ProductDto(ed) from Product ed")
    List<ProductDto> getAllProducts();

    @Query("select new com.example.tttn.dto.ProductDto(ed) from Product ed")
    Page<ProductDto> getListPage(Pageable pageable);

    @Query("select new com.example.tttn.dto.ProductDto(ed) from Product ed where ed.category.code = ?1")
    Page<ProductDto> getListPageProductsInCategory(Pageable pageable, String code);

    @Query("select new com.example.tttn.dto.ProductDto(ed) from Product ed where ed.name like %?1%")
    Page<ProductDto> getListPageSearchProductByName(Pageable pageable, String key);

    @Query("select new com.example.tttn.dto.ProductRevenueDto(p.id, p.name, sum(od.quantity * od.price)) from Product p, OrderDetail od, Order o " +
            "where p.id = od.product.id and o.id = od.orders.id and o.status = 'Đã xác nhận' " +
            "group by p.id " +
            "order by sum(od.quantity * od.price) DESC")
    List<ProductRevenueDto> getRevenueProduct();

    @Query("select new com.example.tttn.dto.ProductRevenueDetail(o.createDate, p.name, od.price, od.quantity, o.customer.username) from Product p, OrderDetail od, Order o " +
            "where p.id = ?1 and p.id = od.product.id and o.id = od.orders.id and o.status = 'Đã xác nhận'" +
            "order by o.createDate ASC")
    List<ProductRevenueDetail> getRevenueProductDetail(Long productId);
}