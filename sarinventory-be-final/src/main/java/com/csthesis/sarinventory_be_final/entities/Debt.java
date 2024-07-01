package com.csthesis.sarinventory_be_final.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "debtors")
@SQLDelete(sql = "UPDATE debts SET isPaid=true WHERE id=?")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "debt_amount", nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "debtor_id")
    @JsonBackReference
    private Debtor debtor;

    @Column(name = "date", nullable = false)
    @JsonFormat(pattern = "MM-dd-yyyy MM:SS")
    private Date date;

    @CreationTimestamp
    @JsonFormat(pattern = "MM-dd-yyyy")
    @Column(updatable = false, nullable = false, name = "date_updated")
    private Date dateCreated;

    @UpdateTimestamp
    @JsonFormat (pattern = "MM-dd-yyyy")
    @Column(name = "date_modified", nullable = false)
    private Date dateModified;

    @Column (nullable = false)
    private Boolean isPaid = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

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

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
