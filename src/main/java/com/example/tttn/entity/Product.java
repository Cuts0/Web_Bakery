package com.example.tttn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseObject{
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private Integer quantity;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @Column
    private Double price;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
