package com.csthesis.sarinventory_be_final.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table (name = "suppliers")
@SQLDelete(sql = "UPDATE suppliers SET deleted = true WHERE id=?")
//@Where(clause = "deleted = false")
//@FilterDef(
//        name = "deletedItemFilter",
//        parameters = @ParamDef(name = "isDeleted", type = org.hibernate.type.descriptor.java.BooleanJavaType.class))
//@Filter(
//        name = "deletedItemFilter",
//        condition = "deleted = :isDeleted")
public class Supplier {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="supplier_name", length = 50, unique = true, nullable = false)
    private String name;

    @Column(name="phone_number", length = 11, nullable = false)
    private String phoneNumber;

    @CreationTimestamp
    @JsonFormat(pattern = "MM-dd-yyyy")
    @Column(updatable = false, nullable = false, name = "date_updated")
    private Date dateCreated;

    @UpdateTimestamp
    @JsonFormat (pattern = "MM-dd-yyyy")
    @Column(name = "date_modified", nullable = false)
    private Date dateModified;

    @Column (nullable = false)
    private Boolean deleted = Boolean.FALSE;

    public Supplier(Long id, String name, String phoneNumber, Date dateCreated, Date dateModified, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
