package com.csthesis.sarinventory_be_final.Dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.crypto.Data;
import java.util.Date;


@JsonSerialize
public class DebtDTO {
    private Long id;
    private String description;
    private Integer amount;
    private Date debtDate;


    public DebtDTO(Long id, String description, Integer amount, Date debtDate) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.debtDate = debtDate;
    }

    public DebtDTO() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setDebtDate(Date debtDate) {
        this.debtDate = debtDate;
    }
}
