package com.csthesis.sarinventory_be_final.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.List;

@Entity
@Table (name = "category")
public class Category {

    @Id
    @GeneratedValue
    @Column (name = "category_id")
    private Long id;

    @Column(name = "category_name", unique = true, nullable = false)
    private String name;

    @CreationTimestamp
    @JsonFormat(pattern = "MM-dd-yyyy")
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @OneToMany(mappedBy = "category")
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private List<Item> itemList;

    public Category(Long id, String name, Date dateCreated, List<Item> itemList) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.itemList = itemList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
