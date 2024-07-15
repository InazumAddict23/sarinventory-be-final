package com.csthesis.sarinventory_be_final.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "debtors")
@SQLDelete(sql = "UPDATE debts SET deleted=true WHERE id=?")
//@Where(clause = "deleted = false")
//@FilterDef(
//        name = "deletedItemFilter",
//        parameters = @ParamDef(name = "isDeleted", type = org.hibernate.type.descriptor.java.BooleanJavaType.class))
//@Filter(
//        name = "deletedItemFilter",
//        condition = "deleted = :isDeleted")

public class Debtor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "debtor_name", unique = true)
    private String name;

    @Column (name = "total_debt")
    private Integer total = 0;

    @OneToMany(mappedBy = "debtor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Debt> debts = new ArrayList<>();

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "user_id")
    private User user;

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

    public Debtor() {

    }

    public Debtor(Long id, String name, Integer total, Date dateCreated, Date dateModified, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.deleted = deleted;
    }

    public void addDebt(Debt debt) {
        debts.add(debt);
        debt.setDebtor(this);
        this.total += debt.getAmount();
    }

    public void removeDebt(Debt debt, boolean softDelete) {
        if (softDelete) {
            if (!debt.isDeleted()) {
                debt.softDelete();
                this.total -= debt.getAmount();
            }
        } else {
            debts.remove(debt);
            debt.setDebtor(null);
            this.total -= debt.getAmount();
        }
    }

    public List<Debt> getActiveDebts() {
        return debts.stream()
                .filter(debt -> !debt.isDeleted())
                .collect(Collectors.toList());
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonManagedReference
    public List<Debt> getDebts() {
        return debts;
    }

    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }

    @JsonBackReference
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
