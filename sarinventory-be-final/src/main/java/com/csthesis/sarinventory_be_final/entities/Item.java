//package com.thesis2.sarinventorybackend.entities;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.SQLDelete;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.util.Date;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table (name = "items")
//@SQLDelete(sql = "UPDATE items SET deleted=true WHERE id =?")
////@Where(clause = "deleted = false")
////@FilterDef(
////        name = "deletedItemFilter",
////        parameters = @ParamDef(name = "isDeleted", type = org.hibernate.type.descriptor.java.BooleanJavaType.class))
////@Filter(
////        name = "deletedItemFilter",
////        condition = "deleted = :isDeleted")
//public class Item{
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @Column(name = "item_name", length = 50)
//    private String name;
//
//    @Column(name = "item_stock", nullable = false)
//    private int stock;
//
//    @Column(name = "item_sold", nullable = false)
//    private int sold;
//
//    @Column(name = "item_added", nullable = false)
//    private int added;
//
//    @Column(name = "item_price", nullable = false)
//    private double price;
//
//    @CreationTimestamp
//    @JsonFormat (pattern = "MM-dd-yyyy")
//    @Column(updatable = false, nullable = false, name = "date_updated")
//    private Date dateCreated;
//
//
//    @UpdateTimestamp
//    @JsonFormat (pattern = "MM-dd-yyyy")
//    @Column(name = "date_modified", nullable = false)
//    private Date dateModified;
//
//    @ManyToOne (cascade = CascadeType.MERGE)
//    @JoinTable (name = "category_item", joinColumns = {@JoinColumn(name = "item_id")}, inverseJoinColumns = {@JoinColumn(name="category_id")})
//    private Category category;
//
//    @Column (nullable = false)
//    private Boolean deleted = Boolean.FALSE;
//}


// Item.java
package com.csthesis.sarinventory_be_final.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Table(name = "items")
@SQLDelete(sql = "UPDATE items SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 50)
    private String name;

    @Column(name = "item_stock", nullable = false)
    private int stock;

    @Column(name = "item_sold", nullable = false)
    private int sold;

    @Column(name = "item_added", nullable = false)
    private int added;

    @Column(name = "item_price", nullable = false)
    private double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @JsonFormat(pattern = "MM-dd-yyyy")
    @Column(updatable = false, nullable = false, name = "date_updated")
    private Date dateCreated;

    @UpdateTimestamp
    @JsonFormat(pattern = "MM-dd-yyyy")
    @Column(name = "date_modified", nullable = false)
    private Date dateModified;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM-dd-yyyy")
    @Column(name = "last_sale_date", nullable = false)
    private Date lastSaleDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinTable(name = "category_item", joinColumns = {@JoinColumn(name = "item_id")}, inverseJoinColumns = {@JoinColumn(name="category_id")})
    private Category category;

    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;

    public Item() {

    }

    public Item(Long id, String name, int stock, int sold, int added, double price, Date dateCreated, Date dateModified, Date lastSaleDate, Category category, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.sold = sold;
        this.added = added;
        this.price = price;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.lastSaleDate = lastSaleDate;
        this.category = category;
        this.deleted = deleted;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getAdded() {
        return added;
    }

    public void setAdded(int added) {
        this.added = added;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @JsonBackReference
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLastSaleDate() {
        return lastSaleDate;
    }

    public void setLastSaleDate(Date lastSaleDate) {
        this.lastSaleDate = lastSaleDate;
    }
}
