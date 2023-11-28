package com.example.tttn.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@Data
public class Category extends BaseObject{
    @Column
    private String name;
    @Column
    private String code;

}
