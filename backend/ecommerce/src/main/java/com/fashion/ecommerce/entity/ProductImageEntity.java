package com.fashion.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_images")
public class ProductImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private Integer sortOrder = 0;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    // getters setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    public Integer getSortOrder() {return sortOrder;}
    public void setSortOrder(Integer sortOrder) {this.sortOrder = sortOrder;}

    public ProductEntity getProduct() {return product;}
    public void setProduct(ProductEntity product) {this.product = product;}
}