package com.csthesis.sarinventory_be_final.Dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class DebtorDTO {
    private Long id;
    private String name;
    private Integer totalDebt;

    // Constructor, getters, and setters

    public DebtorDTO(Long id, String name, Integer totalDebt) {
        this.id = id;
        this.name = name;
        this.totalDebt = totalDebt;
    }

    public DebtorDTO() {
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

    public Integer getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(Integer totalDebt) {
        this.totalDebt = totalDebt;
    }
}
