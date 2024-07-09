package com.csthesis.sarinventory_be_final.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "debts")
@SQLDelete(sql = "UPDATE debts SET isPaid=true WHERE id=?")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @Column (name = "debt_amount")
    private int amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "debtor_id")
    private Debtor debtor;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    @JsonFormat(pattern = "MM-dd-yyyy HH:MM")
    private Date date;

    @CreationTimestamp
    @JsonFormat(pattern = "MM-dd-yyyy")
    @Column(updatable = false, nullable = false, name = "date_updated")
    private Date dateCreated;

    @UpdateTimestamp
    @JsonFormat (pattern = "MM-dd-yyyy")
    @Column(name = "date_modified", nullable = false)
    private Date dateModified;

    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;

    public Debt () {

    }

    public Debt(Long id, String description, int amount, Date date, Date dateCreated, Date dateModified, Boolean deleted) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @JsonBackReference
    public Debtor getDebtor() {
        return debtor;
    }

    public void setDebtor(Debtor debtor) {
        this.debtor = debtor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public void softDelete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
