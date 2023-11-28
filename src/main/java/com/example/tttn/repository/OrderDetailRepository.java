package com.example.tttn.repository;

import com.example.tttn.dto.CartItem;
import com.example.tttn.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("select o from OrderDetail o where o.product.id = ?1")
    OrderDetail findOrderDetailByProductId(Long id);
    @Query("select (count(o)>0) from OrderDetail o where o.product.id = ?1")
    boolean existsOrderDetailByProductId(Long productId);
    @Query("select new com.example.tttn.dto.CartItem(o) from OrderDetail o")
    List<CartItem> findCartItems();
    OrderDetail findOrderDetailById(Long id);
}
