package com.fashion.ecommerce.entity;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name="categories")
public class CategoryEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String name;


    private String description;



    @ManyToOne
    @JoinColumn(name="parent_id")
    private CategoryEntity parent;



    @OneToMany(mappedBy="parent",
            cascade=CascadeType.ALL)
    private List<CategoryEntity> children;



    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id=id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name=name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description=description;
    }


    public CategoryEntity getParent() {
        return parent;
    }


    public void setParent(CategoryEntity parent) {
        this.parent=parent;
    }


    public List<CategoryEntity> getChildren() {
        return children;
    }


    public void setChildren(List<CategoryEntity> children) {
        this.children=children;
    }
}